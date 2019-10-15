package de.peekandpoke.ktorfx.broker

import de.peekandpoke.ultra.common.Lookup
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * A shared look up between incoming type and incoming param converter.
 *
 * The converters that are injected into [IncomingConverter] can be dynamic.
 * This is why we cannot cache a relation between a [Type] and an [IncomingParamConverter]
 * directly. But we can cache the relation to the [Class] of the [IncomingParamConverter]
 *
 * And the [Class] can be used to [Lookup] the correct converter
 *
 * @see IncomingConverter
 */
class IncomingConverterLookup {

    private val typeLookup = mutableMapOf<Type, KClass<out IncomingParamConverter>?>()

    fun getOrPut(type: Type, defaultValue: () -> KClass<out IncomingParamConverter>?) = typeLookup.getOrPut(type, defaultValue)
}
