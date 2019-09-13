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
