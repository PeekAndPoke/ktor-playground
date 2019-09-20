package de.peekandpoke.karango.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.EntityCache
import de.peekandpoke.ultra.vault.Ref

/**
 * TODO: fix me
 */
internal class EntityRefSerializer : StdSerializer<Ref<*>>(Ref::class.java) {

    override fun serialize(value: Ref<*>?, gen: JsonGenerator, provider: SerializerProvider) {

        if (value == null) {
            gen.writeNull()
        } else {
            gen.writeString(value._id)
        }
    }
}

internal class EntityRefDeserializer @JvmOverloads constructor(
    private val db: Database? = null,
    private val cache: EntityCache? = null,
    type: Class<*>? = null
) : StdDeserializer<Ref<*>>(type), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext, property: BeanProperty?): JsonDeserializer<*> {

//        println("-----------------------------------------------------------------------------------------------")
//        println("Ref for prop: ${property?.name} -> ${property?.type}")

        val db = ctxt.findInjectableValue("database", null, null) as Database
        val cache = ctxt.findInjectableValue("entityCache", null, null) as EntityCache

        return EntityRefDeserializer(db, cache, property?.type?.rawClass)
    }

    override fun isCachable(): Boolean {
        return false
    }

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Ref<*>? {

        if (db == null || cache == null) {
            return null
        }

        val node: JsonNode = jp.codec.readTree(jp)

        val id = node.textValue() ?: return null

        val coll = id.split("/").first()

        return cache.getOrPut(id) { db.getRepository(coll)?.findById(id) }?.asRef
    }
}

