package de.peekandpoke.ktorfx.broker

import io.ktor.application.ApplicationCall
import io.ktor.response.respondRedirect

/**
 * Responds to a client with a `301 Moved Permanently` or `302 Found` redirect
 */
suspend fun <T : Any> ApplicationCall.respondRedirect(route: TypedRoute.Bound<T>, permanent: Boolean = false) {

    respondRedirect(typedRouteRenderer.render(route), permanent)
}
