package de.peekandpoke._sortme_.karsten

fun String.camelCaseSplit() = "(?<=[a-z])(?=[A-Z])".toRegex().split(this)

fun String.camelCaseDivide(divider: String = " ") = camelCaseSplit().joinToString(divider)
