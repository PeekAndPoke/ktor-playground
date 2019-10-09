package de.peekandpoke.ktorfx.formidable

fun <T> FormField<T>.trimmed() = addMapper { it.trim() }

fun <T> FormField<T>.trimmed(vararg chars: Char) = addMapper { it.trim(*chars) }

fun <T> FormField<T>.trimmed(predicate: (Char) -> Boolean) = addMapper { it.trim(predicate) }

fun <T> FormField<T>.trimmedStart() = addMapper { it.trimStart() }

fun <T> FormField<T>.trimmedStart(vararg chars: Char) = addMapper { it.trimStart(*chars) }

fun <T> FormField<T>.trimmedStart(predicate: (Char) -> Boolean) = addMapper { it.trimStart(predicate) }

fun <T> FormField<T>.trimmedEnd() = addMapper { it.trimEnd() }

fun <T> FormField<T>.trimmedEnd(vararg chars: Char) = addMapper { it.trimEnd(*chars) }

fun <T> FormField<T>.trimmedEnd(predicate: (Char) -> Boolean) = addMapper { it.trimEnd(predicate) }
