package de.peekandpoke.ktorfx.broker

import kotlin.reflect.KClass

/**
 * Base class for all route collections
 */
abstract class Routes(
    private val converter: OutgoingConverter,
    private val mountPoint: String
) {

    fun route(uri: String) = mountPoint + uri

    fun <T : Any> route(type: KClass<T>, uri: String) = TypedRoute(converter, type, route(uri))

    inline fun <reified T : Any> route(uri: String) = route(T::class, uri)
}
