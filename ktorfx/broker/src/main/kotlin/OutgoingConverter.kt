package de.peekandpoke.ktorfx.broker

import java.lang.reflect.Type

class OutgoingConverter(private val converters: List<OutgoingParamConverter>) {

    private val lookUp = mutableMapOf<Type, OutgoingParamConverter?>()

    fun canHandle(type: Type): Boolean = findConverter(type) != null

    fun convert(value: Any, type: Type): String {

        return findConverter(type)?.convert(value, type)
            ?: throw NoConverterFoundException("There is no outgoing converter the can handle the type '$type'")
    }

    private fun findConverter(type: Type) = lookUp.getOrPut(type) {
        converters.firstOrNull { it.canHandle(type) }
    }
}
