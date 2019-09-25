package io.ultra.ktor_tools.typedroutes

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpMethod
import io.ktor.routing.Route
import io.ktor.routing.method
import io.ktor.routing.route
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.incomingConverter


fun <T : Any> Route.handle(route: TypedRoute<T>, body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit) {
    handle {
        @Suppress("UNCHECKED_CAST")
        body(incomingConverter.convert(call.parameters, route.type) as T)
    }
}

fun <T : Any> Route.get(route: TypedRoute<T>, body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit): Route {
    return route(route.uri, HttpMethod.Get) { handle(route, body) }
}

fun <T : Any> Route.getOrPost(route: TypedRoute<T>, body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit): Route {
    return route(route.uri) {
        method(HttpMethod.Get) { handle(route, body) }
        method(HttpMethod.Post) { handle(route, body) }
    }
}

fun Route.getOrPost(uri: String, body: suspend PipelineContext<Unit, ApplicationCall>.() -> Unit): Route {
    return route(uri) {
        method(HttpMethod.Get) { handle { body() } }
        method(HttpMethod.Post) { handle { body() } }
    }
}
