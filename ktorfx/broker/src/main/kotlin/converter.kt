package de.peekandpoke.ktorfx.broker

import de.peekandpoke.ultra.common.Lookup
import io.ktor.application.ApplicationCall
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

/**
 * A shared look up between incoming type and incoming converter.
 *
 * The converters that are injected into [IncomingConverter] can be dynamic.
 * This is why we cannot cache a relation between a [Type] and an [IncomingParamConverter]
 * directly. But we can cache the relation to the [Class] of the [IncomingParamConverter]
 *
 * And the [Class] can be used to [Lookup] the correct converter
 *
 * @see IncomingConverter
 */
class IncomingConverterLookup {

    private val typeLookup = mutableMapOf<Type, KClass<out IncomingParamConverter>?>()

    fun getOrPut(type: Type, defaultValue: () -> KClass<out IncomingParamConverter>?) = typeLookup.getOrPut(type, defaultValue)
}

/**
 * Converter for incoming url data
 */
class IncomingConverter(
    private val lookUp: IncomingConverterLookup,
    private val converters: Lookup<IncomingParamConverter>
) {
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

        // Get the converter class from the shared lookup
        val converterClass = lookUp.getOrPut(type) {
            converters.all().firstOrNull { it.canHandle(type) }?.let { it::class }
        }

        if (converterClass != null) {
            return converters.get(converterClass)!!.convert(value, type)
        }

        throw NoConverterFoundError("There is no incoming param converter that can handle the type '$type'")
    }
}

class OutgoingConverter(converters: List<OutgoingParamConverter>) {

    private val converters = converters.plus(OutgoingPrimitiveConverter())

    private val lookUp = mutableMapOf<Type, OutgoingParamConverter?>()

    @Suppress("FoldInitializerAndIfToElvis")
    fun convert(value: Any, type: Type): String {

        val converter = lookUp.getOrPut(type) {
            converters.firstOrNull { it.canHandle(type) }
        }

        if (converter == null) {
            return value.toString()
        }

        return converter.convert(value, type)
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

    override fun canHandle(type: Type): Boolean = type == Int::class.java ||
            type == Float::class.java ||
            type == Double::class.java ||
            type == Long::class.java ||
            type == Boolean::class.java ||
            type == String::class.java ||
            type == BigDecimal::class.java ||
            type == BigInteger::class.java ||
            (type is Class<*> && type.isEnum)

    override fun convert(value: String, type: Type): Any = when (type) {

        Int::class.java, java.lang.Integer::class.java -> value.toInt()

        Float::class.java, java.lang.Float::class.java -> value.toFloat()

        Double::class.java, java.lang.Double::class.java -> value.toDouble()

        Long::class.java, java.lang.Long::class.java -> value.toLong()

        Boolean::class.java, java.lang.Boolean::class.java -> value.toBoolean()

        String::class.java, java.lang.String::class.java -> value

        BigDecimal::class.java -> BigDecimal(value)

        BigInteger::class.java -> BigInteger(value)

        // otherwise it must be an enum
        else -> (type as Class<*>).enumConstants?.firstOrNull { (it as Enum<*>).name == value }
            ?: throw NoConverterFoundError("Value '$value' is not a enum member name of '$type'")
    }
}

class OutgoingPrimitiveConverter : OutgoingParamConverter {

    override fun canHandle(type: Type): Boolean = true

    override fun convert(value: Any, type: Type): String = value.toString()
}
