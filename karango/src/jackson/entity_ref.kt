package de.peekandpoke.karango.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.RefCache

/**
 * TODO: fix me
 */
internal class EntityRefSerializer : StdSerializer<Any>(Any::class.java) {

    override fun serialize(value: Any, jgen: JsonGenerator, provider: SerializerProvider) {
        jgen.writeNull()
//        jgen.writeString(value._id)
    }
}

internal class EntityRefDeserializer @JvmOverloads constructor(
    private val db: Database? = null,
    private val cache: RefCache? = null,
    type: Class<*>? = null
) : StdDeserializer<Any>(type), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext, property: BeanProperty): JsonDeserializer<*> {

        return EntityRefDeserializer(
            ctxt.findInjectableValue("database", null, null) as Database,
            ctxt.findInjectableValue("cache", null, null) as RefCache,
            property.type?.rawClass
        )
    }

    override fun isCachable(): Boolean {
        return false
    }

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Any? {

        return null

//        if (db == null || cache == null) {
//            return null
//        }
//
//        val node: JsonNode = jp.codec.readTree(jp)
//
//        val id: String = node.textValue() ?: return null
//
//        return cache.entries.getOrPut(id) {
//
//            @Suppress("UNCHECKED_CAST")
//            db.queryFirst {
//                RETURN(
//                    DOCUMENT(_valueClass as Class<Entity>, id)
//                )
//            }
//        }
    }
}

