package de.peekandpoke.karango.aql

val String.ensureKey get() = if (contains('/')) split('/')[1] else this

fun String.surround(with: String) = "$with${this}$with"
