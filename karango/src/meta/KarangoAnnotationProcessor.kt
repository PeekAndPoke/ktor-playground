package de.peekandpoke.karango.meta

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.asClassName
import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.meta.KotlinProcessor
import de.peekandpoke.ultra.vault.Ref
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
open class KarangoAnnotationProcessor : KotlinProcessor("[Karango]") {

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(Karango::class.java.canonicalName)

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        generateKarangoFiles(roundEnv)

        return true
    }

    private fun generateKarangoFiles(roundEnv: RoundEnvironment) {
        // Find all types that have a Karango Annotation
        val elems = roundEnv
            .getElementsAnnotatedWith(Karango::class.java)
            .filterIsInstance<TypeElement>()

        // Find all the types that are referenced by these types and add them to the pool
        val pool = elems.plus(
            elems.map { it.getReferencedTypesRecursive().map { tm -> typeUtils.asElement(tm) } }.flatten().distinct()
        )

        val all = pool.asSequence().filterIsInstance<TypeElement>()
            .distinct()
            // Black list some packages
            .filter { !it.fqn.startsWith("java.") }
            .filter { !it.fqn.startsWith("javax.") }
            .filter { !it.fqn.startsWith("kotlin.") }
            // Black list all generic types
            .filter { it.typeParameters.size == 0 }
            .toList()

        logNote("all types for karango: $all")

        // generate code for all the relevant types
        all.forEach { buildKarangoFileFor(it) }
    }

    private fun buildKarangoFileFor(element: TypeElement) {

        logNote("Found type ${element.simpleName} in ${element.asClassName().packageName}")

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

        element.variables
            // filter delegated properties (e.g. by lazy)
            .filter { !it.simpleName.contains("${"$"}delegate") }
            .forEach {

                val type = when {
                    // References are treated as just strings
                    it.fqn.startsWith(Ref::class.java.canonicalName) -> "kotlin.String"

                    // otherwise we take the original type
                    else -> it.asKotlinClassName()
                }

                val prop = it.simpleName

                codeBlocks.add("//// $prop ".padEnd(160, '/'))

                codeBlocks.add("//// annotations: ${it.annotationMirrors.map { ann -> ann.toString() }}")

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
}
