package de.peekandpoke.karango

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import de.peekandpoke.karango.aql.DOCUMENT

/**
 * Created by gerk on 23.05.19 09:58
 */
class EntityRefSerializer : StdSerializer<Entity>(Entity::class.java) {

    override fun serialize(value: Entity, jgen: JsonGenerator, provider: SerializerProvider) {
        jgen.writeString(value._id)
    }
}

class EntityRefDeserializer @JvmOverloads constructor(val db: Db? = null, type: Class<*>? = null) : StdDeserializer<Entity>(type), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext?, property: BeanProperty?): JsonDeserializer<*> {

        println("createContextual")

        return EntityRefDeserializer(
            ctxt?.findInjectableValue("__db", null, null) as Db,
            property?.type?.rawClass
        )
    }

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Entity? {

        val node: JsonNode = jp.codec.readTree(jp)

        val id: String = node.textValue() ?: return null

        ctxt.contextualType

        @Suppress("UNCHECKED_CAST")
        val result = db!!.query {
            RETURN(
                DOCUMENT(_valueClass as Class<Entity>, id)
            )
        }

        return result.firstOrNull()
    }
}
