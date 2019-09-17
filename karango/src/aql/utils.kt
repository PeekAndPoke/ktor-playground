package de.peekandpoke.karango.aql

import kotlin.reflect.jvm.reflect

val String.ensureKey get() = if (contains('/')) split('/')[1] else this

/**
 * Gets the name of the n'th parameter
 *
 * Notice: the first parameter has index 1
 *
 * TODO: move into ultra.common
 */
fun <R, T: Function<R>> T.nthParamName(n : Int) : String {

    val params = this.reflect()?.parameters

    return params?.get(n)?.name ?: "param$n"
}

/**
 * Replaces the value of the field [fieldName] with the result of the [builder]
 *
 * The parameter passed to the builder is the current value of the field
 */
fun <T : Any, OLD, NEW: OLD> T.replaceHiddenFieldWith(fieldName: String, builder: (OLD) -> NEW): NEW {

    // Access internal field via reflection
    val field = this::class.java.declaredFields.first { it.name == fieldName }.apply { isAccessible = true }

    @Suppress("UNCHECKED_CAST")
    val created = builder(field.get(this) as OLD)

    // write the wrapped "converters" back
    field.set(this, created)

    return created
}
