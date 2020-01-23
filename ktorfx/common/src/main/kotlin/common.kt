package de.peekandpoke.ktorfx.common

import kotlin.reflect.KType

// TODO; move to ultra-common

//  REFLECTION  ////////////////////////////////////////////////

val KType.isPrimitive
    get() : Boolean = classifier in listOf(
        Boolean::class,
        Char::class,
        Byte::class,
        Short::class,
        Int::class,
        Long::class,
        Float::class,
        Double::class
    )

val KType.isPrimitiveOrString get(): Boolean = classifier == String::class || isPrimitive
