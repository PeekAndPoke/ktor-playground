package de.peekandpoke.ktorfx.broker

import java.lang.reflect.Type

interface OutgoingParamConverter {

    fun canHandle(type: Type): Boolean

    fun convert(value: Any, type: Type): String
}
