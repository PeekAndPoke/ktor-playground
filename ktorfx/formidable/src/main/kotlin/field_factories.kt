@file:Suppress("unused")

package de.peekandpoke.ktorfx.formidable

import kotlin.reflect.KMutableProperty0

@JvmName("field_Boolean")
fun Form.field(prop: KMutableProperty0<Boolean>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toBoolean() })
        .trimmed()
        .acceptsBoolean()

@JvmName("field_Boolean?")
fun Form.field(prop: KMutableProperty0<Boolean?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { if (it.isNotEmpty()) it.toBoolean() else null })
        .trimmed()
        .acceptsBooleanOrBlank()

@JvmName("field_Byte")
fun Form.field(prop: KMutableProperty0<Byte>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toByte() })
        .trimmed()
        .acceptsByte()

@JvmName("field_Byte?")
fun Form.field(prop: KMutableProperty0<Byte?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toByteOrNull() })
        .trimmed()
        .acceptsByteOrBlank()

@JvmName("field_Char")
fun Form.field(prop: KMutableProperty0<Char>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it[0] })
        .acceptsChar()

@JvmName("field_Char?")
fun Form.field(prop: KMutableProperty0<Char?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { if (it.isNotEmpty()) it[0] else null })
        .acceptsCharOrBlank()

@JvmName("field_Short")
fun Form.field(prop: KMutableProperty0<Short>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toShort() })
        .trimmed()
        .acceptsInteger()

@JvmName("field_Short?")
fun Form.field(prop: KMutableProperty0<Short?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toShortOrNull() })
        .trimmed()
        .acceptsIntegerOrBlank()

@JvmName("field_Int")
fun Form.field(prop: KMutableProperty0<Int>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toInt() })
        .trimmed()
        .acceptsInteger()

@JvmName("field_Int?")
fun Form.field(prop: KMutableProperty0<Int?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toIntOrNull() })
        .trimmed()
        .acceptsIntegerOrBlank()

@JvmName("field_Float")
fun Form.field(prop: KMutableProperty0<Float>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toFloat() })
        .trimmed()
        .acceptsFloat()

@JvmName("field_Float?")
fun Form.field(prop: KMutableProperty0<Float?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toFloatOrNull() })
        .trimmed()
        .acceptsFloatOrBlank()

@JvmName("field_Double")
fun Form.field(prop: KMutableProperty0<Double>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toDouble() })
        .trimmed()
        .acceptsDouble()

@JvmName("field_Double?")
fun Form.field(prop: KMutableProperty0<Double?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toDoubleOrNull() })
        .trimmed()
        .acceptsDoubleOrBlank()

@JvmName("field_String")
fun Form.field(prop: KMutableProperty0<String>) = add(prop.name, prop.getter(), prop.setter, { it }, { it })

@JvmName("field_String?")
fun Form.field(prop: KMutableProperty0<String?>) = add(prop.name, prop.getter(), prop.setter, { it ?: "" }, { s -> if (s.isNotEmpty()) s else null })
