package de.peekandpoke.ultra.vault.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.EntityCache
import de.peekandpoke.ultra.vault.Ref

internal class RefDeserializer @JvmOverloads constructor(
    private val db: Database? = null,
    private val cache: EntityCache? = null,
    type: Class<*>? = null
) : StdDeserializer<Ref<*>>(type), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext, property: BeanProperty?): JsonDeserializer<*> {

//        println("-----------------------------------------------------------------------------------------------")
//        println("Ref for prop: ${property?.name} -> ${property?.type}")

        val db = ctxt.findInjectableValue("database", null, null) as Database
        val cache = ctxt.findInjectableValue("entityCache", null, null) as EntityCache

        return RefDeserializer(db, cache, property?.type?.rawClass)
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
