package de.peekandpoke.karango.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import de.peekandpoke.karango.Stored
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime

class KarangoJacksonModule : SimpleModule() {

    init {
        // SavedEntity
        addDeserializer(Stored::class.java, SavedEntityDeserializer())

        // LocalDateTime
        addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())

        // Instant
        addSerializer(Instant::class.java, InstantSerializer())
        addDeserializer(Instant::class.java, InstantDeserializer())

        // ZonedDateTime
        addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer())
        addDeserializer(ZonedDateTime::class.java, ZonedDateTimeDeserializer())
    }
}

