package de.peekandpoke.ktorfx.broker

import java.lang.reflect.Type

class OutgoingConverter(private val converters: List<OutgoingParamConverter>) {

    private val lookUp = mutableMapOf<Type, OutgoingParamConverter?>()

    @Suppress("FoldInitializerAndIfToElvis")
    fun convert(value: Any, type: Type): String {

        // Get the converter class from the shared lookup
        val converter = lookUp.getOrPut(type) {
            converters.firstOrNull { it.canHandle(type) }
        }
            ?: return value.toString()

        return converter.convert(value, type)
    }
}
