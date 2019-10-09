package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.polyglot.Translatable

/**
 * Makes the field accept only non blank input (not only whitespace characters)
 */
fun <T> FormField<T>.acceptsNonBlank(message: Translatable = must_not_be_blank) = addAcceptRule(message, ::isNotBlank)

private fun isNotBlank(value: String) = value.isNotBlank()

/**
 * Make the field accept only non empty input
 */
fun <T> FormField<T>.acceptsNonEmpty(message: Translatable = must_not_be_empty) = addAcceptRule(message, ::isNotEmpty)

private fun isNotEmpty(value: String) = value.isNotEmpty()

/**
 * Makes the field accept only boolean values "true" or "false"
 */
fun <T> FormField<T>.acceptsBoolean(message: Translatable = must_be_a_boolean) = addAcceptRule(message, ::isBoolean)

fun isBoolean(value: String) = value == "true" || value == "false"

/**
 * Makes the field accept only boolean values or blank input
 */
fun <T> FormField<T?>.acceptsBooleanOrBlank(message: Translatable = must_be_a_boolean_or_blank) = addAcceptRule(message, ::isBooleanOrBlank)

fun isBooleanOrBlank(value: String) = value.isBlank() || value == "true" || value == "false"

/**
 * Makes the field accept only byte values
 */
fun <T> FormField<T>.acceptsByte(message: Translatable = must_be_a_byte) = addAcceptRule(message, ::isByte)

fun isByte(value: String) = value.toByteOrNull() != null

/**
 * Makes the field accept only byte values or blank input
 */
fun <T> FormField<T?>.acceptsByteOrBlank(message: Translatable = must_be_a_byte_or_blank) = addAcceptRule(message, ::isByteOrBlank)

fun isByteOrBlank(value: String) = value.isBlank() || isByte(value)

/**
 * Makes the field accept only char values
 */
fun <T> FormField<T>.acceptsChar(message: Translatable = must_be_a_char) = addAcceptRule(message, ::isChar)

fun isChar(value: String) = value.length == 1

/**
 * Makes the field accept only char values or blank input
 */
fun <T> FormField<T?>.acceptsCharOrBlank(message: Translatable = must_be_a_char_or_blank) = addAcceptRule(message, ::isCharOrBlank)

fun isCharOrBlank(value: String) = value.isBlank() || isChar(value)

/**
 * Makes the field accept only integer values
 */
fun <T> FormField<T>.acceptsInteger(message: Translatable = must_be_an_integer) = addAcceptRule(message, ::isInteger)

fun isInteger(value: String) = value.toIntOrNull() != null

/**
 * Makes the field accept only integer values or blank input
 */
fun <T> FormField<T?>.acceptsIntegerOrBlank(message: Translatable = must_be_an_integer_or_blank) = addAcceptRule(message, ::isIntegerOrBlank)

fun isIntegerOrBlank(value: String) = value.isBlank() || isInteger(value)

/**
 * Makes the field accept only double values
 */
fun <T> FormField<T>.acceptsFloat(message: Translatable = must_be_a_float) = addAcceptRule(message, ::isFloat)

fun isFloat(value: String) = value.toFloatOrNull() != null

/**
 * Makes the field accept only float values or blank input
 */
fun <T> FormField<T?>.acceptsFloatOrBlank(message: Translatable = must_be_a_float_or_blank) = addAcceptRule(message, ::isFloatOrBlank)

fun isFloatOrBlank(value: String) = value.isBlank() || isFloat(value)

/**
 * Makes the field accept only double values
 */
fun <T> FormField<T>.acceptsDouble(message: Translatable = must_be_a_double) = addAcceptRule(message, ::isDouble)

fun isDouble(value: String) = value.toDoubleOrNull() != null

/**
 * Makes the field accept only double values
 */
fun <T> FormField<T?>.acceptsDoubleOrBlank(message: Translatable = must_be_a_double_or_blank) = addAcceptRule(message, ::isDoubleOrBlank)

fun isDoubleOrBlank(value: String) = value.isBlank() || isDouble(value)
