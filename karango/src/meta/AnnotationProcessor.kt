package de.peekandpoke.karango.meta

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.asClassName
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class EntityCollection(val name: String)

@AutoService(Processor::class)
open class AnnotationProcessor : KotlinAbstractProcessor(), ProcessorUtils {

    override val logPrefix: String = "[Karango] "

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val elems = roundEnv
            .getElementsAnnotatedWith(EntityCollection::class.java)
            .filterIsInstance<TypeElement>()

        val elemsAndReferences = elems.plus(elems.map {
            it.referencedTypes.map { tm -> typeUtils.asElement(tm) }
        }.flatten().distinct())

        elemsAndReferences
            .filterIsInstance<TypeElement>()
            // remove all the classes that are built in java
            .filter { !it.fqn.startsWith("java.") }
            .forEach { buildFileSpecFor(it) }

        return true
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(EntityCollection::class.java.canonicalName)


    private fun buildFileSpecFor(element: TypeElement) {

        logNote("Found type ${element.simpleName} in ${element.asClassName().packageName}")

        logNote(".. referencing types: " + element.referencedTypes)

        val annotation: EntityCollection? = element.getAnnotation(EntityCollection::class.java)

        val className = element.asClassName()
        val packageName = className.packageName
        val simpleName = className.simpleName

        val dir = File("$generatedDir/${className.packageName.replace('.', '/')}").also { it.mkdirs() }
        val file = File(dir, "${className.simpleName}${'$'}${'$'}karango.kt")

        element.variables.map {

            if (it.isCollection()) {
                logNote(".. encloses $it ${it.asType()::class.java} ${it.asType()} .. and is collection !")
            } else {
                logNote(".. encloses $it ${it.asType()}")
            }
        }


        val collFuns = if (annotation !== null) {

            val collectionName = annotation.name

            listOf(
                """
            object ${simpleName}Collection : EntityCollectionDefinitionImpl<$simpleName>() {
                override fun getSimpleName() = "$collectionName"
                override fun getQueryName() = "c_$collectionName"
                override fun getReturnType() = $simpleName::class.java
            }
            """
            ).plus(
                element.variables.map {

                    val type = it.asKotlinClass()
                    val prop = it.simpleName

                    """
            inline val ${simpleName}Collection.$prop inline get() = PathInCollection<$simpleName, $type>(this, listOf(".$prop")) 
            """
                }
            )

        } else listOf()


        val pathFuns = element.variables.map {

            val type = it.asKotlinClass()
            val prop = it.simpleName

            """
            inline val <S> PathInCollection<S, $simpleName>.$prop inline get() = append<$type>(".$prop")
            """
        }

        val content = """
                    
            package $packageName 
            
            import de.peekandpoke.karango.*
            
            ${collFuns.joinToString("")}
            
            ${pathFuns.joinToString("")}
            
        """.trimIndent()

        file.writeText(content)
    }
}
