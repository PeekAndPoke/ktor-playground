package de.peekandpoke.ktorfx.broker.converters

import de.peekandpoke.ktorfx.broker.IncomingParamConverter
import de.peekandpoke.ktorfx.broker.OutgoingParamConverter
import java.lang.reflect.Type
import java.time.*

private fun supportsType(type: Type): Boolean = type == Instant::class.java ||
        type == LocalDate::class.java ||
        type == LocalTime::class.java ||
        type == LocalDateTime::class.java ||
        type == ZonedDateTime::class.java


class IncomingJavaTimeConverter : IncomingParamConverter {

    override fun canHandle(type: Type): Boolean = supportsType(type)

    override fun convert(value: String, type: Type): Any {

        return when (type) {

            Instant::class.java -> Instant.parse(value)

            LocalDate::class.java -> LocalDate.parse(value)

            LocalTime::class.java -> LocalTime.parse(value)

            LocalDateTime::class.java -> LocalDateTime.parse(value)

            else -> ZonedDateTime.parse(value)
        }
    }
}

class OutgoingJavaTimeConverter : OutgoingParamConverter {

    override fun canHandle(type: Type): Boolean = supportsType(type)

    override fun convert(value: Any, type: Type): String = value.toString()
}
