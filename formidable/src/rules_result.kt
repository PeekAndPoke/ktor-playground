package de.peekandpoke.formidable

fun FormField<Int>.resultingInRange(range: IntRange, message: String = "Must be in range $range") =
    resultingIn(message) { range.contains(it) }

@JvmName("resultingInRange?")
fun FormField<Int?>.resultingInRange(range: IntRange, message: String = "Must be in range $range") =
    resultingIn(message) { it == null || range.contains(it) }


fun <T> FormField<T>.resultingInAnyOf(options: List<T>, message: String = "Invalid value") =
    resultingIn(message) { options.contains(it) }
