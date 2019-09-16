package de.peekandpoke.karango.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

private val utc = ZoneId.of("UTC")

class LocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator, provider: SerializerProvider?) {

        if (value == null) {
            gen.writeNull()
        } else {
            val zoned = value.atZone(utc)

            gen.writeStartObject()

            gen.writeObjectField("ts", zoned.toInstant().toEpochMilli())
            gen.writeObjectField("human", zoned.toString())
            gen.writeObjectField("timezone", zoned.zone.id)

            gen.writeEndObject()
        }
    }
}

class LocalDateTimeDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDateTime? {

        val node = p.codec.readTree<JsonNode>(p)

        if (node.isObject) {
            val tsNode = node.get("ts")

            if (tsNode != null && tsNode.isNumber) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(tsNode.longValue()), utc)
            }
        }

        return null
    }
}

class InstantSerializer : StdSerializer<Instant>(Instant::class.java) {
    override fun serialize(value: Instant?, gen: JsonGenerator, provider: SerializerProvider?) {

        if (value == null) {
            gen.writeNull()
        } else {
            gen.writeStartObject()

            gen.writeObjectField("ts", value.toEpochMilli())
            gen.writeObjectField("human", value.toString())
            gen.writeObjectField("timezone", utc.id)

            gen.writeEndObject()
        }
    }
}

class InstantDeserializer : StdDeserializer<Instant>(Instant::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): Instant? {

        val node = p.codec.readTree<JsonNode>(p)

        if (node.isObject) {
            val tsNode = node.get("ts")

            if (tsNode != null && tsNode.isNumber) {
                return Instant.ofEpochMilli(tsNode.longValue())
            }
        }

        return null
    }
}

class ZonedDateTimeSerializer : StdSerializer<ZonedDateTime>(ZonedDateTime::class.java) {
    override fun serialize(value: ZonedDateTime?, gen: JsonGenerator, provider: SerializerProvider?) {

        if (value == null) {
            gen.writeNull()
        } else {
            gen.writeStartObject()

            gen.writeObjectField("ts", value.toInstant().toEpochMilli())
            gen.writeObjectField("human", value.toString())
            gen.writeObjectField("timezone", value.zone.id)

            gen.writeEndObject()
        }
    }
}

class ZonedDateTimeDeserializer : StdDeserializer<ZonedDateTime>(ZonedDateTime::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): ZonedDateTime? {

        val node = p.codec.readTree<JsonNode>(p)

        if (node.isObject) {

            val tsNode = node.get("ts")
            val zoneNode = node.get("timezone")

            if (tsNode != null && tsNode.isNumber && zoneNode != null && zoneNode.isTextual) {

                val ts = tsNode.longValue()
                val timezone = ZoneId.of(zoneNode.textValue())

                return ZonedDateTime.ofInstant(Instant.ofEpochMilli(ts), timezone)
            }
        }

        return null
    }
}
