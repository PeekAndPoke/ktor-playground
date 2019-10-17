package de.peekandpoke.ktorfx.broker

import de.peekandpoke.ultra.common.toUri
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

data class TypedRoute<T : Any>(val converter: OutgoingConverter, val type: KClass<T>, val uri: String) {

    private val parsedParams = "\\{([^}]*)}".toRegex().findAll(uri).map { it.groupValues[1] }.toList()

    init {
        // Check that the route object has a primary constructor
        val primaryConstructor = type.primaryConstructor
            ?: throw InvalidRouteObjectException("Route '$uri': The route object '$type' must have a primary constructor")

        // Check that all properties of the route object can be handled by the outgoing converter
        val unhandled = type.declaredMemberProperties.filter { !converter.canHandle(it.returnType.javaType) }

        if (unhandled.isNotEmpty()) {
            throw InvalidRouteObjectException(
                "Route '$uri': The outgoing converter cannot handle parameters ${unhandled.map { it.name }} of route object '$type'"
            )
        }

        // Check that all non optional parameters are present in the route
        val missingInUri = primaryConstructor.parameters
            .filter { !it.isOptional }
            .map { it.name }
            .filter { !parsedParams.contains(it) }

        if (missingInUri.isNotEmpty()) {
            error("Route '$uri': misses route parameters $missingInUri for route object '${type}'")
        }
    }

    operator fun invoke(obj: T): String {

        var result = uri

        val queryParams = mutableMapOf<String, String>()

        // replace route params or build up query parameters
        type.declaredMemberProperties.forEach {

            val converted = converter.convert(it.get(obj) ?: "", it.returnType.javaType)

            // replace the route param
            if (parsedParams.contains(it.name)) {
                result = result.replace("{${it.name}}", converted)
            } else {
                // only add to the query params if the value is non null
                if (it.get(obj) != null) {
                    queryParams[it.name] = converted
                }
            }
        }

        // finally append query params if there are any
        return result.toUri(queryParams)
    }
}
