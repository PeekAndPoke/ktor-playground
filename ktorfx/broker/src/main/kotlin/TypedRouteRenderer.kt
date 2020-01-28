package de.peekandpoke.ktorfx.broker

import de.peekandpoke.ultra.common.toUri
import de.peekandpoke.ultra.security.csrf.CsrfProtection
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaType

class TypedRouteRenderer(
    private val converter: OutgoingConverter,
    private val csrf: CsrfProtection
) {
    operator fun <T : Any> invoke(route: TypedRoute.Bound<T>) = render(route.route, route.obj)

    fun <T : Any> render(route: TypedRoute.Bound<T>) = render(route.route, route.obj)

    fun <T : Any> render(route: TypedRoute<T>, obj: T): String {

        var result = route.uri

        val queryParams = mutableMapOf<String, String>()

        // Special handling for csrf tokens
        if (result.contains("{csrf}")) {
            result = result.replace("{csrf}", csrf.createToken(obj.toString()))
        }

        // replace route params or build up query parameters
        route.type.declaredMemberProperties.forEach {

            val converted = converter.convert(it.get(obj) ?: "", it.returnType.javaType)

            // replace the route param
            if (route.parsedParams.contains(it.name)) {
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
