package de.peekandpoke.karango.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import de.peekandpoke.ultra.vault.Ref
import de.peekandpoke.ultra.vault.Stored
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime

class KarangoJacksonModule : SimpleModule() {

    init {
        // SavedEntity
        addDeserializer(Stored::class.java, StoredDeserializer())

        // References
        addSerializer(Ref::class.java, EntityRefSerializer())
        addDeserializer(Ref::class.java, EntityRefDeserializer())

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
