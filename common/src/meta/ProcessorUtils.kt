package de.peekandpoke.ultra.common.meta

import com.squareup.kotlinpoet.asTypeName
import me.eugeniomarletti.kotlin.processing.KotlinProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

@Suppress("unused")
interface ProcessorUtils : KotlinProcessingEnvironment {

    val logPrefix: String

    fun logNote(str: String) = messager.printMessage(Diagnostic.Kind.NOTE, "$logPrefix $str")

    fun logWarning(str: String) = messager.printMessage(Diagnostic.Kind.WARNING, "$logPrefix $str")

    fun logError(str: String) = messager.printMessage(Diagnostic.Kind.ERROR, "$logPrefix $str")

    fun logMandatoryWarning(str: String) = messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "$logPrefix $str")

    fun logOther(str: String) = messager.printMessage(Diagnostic.Kind.OTHER, "$logPrefix $str")

    ////  REFLECTION  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    val <T : Any> KClass<T>.fqn: String get() = java.canonicalName

    val TypeMirror.fqn get() = asTypeName().toString()

    val Element.fqn get() = asType().fqn

    val Element.isPrimitiveType get() = when (fqn) {
        "kotlin.Boolean",
        "kotlin.Char",
        "kotlin.Byte",
        "kotlin.Short",
        "kotlin.Int",
        "kotlin.Long",
        "kotlin.Float",
        "kotlin.Double",
        "kotlin.Void" -> true

        else -> false
    }

    val Element.isStringType get() = fqn == "java.lang.String"

    /**
     * Get all variables of a type element
     */
    val TypeElement.variables
        get() = enclosedElements
            .filterIsInstance<VariableElement>()
            .filter { !it.simpleName.contentEquals("Companion") }

    /**
     * Get all types, that are directly or recursively used within the given Element
     */
    fun Element.getReferencedTypesRecursive(): Set<TypeMirror> {

        var last = mutableSetOf<TypeMirror>()
        var current = mutableSetOf(this.asType())

        while (current.size > last.size) {

            last = current

            current = last
                .map { it.getReferencedTypes() }
                .flatten()
                .distinct()
                .toMutableSet()
        }

        return current
    }

    /**
     * Get all types that are directly referenced by the given type
     */
    fun TypeMirror.getReferencedTypes(): Set<TypeMirror> {

        val result = mutableSetOf<TypeMirror>()

        if (this is DeclaredType) {

            logNote("DeclaredType $this")

            // add the type it-self
            result.add(this)
            // add all generic type parameters
            result.addAll(this.typeArguments)

            // Next lets have a look at all the variables that the type defines
            val element = typeUtils.asElement(this)

            if (element is TypeElement) {

                logNote("  .. TypeElement ${this}")

                val nested = element.variables
                    .asSequence()
                    .filter { !it.asType().kind.isPrimitive }
                    .map { v: VariableElement ->

                        mutableListOf<TypeMirror>().apply {

                            val elType: TypeMirror = v.asType()

                            if (elType is DeclaredType) {
                                // add the type it self
                                add(elType)
                                logNote("  .. ${v.simpleName} .. DeclaredType $elType")

                                // add all generic type arguments of the type that are declared types
                                addAll(elType.typeArguments.mapNotNull { (it as? DeclaredType) })
                            }
                        }
                    }
                    .flatten()
                    // Black-list of packages that we will not create code for
                    .filter { !it.fqn.startsWith("java.") }
                    .filter { !it.fqn.startsWith("javax.") }
                    .filter { !it.fqn.startsWith("kotlin.") }

                result.addAll(nested)
            }
        }

        return result
    }

    ////  KOTLIN COMPAT  /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun Element.asKotlinClass(): String =
        asType()
            .asTypeName().toString()
            .replace("/", "")
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
