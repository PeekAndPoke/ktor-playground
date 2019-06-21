package de.peekandpoke.formidable

fun FormField<Int>.inRange(range: IntRange, message: String = "Must be in range $range") =
    resultingIn({ range.contains(it) }, message)

@JvmName("inRange?")
fun FormField<Int?>.inRange(range: IntRange, message: String = "Must be in range $range") =
    resultingIn({ it == null || range.contains(it) }, message)

