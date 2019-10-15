package de.peekandpoke.ktorfx.broker.converters

import de.peekandpoke.ktorfx.broker.IncomingParamConverter
import de.peekandpoke.ktorfx.broker.NoConverterFoundException
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger

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
            ?: throw NoConverterFoundException("Value '$value' is not a enum member name of '$type'")
    }
}
