package de.peekandpoke.ultra.vault.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.StorableMeta
import de.peekandpoke.ultra.vault.Stored

/**
 * Custom serializer for Stored<T>
 *
 * While @JsonUnwrap work fine for serialization, it does not for deserialization
 *
 * The @JsonUnwrap annotation is not supported for creator properties yet.
 *
 * The error we got was:
 *
 * Caused by:
 * com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot define Creator property "value" as `@JsonUnwrapped`: combination not yet supported
 *  at [Source: UNKNOWN; line: -1, column: -1]
 *  at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:67)
 *  at com.fasterxml.jackson.databind.DeserializationContext.reportBadDefinition(DeserializationContext.java:1452)
 *  at com.fasterxml.jackson.databind.deser.BeanDeserializerBase._findPropertyUnwrapper(BeanDeserializerBase.java:836)
 *  at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.resolve(BeanDeserializerBase.java:494)
 *  at com.fasterxml.jackson.databind.deser.DeserializerCache._createAndCache2(DeserializerCache.java:293)
 *  at com.fasterxml.jackson.databind.deser.DeserializerCache._createAndCacheValueDeserializer(DeserializerCache.java:244)
 *  at com.fasterxml.jackson.databind.deser.DeserializerCache.findValueDeserializer(DeserializerCache.java:142)
 *  at com.fasterxml.jackson.databind.DeserializationContext.findRootValueDeserializer(DeserializationContext.java:477)
 *  at com.fasterxml.jackson.databind.ObjectMapper._findRootDeserializer(ObjectMapper.java:4190)
 *  at com.fasterxml.jackson.databind.ObjectMapper._convert(ObjectMapper.java:3743)
 */
internal class StoredDeserializer(
    private val type: JavaType? = null,
    private val mapper: ObjectMapper? = null
) : StdDeserializer<Stored<*>>(Stored::class.java), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext, property: BeanProperty?): JsonDeserializer<*> {

//        println("----------------------------------------------------------------------------------------------")
//        println("CREATING ${this::class.java.simpleName} for ${ctxt.contextualType}")
//
//        println(ctxt.contextualType)
//        println(property?.member?.type)

        return StoredDeserializer(
            ctxt.contextualType,
            ctxt.findInjectableValue("mapper", null, null) as ObjectMapper
        )
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Stored<*>? {

//        println("----------------------------------------------------------------------------------------------")
        val innerType = type!!.bindings.typeParameters[0]
//        println(innerType)
        val innerDeser = ctxt.findRootValueDeserializer(ctxt.constructType(Any::class.java))
//        println(innerDeser)
        val valueAsMap = innerDeser.deserialize(p, ctxt)
//        println(valueAsMap)

        if (valueAsMap == null || valueAsMap !is Map<*, *>) {
            return null
        }

        // convert the entity
        val value = mapper!!.convertValue<Any>(valueAsMap, innerType)

        // convert the meta data
        val meta: StorableMeta? = valueAsMap[Storable<*>::_meta.name]
            ?.takeIf { it is Map<*, *> }
            ?.let { mapper.convertValue(it, StorableMeta::class.java) }

        return Stored(
            value,
            valueAsMap["_id"] as String,
            valueAsMap["_key"] as String,
            valueAsMap["_rev"] as String,
            meta
        )
    }
}
