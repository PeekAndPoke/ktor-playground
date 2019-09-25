package io.ultra.ktor_tools.typedroutes

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

abstract class Routes(val converter: OutgoingConverter, private val mountPoint: String = "") {

    fun route(uri: String) = mountPoint + uri

    inline fun <reified T : Any> route(uri: String) = TypedRoute(converter, T::class, route(uri))
}

data class TypedRoute<T : Any>(val converter: OutgoingConverter, val type: KClass<T>, val uri: String) {

    private val parsedParams = "\\{([^}]*)}".toRegex().findAll(uri).map { it.groupValues[1] }.toList()

    init {
        if (!type.isData && type.primaryConstructor!!.parameters.isNotEmpty()) {
            error("The route object '${type.qualifiedName}' must be a data class")
        }

        if (type.primaryConstructor!!.parameters.any { it.isOptional }) {
            error("The route object '${type.qualifiedName}' must not have optional parameters")
        }

        // TODO: check that all parameters can be handled by the conversion service

        val missingInUri = type.primaryConstructor!!.parameters
            .map { it.name }
            .filter { !parsedParams.contains(it) }

        if (missingInUri.isNotEmpty()) {
            error("Route '$uri' is missing parameters for route object '${type.qualifiedName}': ${missingInUri.joinToString(", ")}")
        }
    }

    operator fun invoke(obj: T) = type.primaryConstructor!!.parameters.fold(uri) { uri, it ->
        uri.replace(
            "{${it.name}}",
            type.declaredMemberProperties.first { p -> p.name == it.name }.let {
                converter.convert(it.get(obj)!!, it.returnType.javaType)
            }
        )
    }
}

