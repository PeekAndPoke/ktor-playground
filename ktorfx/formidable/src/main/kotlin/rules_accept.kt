package de.peekandpoke.ktorfx.formidable

import io.ultra.polyglot.Translatable

fun isBoolean(value: String) = value == "true" || value == "false"

fun <T> FormField<T>.acceptsBoolean(message: Translatable = must_be_a_boolean) = accepting(message, ::isBoolean)

fun isBooleanOrBlank(value: String) = value.isBlank() || value == "true" || value == "false"

fun <T> FormField<T?>.acceptsBooleanOrBlank(message: Translatable = must_be_a_boolean_or_blank) = accepting(message, ::isBooleanOrBlank)

fun isByte(value: String) = value.toByteOrNull() != null

fun <T> FormField<T>.acceptsByte(message: Translatable = must_be_a_byte) = accepting(message, ::isByte)

fun isByteOrBlank(value: String) = value.isBlank() || isByte(value)

fun <T> FormField<T?>.acceptsByteOrBlank(message: Translatable = must_be_a_byte_or_blank) = accepting(message, ::isByteOrBlank)

fun isInteger(value: String) = value.toIntOrNull() != null

fun <T> FormField<T>.acceptsInteger(message: Translatable = must_be_an_integer) = accepting(message, ::isInteger)

fun isIntegerOrBlank(value: String) = value.isBlank() || isInteger(value)

fun <T> FormField<T?>.acceptsIntegerOrBlank(message: Translatable = must_be_an_integer_or_blank) = accepting(message, ::isIntegerOrBlank)

fun isNotBlank(value: String) = value.isNotBlank()

fun <T> FormField<T>.acceptsNonBlank(message: Translatable = must_not_be_blank) = accepting(message, ::isNotBlank)
