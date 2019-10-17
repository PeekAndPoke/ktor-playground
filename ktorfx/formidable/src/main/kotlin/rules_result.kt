package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.polyglot.Translatable

/**
 * Makes the field accept only results within the given [ClosedRange]
 */
fun <T : Comparable<T>> FormField<T>.resultingInRange(
    range: ClosedRange<T>,
    message: Translatable = FormidableI18n.must_be_in_range(range)
) =
    addResultRule(message) { range.contains(it) }

/**
 * Makes the field accept only results within the given [ClosedRange] or blank input
 */
@JvmName("resultingInRange?")
fun <T : Comparable<T>> FormField<T?>.resultingInRange(
    range: ClosedRange<T>,
    message: Translatable = FormidableI18n.must_be_in_range_or_blank(range)
) =
    addResultRule(message) { it == null || range.contains(it) }

/**
 * Makes the field accept only results the are present in the given [Set]
 */
fun <T> FormField<T>.resultingInAnyOf(
    options: Set<T>,
    message: Translatable = FormidableI18n.invalid_value
) =
    addResultRule(message) { options.contains(it) }

