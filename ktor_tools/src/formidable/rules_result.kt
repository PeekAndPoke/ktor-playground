package io.ultra.ktor_tools.formidable

import io.ultra.polyglot.Translatable

fun <T : Comparable<T>> FormField<T>.resultingInRange(range: ClosedRange<T>, message: Translatable = must_be_in_range(range)) =
    resultingIn(message) { range.contains(it) }

@JvmName("resultingInRange?")
fun <T : Comparable<T>> FormField<T?>.resultingInRange(range: ClosedRange<T>, message: Translatable = must_be_in_range_or_blank(range)) =
    resultingIn(message) { it == null || range.contains(it) }

fun <T> FormField<T>.resultingInAnyOf(options: List<T>, message: Translatable = invalid_value) =
    resultingIn(message) { options.contains(it) }

