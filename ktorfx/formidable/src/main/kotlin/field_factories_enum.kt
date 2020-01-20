package de.peekandpoke.ktorfx.formidable

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0

/**
 * Adds a field for am Enum property
 */
@JvmName("field_Enum")
fun <T : Enum<T>> Form.enum(prop: KMutableProperty0<T>): FormField<T> {

    val cls = prop.returnType.classifier as KClass<*>
    val enumValues = cls.java.enumConstants

    return add(
        FormFieldImpl(
            this,
            prop,
            { it.toString() },
            {
                @Suppress("UNCHECKED_CAST")
                enumValues.firstOrNull { enumValue -> enumValue.toString() == it } as T
            }
        )
    )
}
