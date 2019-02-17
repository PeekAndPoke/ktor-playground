package de.peekandpoke.karango.meta

import com.squareup.kotlinpoet.asTypeName
import me.eugeniomarletti.kotlin.processing.KotlinProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic
import kotlin.reflect.KClass

interface ProcessorUtils : KotlinProcessingEnvironment {

    val logPrefix: String

    fun logNote(str: String) =
        messager.printMessage(Diagnostic.Kind.NOTE, "$logPrefix $str")

    fun logWarning(str: String) =
        messager.printMessage(Diagnostic.Kind.WARNING, "$logPrefix $str")

    fun logError(str: String) =
        messager.printMessage(Diagnostic.Kind.ERROR, "$logPrefix $str")

    fun logMandatoryWarning(str: String) =
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "$logPrefix $str")

    fun logOther(str: String) =
        messager.printMessage(Diagnostic.Kind.OTHER, "$logPrefix $str")

    ////  REFLECTION  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val <T : Any> KClass<T>.fqn: String get() = java.canonicalName

    val TypeMirror.fqn get() = asTypeName().toString()

    val Element.fqn get() = asType().fqn

    /**
     * Check if an element is a collection type
     */
    fun Element?.isCollection() = this is TypeElement &&
            interfaces.any { itf -> itf.fqn.startsWith(java.util.Collection::class.fqn) }

    val TypeElement.variables
        get() = enclosedElements
            .filterIsInstance<VariableElement>()
            .filter { !it.simpleName.contentEquals("Companion") }

    val TypeElement.referencedTypes
        get() : List<TypeMirror> = variables
            .map { v -> listOf(v.asType()).plus(v.referencedTypes) }
            .flatten()
            .distinct()

    /**
     * Check if a variable element is a collection type
     */
    val VariableElement.isCollection get () = typeUtils.asElement(asType()).isCollection()

    val DeclaredType?.referencedTypes
        get (): List<TypeMirror> {

            if (this is DeclaredType) {
                return this.typeArguments.toList()
            }

            return listOf()
        }

    val VariableElement.referencedTypes get () : List<TypeMirror> = (this.asType() as? DeclaredType).referencedTypes

    ////  KOTLIN COMPAT  /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun Element.asKotlinClass(): String =
        asType()
            .asTypeName().toString()
            .replace("/", ".")
            .replace("kotlin.jvm.functions", "kotlin")
            .replace("java.util.List", "kotlin.collections.List")
            .replace("java.util.Set", "kotlin.collections.Set")
            .replace("java.util.Map", "kotlin.collections.Map")
            .replace("java.util.SortedMap", "kotlin.collections.SortedMap")
            .replace("java.util.Collection", "kotlin.collections.Collection")
            .replace("java.lang.Throwable", "kotlin.Throwable")
            .let {
                if (it == "java.lang") it.replace("java.lang", "kotlin")
                else it
            }.let {
                if (it == "java.util") it.replace("java.util", "kotlin.collections")
                else it
            }
            .replace("int", "kotlin.Int")
            .replace("kotlin.Integer", "kotlin.Int")
            .replace("Integer", "Int")
            .replace("java.lang.Int", "kotlin.Int")
            .replace("java.lang.String", "kotlin.String")

}
