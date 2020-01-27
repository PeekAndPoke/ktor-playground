package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.mutator.MutatorBase
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0

/**
 * Adds a field for an any nullable object property
 */
@JvmName("field_Object?")
fun <T : Any> Form.field(
    prop: KMutableProperty0<T?>,
    toStr: (T?) -> String,
    fromStr: (String) -> T?
): FormField<T?> {

    return add(FormFieldImpl(this, prop, toStr, fromStr))
}

/**
 * Adds a field for an any nullable mutator property
 */
@JvmName("field_Mutable?")
fun <I : Any, T : MutatorBase<I, *>> Form.field(
    prop: KProperty0<T?>,
    toStr: (I?) -> String,
    fromStr: (String) -> I?
): FormField<I?> {

    val mutator = prop.getter()

    return add(
        FormFieldImpl(
            parent = this,
            name = prop.name,
            value = mutator?.getResult(),
            setter = {
                if (it != null) {
                    mutator?.plusAssign(it)
                }
            },
            toStr = toStr,
            fromStr = fromStr
        )
    )
}
