package de.peekandpoke.karango.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class KarangoDateTimeModule : SimpleModule() {

    init {
        addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
        addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
    }
}

class LocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator, provider: SerializerProvider?) {

        if (value == null) {
            gen.writeNull()
        } else {
            gen.writeNumber(Timestamp.valueOf(value).time)
        }
    }
}

class LocalDateTimeDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDateTime? {

        val node = p.codec.readTree<JsonNode>(p)

        if (node.isNumber) {

            return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(node.longValue()),
                TimeZone.getDefault().toZoneId()
            )

        } else if (node.isTextual) {
            return LocalDateTime.parse(node.textValue(), formatter)
        }

        return null
    }

    companion object {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }
}

