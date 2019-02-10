package de.peekandpoke.karango.meta

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import me.eugeniomarletti.kotlin.metadata.KotlinClassMetadata
import me.eugeniomarletti.kotlin.metadata.kotlinMetadata
import me.eugeniomarletti.kotlin.metadata.proto
import me.eugeniomarletti.kotlin.metadata.shadow.serialization.deserialization.getName
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class EntityCollection(val name: String)

@AutoService(Processor::class)
open class AnnotationProcessor : KotlinAbstractProcessor() {

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        roundEnv
            .getElementsAnnotatedWith(EntityCollection::class.java)
            .filterIsInstance<TypeElement>()
            .forEach { buildFileSpecFor(it) }

        return true
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(EntityCollection::class.java.canonicalName)

    private val Element.hasNoCompanion: Boolean
        get() = (kotlinMetadata as? KotlinClassMetadata)?.data?.run {
            nameResolver.getName(proto.companionObjectName).asString() != "Companion"
        } ?: true

    private fun VariableElement.asKotlinClass(): String =
        asType()
            .asTypeName().toString()
            .replace("/", ".")
            .replace("kotlin.jvm.functions", "kotlin")
            .replace("java.util.List", "kotlin.collections.List")
            .replace("java.util.Set", "kotlin.collections.Set")
            .replace("java.util.Map", "kotlin.collections.Map")
            .replace("java.util.SortedMap", "kotlin.collections.SortedMap")
            .replace("java.util.Collection", "kotlin.collections.Collection")
            .replace("java.lang.Throwable", "kotlin.Throwable").let {
                if (it == "java.lang") it.replace("java.lang", "kotlin")
                else it
            }.let {
                if (it == "java.util") it.replace("java.util", "kotlin.collections")
                else it
            }
            .replace("kotlin.Integer", "kotlin.Int")
            .replace("Integer", "Int")
            .replace("java.lang.String", "kotlin.String")

    private fun buildFileSpecFor(element: TypeElement) {

        messager.printMessage(
            Diagnostic.Kind.WARNING,
            "Karango: Found type ${element.simpleName} in ${element.asClassName().packageName}"
        )

        val annotation = element.getAnnotation(EntityCollection::class.java)!!

        val className = element.asClassName()
        val packageName = className.packageName;
        val simpleName = className.simpleName

        val collectionName = annotation.name

        val dir = File("$generatedDir/${className.packageName.replace('.', '/')}").also { it.mkdirs() }
        val file = File(dir, "${className.simpleName}${'$'}${'$'}karango.kt")

        val variables = element
            .enclosedElements
            .filter { it.kind == ElementKind.FIELD }
            .filterIsInstance<VariableElement>()
            .filter { !it.simpleName.contentEquals("Companion") }

        variables.forEach {
            messager.printMessage(Diagnostic.Kind.WARNING, "enclosed $it ${it.asType()}")
        }

        val collFuns = variables.map {
            val type = it.asKotlinClass()
            val prop = it.simpleName

//                    """
//                    inline val ${simpleName}Collection.$prop inline get() = PathInEntity<$type>("$collectionName", "c_$collectionName", listOf("$prop")) 
//                    inline val PathInEntity<$simpleName>.$prop inline get() = append<$type>("$prop")
//                    """
            """
            inline val ${simpleName}Collection.$prop inline get() = PathInCollection<$simpleName, $type>(this, listOf("$prop")) 
            inline val <S> PathInCollection<S, $simpleName>.$prop inline get() = append<$type>("$prop")
            """
        }

        val content = """
                    
            package $packageName 
            
            import de.peekandpoke.karango.*
            
            object ${simpleName}Collection : EntityCollectionDefinitionImpl<$simpleName>() {
                override fun getSimpleName() = "$collectionName"
                override fun getQueryName() = "c_$collectionName"
                override fun getReturnType() = $simpleName::class.java
            }

            ${collFuns.joinToString("")}
            
        """.trimIndent()

        file.writeText(content)
    }
}
