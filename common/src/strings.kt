package de.peekandpoke.ultra.common

fun String.surround(with: String) = "$with${this}$with"

fun String.ucFirst(): String {

    if (length == 0) return this

    return substring(0, 1).toUpperCase() + substring(1)
}

fun String.lcFirst(): String {

    if (length == 0) return this

    return substring(0, 1).toLowerCase() + substring(1)
}
