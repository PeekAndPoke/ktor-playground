package de.peekandpoke.formidable

fun isInteger(value: String) = value.toIntOrNull() != null

fun RenderableField.acceptsInteger() = accepting(::isInteger, "Must be an Integer")

fun isIntegerOrBlank(value: String) = value.isBlank() || value.toIntOrNull() != null

fun RenderableField.acceptsIntegerOrBlank() = accepting(::isIntegerOrBlank, "Must be an Integer or blank")
