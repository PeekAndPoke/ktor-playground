package de.peekandpoke.karango.meta

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.asClassName
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

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        // Find all types that have a Karango Annotation
        val elems = roundEnv
            .getElementsAnnotatedWith(EntityCollection::class.java)
            .filterIsInstance<TypeElement>()

        // Find all the types that are referenced by these types and add them to the pool
        val pool = elems.plus(
            elems.map { it.getReferencedTypesRecursive().map { tm -> typeUtils.asElement(tm) } }.flatten().distinct()
        )

        logNote("all types: $pool")

        val all = pool.filterIsInstance<TypeElement>()
            // Black list some packages
            .filter { !it.fqn.startsWith("java.") }
            .filter { !it.fqn.startsWith("javax.") }
            .filter { !it.fqn.startsWith("kotlin.") }
            .distinct()

        // generate code for all the relevant types
        all.forEach { buildFileSpecFor(it) }


        // build registry with all the classes
        if (!roundEnv.processingOver()) {
            val dir = File("$generatedDir/de/peekandpoke/karango/generated").also { it.mkdirs() }
            val file = File(dir, "class_registry.kt")

            val allClassesStr = all.joinToString(",\n        ") { "\"${it.fqn}\"" }

            val content = """
                package de.peekandpoke.karango.generated

                class EntityClassRegistry {

                    val entries = arrayOf<String>(
                        $allClassesStr
                    )
                }

            """.trimIndent()

            file.writeText(content)
        }

        return true
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(EntityCollection::class.java.canonicalName)


    private fun buildFileSpecFor(element: TypeElement) {

        logNote("Found type ${element.simpleName} in ${element.asClassName().packageName}")

        val annotation: EntityCollection? = element.getAnnotation(EntityCollection::class.java)

        val className = element.asClassName()
        val packageName = className.packageName
        val simpleName = className.simpleName

        val codeBlocks = mutableListOf<String>()

        codeBlocks.add("""
            package $packageName

            import de.peekandpoke.karango.*
            import de.peekandpoke.karango.aql.*

        """.trimIndent())

        if (annotation != null) {

            // TODO: make sure that we get a valid property name here ... split non Alphanum-chars and join camel-case
            val defName = if (annotation.defName.isNotEmpty()) annotation.defName else annotation.collection.ucFirst()

            codeBlocks.add("""
                val $defName : EntityCollectionDefinition<$simpleName> =
                    object : EntityCollectionDefinitionImpl<$simpleName>("${annotation.collection}", type()) {}

            """.trimIndent())
        }

        element.variables.forEach {

            val type = it.asKotlinClass()
            val prop = it.simpleName

            codeBlocks.add("//// $prop ".padEnd(160, '/'))

            codeBlocks.add("""

                inline val Iter<$simpleName>.$prop inline get() = PropertyPath.start(this).append<$type, $type>("$prop")
                inline val Expression<$simpleName>.$prop inline get() = PropertyPath.start(this).append<$type, $type>("$prop")

                inline val PropertyPath<$simpleName, $simpleName>.$prop inline @JvmName("${prop}_0") get() = append<$type, $type>("$prop")
                inline val PropertyPath<$simpleName, L1<$simpleName>>.$prop inline @JvmName("${prop}_1") get() = append<$type, L1<$type>>("$prop")
                inline val PropertyPath<$simpleName, L2<$simpleName>>.$prop inline @JvmName("${prop}_2") get() = append<$type, L2<$type>>("$prop")
                inline val PropertyPath<$simpleName, L3<$simpleName>>.$prop inline @JvmName("${prop}_3") get() = append<$type, L3<$type>>("$prop")
                inline val PropertyPath<$simpleName, L4<$simpleName>>.$prop inline @JvmName("${prop}_4") get() = append<$type, L4<$type>>("$prop")
                inline val PropertyPath<$simpleName, L5<$simpleName>>.$prop inline @JvmName("${prop}_5") get() = append<$type, L5<$type>>("$prop")

            """.trimIndent())
        }

        val content = codeBlocks.joinToString(System.lineSeparator())

        val dir = File("$generatedDir/${className.packageName.replace('.', '/')}").also { it.mkdirs() }
        val file = File(dir, "${className.simpleName}${"$$"}karango.kt")

        file.writeText(content)
    }
}
