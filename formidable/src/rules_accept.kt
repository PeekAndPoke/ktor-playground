package de.peekandpoke.formidable

fun isBoolean(value: String) = value == "true" || value == "false"

fun <T> FormField<T>.acceptsBoolean(message : String = "Must be a Boolean") = accepting(message, ::isBoolean)

fun isBooleanOrBlank(value: String) = value.isBlank() || value == "true" || value == "false"

fun <T> FormField<T>.acceptsBooleanOrBlank(message : String = "Must be a Boolean or blank") = accepting(message, ::isBooleanOrBlank)

fun isInteger(value: String) = value.toIntOrNull() != null

fun <T> FormField<T>.acceptsInteger(message : String = "Must be an Integer") = accepting(message, ::isInteger)

fun isIntegerOrBlank(value: String) = value.isBlank() || isInteger(value)

fun <T> FormField<T>.acceptsIntegerOrBlank(message : String = "Must be an Integer or blank") = accepting(message, ::isIntegerOrBlank)

