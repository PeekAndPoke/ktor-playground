package de.peekandpoke.karango.meta

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.asClassName
import de.peekandpoke.frozen.Mutator
import de.peekandpoke.karango.aql.ucFirst
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
open class AnnotationProcessor : KotlinAbstractProcessor(), ProcessorUtils {

    override val logPrefix: String = "[Karango] "

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(EntityCollection::class.java.canonicalName)

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        generateKarangoFiles(roundEnv)

        generateMutatorFiles(roundEnv)

        // build registry with all the classes for which we want to create dynamic proxies
        val all = roundEnv
            .getElementsAnnotatedWith(EntityCollection::class.java)
            .filterIsInstance<TypeElement>()


        if (!roundEnv.processingOver()) {
            val dir = File("$generatedDir/de/peekandpoke/frozen/generated").also { it.mkdirs() }
            val file = File(dir, "class_registry.kt")

            val allClassesStr = all.joinToString(",\n                        ") { "\"${it.fqn}\"" }

            val content = """
                package de.peekandpoke.frozen.generated

                class Karango_MakeProxiable : de.peekandpoke.frozen.MakeClassesProxiable {
                    override fun get() = listOf<String>(
                        $allClassesStr
                    )
                }
            """.trimIndent()

            file.writeText(content)
        }

        return true
    }

    private fun generateKarangoFiles(roundEnv: RoundEnvironment) {
        // Find all types that have a Karango Annotation
        val elems = roundEnv
            .getElementsAnnotatedWith(EntityCollection::class.java)
            .filterIsInstance<TypeElement>()

        // Find all the types that are referenced by these types and add them to the pool
        val pool = elems.plus(
            elems.map { it.getReferencedTypesRecursive().map { tm -> typeUtils.asElement(tm) } }.flatten().distinct()
        )

        val all = pool.filterIsInstance<TypeElement>()
            // Black list some packages
            .filter { !it.fqn.startsWith("java.") }
            .filter { !it.fqn.startsWith("javax.") }
            .filter { !it.fqn.startsWith("kotlin.") }
            .distinct()

        logNote("all types for karango: $all")

        // generate code for all the relevant types
        all.forEach { buildKarangoFileFor(it) }
    }

    private fun buildKarangoFileFor(element: TypeElement) {

        logNote("Found type ${element.simpleName} in ${element.asClassName().packageName}")

        val annotation: EntityCollection? = element.getAnnotation(EntityCollection::class.java)

        val className = element.asClassName()
        val packageName = className.packageName
        val simpleName = className.simpleName

        val codeBlocks = mutableListOf<String>()

        codeBlocks.add(
            """
            package $packageName

            import de.peekandpoke.karango.*
            import de.peekandpoke.karango.aql.*

        """.trimIndent()
        )

        if (annotation != null) {

            // TODO: make sure that we get a valid property name here ... split non Alphanum-chars and join camel-case
            val defName = if (annotation.defName.isNotEmpty()) annotation.defName else annotation.collection.ucFirst()

            codeBlocks.add(
                """
                val $defName : EntityCollectionDefinition<$simpleName> =
                    object : EntityCollectionDefinitionImpl<$simpleName>("${annotation.collection}", type()) {}

            """.trimIndent()
            )
        }

        element.variables.forEach {

            val type = it.asKotlinClass()
            val prop = it.simpleName

            codeBlocks.add("//// $prop ".padEnd(160, '/'))

            codeBlocks.add(
                """

                inline val Iter<$simpleName>.$prop inline get() = PropertyPath.start(this).append<$type, $type>("$prop")
                inline val Expression<$simpleName>.$prop inline get() = PropertyPath.start(this).append<$type, $type>("$prop")

                inline val PropertyPath<$simpleName, $simpleName>.$prop inline @JvmName("${prop}_0") get() = append<$type, $type>("$prop")
                inline val PropertyPath<$simpleName, L1<$simpleName>>.$prop inline @JvmName("${prop}_1") get() = append<$type, L1<$type>>("$prop")
                inline val PropertyPath<$simpleName, L2<$simpleName>>.$prop inline @JvmName("${prop}_2") get() = append<$type, L2<$type>>("$prop")
                inline val PropertyPath<$simpleName, L3<$simpleName>>.$prop inline @JvmName("${prop}_3") get() = append<$type, L3<$type>>("$prop")
                inline val PropertyPath<$simpleName, L4<$simpleName>>.$prop inline @JvmName("${prop}_4") get() = append<$type, L4<$type>>("$prop")
                inline val PropertyPath<$simpleName, L5<$simpleName>>.$prop inline @JvmName("${prop}_5") get() = append<$type, L5<$type>>("$prop")

            """.trimIndent()
            )
        }

        val content = codeBlocks.joinToString(System.lineSeparator())

        val dir = File("$generatedDir/${className.packageName.replace('.', '/')}").also { it.mkdirs() }
        val file = File(dir, "${className.simpleName}${"$$"}karango.kt")

        file.writeText(content)
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

        val all = pool.filterIsInstance<TypeElement>()
            // Black list some packages
            .filter { !it.fqn.startsWith("java.") }
            .filter { !it.fqn.startsWith("javax.") }
            .filter { !it.fqn.startsWith("kotlin.") }
            .distinct()

        logNote("all types for karango: $all")

        // generate code for all the relevant types
        all.forEach { buildMutatorFileFor(it) }

    }

    private fun buildMutatorFileFor(element: TypeElement) {

        logNote("MUTATOR: Found type ${element.simpleName} in ${element.asClassName().packageName}")

        val className = element.asClassName()
        val packageName = className.packageName
        val simpleName = className.simpleName
        val mutatorName = "${simpleName}Mutator"

        val codeBlocks = mutableListOf<String>()

        codeBlocks.add(
            """
            package $packageName

            import de.peekandpoke.frozen.*

            fun $simpleName.mutate(builder: (draft: ${simpleName}Mutator) -> Unit) = mutator().apply(builder).result

            fun $simpleName.mutator(onChange: OnModify<$simpleName> = {_, _ ->}) = ${simpleName}Mutator(this, onChange)

            class ${simpleName}Mutator(target: $simpleName, onChange: OnModify<$simpleName> = {_, _ ->}) : DataClassMutator<$simpleName>(target, onChange) {

        """.trimIndent()
        )

        element.variables.forEach {

            val type = it.asKotlinClass()
            val prop = it.simpleName

            codeBlocks.add("//// $prop ".padEnd(160, '/') + System.lineSeparator())

            logNote("$prop of type ${it.fqn}")

            when {
                it.isPrimitiveType || it.isStringType ->
                    codeBlocks.add(
                        """
                            var $prop: $type
                                get() = result.$prop
                                set(v) = modify(result::$prop, v)

                        """.trimIndent().prependIndent("    ")
                    )

                // TODO: check for collections

                !it.fqn.startsWith("java.") && !it.fqn.startsWith("javax.") && !it.fqn.startsWith("kotlin.") ->
                    codeBlocks.add(
                        """
                            val $prop: ${type}Mutator
                                = result.$prop.mutator { before, after -> modify(result::$prop, after) }

                        """.trimIndent().prependIndent("    ")
                    )

                else -> logWarning("Cannot handle $prop of type ${it.fqn}")
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
