package de.peekandpoke.formidable

fun isBoolean(value: String) = value == "true" || value == "false"

fun <T> FormField<T>.acceptsBoolean(message : String = "Must be an Boolean") = accepting(::isBoolean, message)

fun isBooleanOrBlank(value: String) = value.isBlank() || value == "true" || value == "false"

fun <T> FormField<T>.acceptsBooleanOrBlank(message : String = "Must be an Boolean or blank") = accepting(::isBooleanOrBlank, message)

fun isInteger(value: String) = value.toIntOrNull() != null

fun <T> FormField<T>.acceptsInteger(message : String = "Must be an Integer") = accepting(::isInteger, message)

fun isIntegerOrBlank(value: String) = value.isBlank() || isInteger(value)

fun <T> FormField<T>.acceptsIntegerOrBlank(message : String = "Must be an Integer or blank") = accepting(::isIntegerOrBlank, message)

