package de.peekandpoke.karango.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.aql.DOCUMENT
import de.peekandpoke.karango.aql.RETURN

/**
 * Created by gerk on 23.05.19 09:58
 */
internal class EntityRefSerializer : StdSerializer<Entity>(Entity::class.java) {

    override fun serialize(value: Entity, jgen: JsonGenerator, provider: SerializerProvider) {
        jgen.writeString(value._id)
    }
}

internal class EntityRefDeserializer @JvmOverloads constructor(
    private val db: Db? = null,
    private val cache: RefCache? = null,
    type: Class<*>? = null
) : StdDeserializer<Entity>(type), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext, property: BeanProperty): JsonDeserializer<*> {

        return EntityRefDeserializer(
            ctxt.findInjectableValue("db", null, null) as Db,
            ctxt.findInjectableValue("cache", null, null) as RefCache,
            property.type?.rawClass
        )
    }

    override fun isCachable(): Boolean {
        return false
    }

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Entity? {

        if (db == null || cache == null) {
            return null
        }

        val node: JsonNode = jp.codec.readTree(jp)

        val id: String = node.textValue() ?: return null

        return cache.entries.getOrPut(id) {

            @Suppress("UNCHECKED_CAST")
            db.queryFirst {
                RETURN(
                    DOCUMENT(_valueClass as Class<Entity>, id)
                )
            }
        }
    }
}

internal class RefCache {
    val entries = mutableMapOf<String, Entity?>()
}
