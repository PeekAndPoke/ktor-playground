package de.peekandpoke.karango.aql

import kotlin.reflect.jvm.reflect

fun String.ucFirst(): String {

    if (length == 0) return this

    return substring(0, 1).toUpperCase() + substring(1)
}

val String.ensureKey get() = if (contains('/')) split('/')[1] else this

fun String.surround(with: String) = "$with${this}$with"


fun <R, T: Function<R>> T.nthParamName(n : Int) : String {
    
    val refl = this.reflect()
    val params = refl?.parameters
    
    return params?.get(n)?.name ?: "it"
}
