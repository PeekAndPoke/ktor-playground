package io.ultra.ktor_tools.typedroutes

import io.ktor.application.ApplicationCall
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

class IncomingConverter(converters: List<IncomingParamConverter>) {

    private val converters = converters.plus(IncomingPrimitiveConverter())

    fun convert(call: ApplicationCall, type: KClass<*>): Any {

        val routeParams = call.parameters
        val queryParams = call.request.queryParameters

        // check if all non optional values are provided
        val callParams = type.primaryConstructor!!.parameters
            .map { it to (routeParams[it.name!!] ?: queryParams[it.name!!]) }
            .filter { (_, v) -> v is String }
            .map { (k, v) -> k to convert(v as String, k.type.javaType) }
            .toMap()

        return type.primaryConstructor!!.callBy(callParams)
    }

    private fun convert(value: String, type: Type): Any {
        // TODO: do some caching for the look up
        return converters.first { it.canHandle(type) }.convert(value, type)
    }
}

class OutgoingConverter(converters: List<OutgoingParamConverter>) {

    private val converters = converters.plus(OutgoingPrimitiveConverter())

    fun convert(value: Any, type: Type): String {
        // TODO: do some caching for the look up
        return converters.first { it.canHandle(type) }.convert(value, type)
    }
}

interface IncomingParamConverter {

    fun canHandle(type: Type): Boolean

    fun convert(value: String, type: Type): Any
}

interface OutgoingParamConverter {

    fun canHandle(type: Type): Boolean

    fun convert(value: Any, type: Type): String
}

class IncomingPrimitiveConverter : IncomingParamConverter {

    override fun canHandle(type: Type): Boolean = true

    // TODO: make this more beautiful ... probably multiple converters
    override fun convert(value: String, type: Type): Any = when (type) {
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

class OutgoingPrimitiveConverter : OutgoingParamConverter {

    override fun canHandle(type: Type): Boolean = true

    override fun convert(value: Any, type: Type): String = value.toString()
}
