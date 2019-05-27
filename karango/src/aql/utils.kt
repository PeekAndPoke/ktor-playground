package de.peekandpoke.karango.aql

import kotlin.reflect.jvm.reflect


val String.ensureKey get() = if (contains('/')) split('/')[1] else this

fun <R, T: Function<R>> T.nthParamName(n : Int) : String {

    val refl = this.reflect()
    val params = refl?.parameters

    return params?.get(n)?.name ?: "it"
}
