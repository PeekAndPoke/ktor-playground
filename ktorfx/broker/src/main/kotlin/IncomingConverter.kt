package de.peekandpoke.ktorfx.broker

import de.peekandpoke.ultra.common.Lookup
import io.ktor.http.Parameters
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

/**
 * Converter for incoming url data
 */
class IncomingConverter(
    private val lookUp: IncomingConverterLookup,
    private val converters: Lookup<IncomingParamConverter>
) {
    fun convert(routeParams: Parameters, queryParams: Parameters, type: KClass<*>): Any {

        // check if all non optional values are provided
        val callParams = type.primaryConstructor!!.parameters
            .map { it to (routeParams[it.name!!] ?: queryParams[it.name!!]) }
            .filter { (_, v) -> v is String }
            .map { (k, v) -> k to convert(v as String, k.type.javaType) }
            .toMap()

        return type.primaryConstructor!!.callBy(callParams)
    }

    fun convert(value: String, type: Type): Any {

        // Get the converter class from the shared lookup
        val converterClass = lookUp.getOrPut(type) {
            converters.all().firstOrNull { it.canHandle(type) }?.let { it::class }
        }
            ?: throw NoConverterFoundException("There is no incoming param converter that can handle the type '$type'")

        return converters.get(converterClass)!!.convert(value, type)
    }
}
