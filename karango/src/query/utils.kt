package de.peekandpoke.karango.query

val String.ensureKey get() = if (contains('/')) split('/')[1] else this

