package de.peekandpoke.ktorfx.broker

import java.lang.reflect.Type

interface IncomingParamConverter {

    fun canHandle(type: Type): Boolean

    fun convert(value: String, type: Type): Any
}
