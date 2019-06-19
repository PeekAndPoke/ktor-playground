package de.peekandpoke.formidable

import kotlin.reflect.KMutableProperty0


@JvmName("text_Byte")
fun Form.text(prop: KMutableProperty0<Byte>) = text(prop.name, prop.getter().toString(), prop.setter, { it.toByte() })
    .acceptsInteger()

@JvmName("text_Byte_nullable")
fun Form.text(prop: KMutableProperty0<Byte?>) = text(prop.name, prop.getter()?.toString() ?: "", prop.setter, { it.toByteOrNull() })
    .acceptsIntegerOrBlank()

@JvmName("text_Short")
fun Form.text(prop: KMutableProperty0<Short>) = text(prop.name, prop.getter().toString(), prop.setter, { it.toShort() })
    .acceptsInteger()

@JvmName("text_Short_nullable")
fun Form.text(prop: KMutableProperty0<Short?>) = text(prop.name, prop.getter()?.toString() ?: "", prop.setter, { it.toShortOrNull() })
    .acceptsIntegerOrBlank()

@JvmName("text_Int")
fun Form.text(prop: KMutableProperty0<Int>) = text(prop.name, prop.getter().toString(), prop.setter, { it.toInt() })
    .acceptsInteger()

@JvmName("text_Int_nullable")
fun Form.text(prop: KMutableProperty0<Int?>): RenderableField =
    text(prop.name, prop.getter()?.toString() ?: "", prop.setter, { it.toIntOrNull() })
        .acceptsIntegerOrBlank()

@JvmName("text_String")
fun Form.text(prop: KMutableProperty0<String>) = text(prop.name, prop.getter(), prop.setter, { it })

@JvmName("text_String_nullable")
fun Form.text(prop: KMutableProperty0<String?>) = text(prop.name, prop.getter() ?: "", prop.setter, { s -> s.takeIf { it.isNotEmpty() } })
