package de.peekandpoke.ktorfx.broker

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

data class TypedRoute<T : Any>(
    val converter: OutgoingConverter,
    val type: KClass<T>,
    val uri: String
) {

    /**
     * A typed route bound to an object
     */
    data class Bound<T : Any>(val route: TypedRoute<T>, val obj: T)

    val parsedParams = "\\{([^}]*)}".toRegex().findAll(uri).map { it.groupValues[1] }.toList()

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

    operator fun invoke(obj: T) = bind(obj)

    fun bind(obj: T) = Bound(this, obj)
}
