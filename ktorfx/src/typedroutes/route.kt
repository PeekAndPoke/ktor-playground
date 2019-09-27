package io.ultra.ktor_tools.typedroutes

import de.peekandpoke.ultra.common.toUri
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

        // TODO: check that all parameters can be handled by the conversion service

        // check that all non optional parameters are present in the route
        val missingInUri = type.primaryConstructor!!.parameters
            .filter { !it.isOptional }
            .map { it.name }
            .filter { !parsedParams.contains(it) }

        if (missingInUri.isNotEmpty()) {
            error("Route '$uri' is missing parameters for route object '${type.qualifiedName}': ${missingInUri.joinToString(", ")}")
        }
    }

    operator fun invoke(obj: T): String {

        var result = uri

        val queryParams = mutableMapOf<String, String>()

        // replace route params or build up query parameters
        type.declaredMemberProperties.forEach {

            val converted = converter.convert(it.get(obj) ?: "", it.returnType.javaType)

            if (parsedParams.contains(it.name)) {
                result = result.replace("{${it.name}}", converted)
            } else {
                queryParams[it.name] = converted
            }
        }

        // finally append query params if there are any
        return result.toUri(queryParams)
    }
}

