@file:Suppress("unused")

package de.peekandpoke.ktorfx.formidable

import kotlin.reflect.KMutableProperty0

@JvmName("text_Boolean")
fun Form.field(prop: KMutableProperty0<Boolean>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toBoolean() })
        .acceptsBoolean()

@JvmName("text_Boolean?")
fun Form.field(prop: KMutableProperty0<Boolean?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { if (it.isNotEmpty()) it.toBoolean() else null })
        .acceptsBooleanOrBlank()

@JvmName("text_Byte")
fun Form.field(prop: KMutableProperty0<Byte>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toByte() })
        .acceptsByte()

@JvmName("text_Byte?")
fun Form.field(prop: KMutableProperty0<Byte?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toByteOrNull() })
        .acceptsByteOrBlank()

@JvmName("text_Short")
fun Form.field(prop: KMutableProperty0<Short>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toShort() })
        .acceptsInteger()

@JvmName("text_Short?")
fun Form.field(prop: KMutableProperty0<Short?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toShortOrNull() })
        .acceptsIntegerOrBlank()

@JvmName("text_Int")
fun Form.field(prop: KMutableProperty0<Int>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toInt() })
        .acceptsInteger()

@JvmName("text_Int?")
fun Form.field(prop: KMutableProperty0<Int?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toIntOrNull() })
        .acceptsIntegerOrBlank()

@JvmName("text_String")
fun Form.field(prop: KMutableProperty0<String>) = add(prop.name, prop.getter(), prop.setter, { it }, { it })

@JvmName("text_String?")
fun Form.field(prop: KMutableProperty0<String?>) = add(prop.name, prop.getter(), prop.setter, { it ?: "" }, { s -> if (s.isNotEmpty()) s else null })
