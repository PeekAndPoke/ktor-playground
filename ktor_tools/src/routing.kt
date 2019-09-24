package io.ultra.ktor_tools

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.handle
import io.ktor.locations.location
import io.ktor.routing.Route
import io.ktor.routing.method
import io.ktor.routing.route
import io.ktor.util.pipeline.PipelineContext
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

class ParamConversionService(converters: List<ParamConverter>) {

    private val converters = converters.plus(PrimitiveTypesConverter())

    fun convert(values: Parameters, type: KClass<*>): Any {

        return type.primaryConstructor!!.callBy(
            type.primaryConstructor!!.parameters.map {
                it to convertUriValue(values[it.name!!]!!, it.type.javaType)
            }.toMap()
        )
    }

    fun convertToUri(value: Any, type: Type): String {
        // TODO: do some caching for the look up
        return converters.first { it.canHandle(type) }.toUri(value, type)
    }

    private fun convertUriValue(value: String, type: Type): Any {
        // TODO: do some caching for the look up
        return converters.first { it.canHandle(type) }.fromUri(value, type)
    }
}

interface ParamConverter {

    fun canHandle(type: Type): Boolean

    fun toUri(value: Any, type: Type): String

    fun fromUri(value: String, type: Type): Any
}

class PrimitiveTypesConverter : ParamConverter {

    override fun canHandle(type: Type): Boolean = true

    override fun toUri(value: Any, type: Type): String {
        return value.toString()
    }

    // TODO: make this more beautiful ... probably multiple converters
    override fun fromUri(value: String, type: Type): Any = when (type) {
        Int::class.java, java.lang.Integer::class.java -> value.toInt()
        Float::class.java, java.lang.Float::class.java -> value.toFloat()
        Double::class.java, java.lang.Double::class.java -> value.toDouble()
        Long::class.java, java.lang.Long::class.java -> value.toLong()
        Boolean::class.java, java.lang.Boolean::class.java -> value.toBoolean()
        String::class.java, java.lang.String::class.java -> value
        BigDecimal::class.java -> BigDecimal(value)
        BigInteger::class.java -> BigInteger(value)
        else ->
            if (type is Class<*> && type.isEnum) {
                type.enumConstants?.firstOrNull { (it as Enum<*>).name == value }
                // TODO: custom exception
                    ?: error("Value $value is not a enum member name of $type")
            } else {
                // TODO: custom exception
                error("Type $type is not supported in default data conversion service")
            }
    }
}


data class TypedRoute<T : Any>(val converter: ParamConversionService, val type: KClass<T>, val uri: String) {

    private val parsedParams = "\\{([^}]*)}".toRegex().findAll(uri).map { it.groupValues[1] }.toList()

    init {
        if (!type.isData && type.primaryConstructor!!.parameters.isNotEmpty()) {
            error("The route object '${type.qualifiedName}' must be a data class")
        }

        if (type.primaryConstructor!!.parameters.any { it.isOptional }) {
            error("The route object '${type.qualifiedName}' must not have optional parameters")
        }

        // TODO: check that all parameters can be handles by the conversion service

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
                converter.convertToUri(it.get(obj)!!, it.returnType.javaType)
            }
        )
    }
}

abstract class Routes(val converter: ParamConversionService, private val mountPoint: String = "") {

    fun route(uri: String) = mountPoint + uri

    inline fun <reified T : Any> route(uri: String) = TypedRoute(converter, T::class, route(uri))
}

fun <T : Any> Route.handle(route: TypedRoute<T>, body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit) {
    handle {
        val converter = call.attributes[KontainerKey].get<ParamConversionService>()

        @Suppress("UNCHECKED_CAST")
        body(converter.convert(call.parameters, route.type) as T)
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


/**
 * Registers a typed handler [body] for a `GET`  OR `POST` location defined by class [T].
 *
 * Class [T] **must** be annotated with [Location].
 *
 * @param body receives an instance of typed location [T] as first parameter.
 */
@KtorExperimentalLocationsAPI
inline fun <reified T : Any> Route.getOrPost(noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit): Route {
    return location(T::class) {
        method(HttpMethod.Get) {
            handle(body)
        }
        method(HttpMethod.Post) {
            handle(body)
        }
    }
}
