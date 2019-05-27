package de.peekandpoke.mutator.meta

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.asClassName
import de.peekandpoke.mutator.Mutator
import de.peekandpoke.ultra.common.meta.ProcessorUtils
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
open class MutatorAnnotationProcessor : KotlinAbstractProcessor(), ProcessorUtils {

    override val logPrefix: String = "[Mutator] "

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(
        Mutator::class.java.canonicalName
    )

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        generateMutatorFiles(roundEnv)

        return true
    }

    private fun generateMutatorFiles(roundEnv: RoundEnvironment) {
        // Find all types that have a Karango Annotation
        val elems = roundEnv
            .getElementsAnnotatedWith(Mutator::class.java)
            .filterIsInstance<TypeElement>()

        // Find all the types that are referenced by these types and add them to the pool
        val pool = elems.plus(
            elems.map { it.getReferencedTypesRecursive().map { tm -> typeUtils.asElement(tm) } }.flatten().distinct()
        )

        val all = pool.asSequence().filterIsInstance<TypeElement>()
            // Black list some packages
            .filter { !it.fqn.startsWith("java.") }
            .filter { !it.fqn.startsWith("javax.") }
            .filter { !it.fqn.startsWith("kotlin.") }
            .distinct().toList()

        logNote("all types (nested): $all")

        // generate code for all the relevant types
        all.forEach { buildMutatorFileFor(it) }
    }

    private fun buildMutatorFileFor(element: TypeElement) {

        logNote("Found type ${element.simpleName} in ${element.asClassName().packageName}")

        val className = element.asClassName()
        val packageName = className.packageName
        val simpleName = className.simpleName

        val codeBlocks = mutableListOf<String>()

        codeBlocks.add(
            """
            package $packageName

            import de.peekandpoke.mutator.*

            fun $simpleName.mutate(builder: (draft: ${simpleName}Mutator) -> Unit) = mutator().apply(builder).getResult()

            fun $simpleName.mutator(onChange: OnModify<$simpleName> = {}) = ${simpleName}Mutator(this, onChange)

            class ${simpleName}Mutator(target: $simpleName, onChange: OnModify<$simpleName> = {}) : DataClassMutator<$simpleName>(target, onChange) {

        """.trimIndent()
        )

        element.variables.forEach {

            val type = it.asKotlinClass()
            val prop = it.simpleName

            codeBlocks.add("//// $prop ".padEnd(160, '/') + System.lineSeparator())

            logNote("  .. $prop of type ${it.fqn}")

            when {
                it.isPrimitiveType || it.isStringType ->
                    codeBlocks.add(
                        """
                            var $prop: $type
                                get() = getResult().$prop
                                set(v) = modify(getResult()::$prop, v)

                                fun $prop(cb: $type.($type) -> $type) = modify(getResult()::$prop, $prop.cb($prop))

                        """.trimIndent().prependIndent("    ")
                    )

                // TODO: check for collections

                !it.fqn.startsWith("java.") && !it.fqn.startsWith("javax.") && !it.fqn.startsWith("kotlin.") ->
                    codeBlocks.add(
                        """
                            val $prop: ${type}Mutator
                                = getResult().$prop.mutator { modify(getResult()::$prop, it) }

                        """.trimIndent().prependIndent("    ")
                    )

                else -> logWarning("  !! Cannot handle $prop of type ${it.fqn}")
            }
        }

        codeBlocks.add(
            """
                }
            """.trimIndent()
        )

        val content = codeBlocks.joinToString(System.lineSeparator())

        val dir = File("$generatedDir/${className.packageName.replace('.', '/')}").also { it.mkdirs() }
        val file = File(dir, "${className.simpleName}${"$$"}mutator.kt")

        file.writeText(content)
    }
}
