package de.peekandpoke.karango.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import de.peekandpoke.karango.Stored

class SavedEntityDeserializer(
    private val type: JavaType? = null,
    private val mapper: ObjectMapper? = null
) : StdDeserializer<Stored<*>>(Stored::class.java), ContextualDeserializer {

    override fun createContextual(ctxt: DeserializationContext, property: BeanProperty?): JsonDeserializer<*> {

//        println("----------------------------------------------------------------------------------------------")
//        println("CREATING SavedEntityDeserializer for ${ctxt.contextualType}")
//
//        println(ctxt.contextualType)
//        println(property?.member?.type)

        return SavedEntityDeserializer(
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

        val value = mapper!!.convertValue<Any>(valueAsMap, innerType)
//        println(value)

        return Stored(
            valueAsMap["_id"] as String,
            valueAsMap["_key"] as String,
            valueAsMap["_rev"] as String,
            value
        )
    }
}
