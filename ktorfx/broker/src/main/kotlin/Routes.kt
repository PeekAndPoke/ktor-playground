package de.peekandpoke.ktorfx.broker

/**
 * Base class for all route collections
 */
abstract class Routes(val converter: OutgoingConverter, private val mountPoint: String = "") {

    fun route(uri: String) = mountPoint + uri

    inline fun <reified T : Any> route(uri: String) = TypedRoute(converter, T::class, route(uri))
}
