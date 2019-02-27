@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql


/**
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#concat
 */
fun CONCAT(first: Expression<String>, vararg rest: Expression<String>) = AqlFunc.CONCAT.stringCall(first, *rest)

/**
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#contains
 */
@JvmName("CONTAINS_")
fun CONTAINS(haystack: Expression<String>, needle: Expression<String>) = AqlFunc.CONTAINS.boolCall(haystack, needle)

infix fun Expression<String>.CONTAINS(needle: Expression<String>) = CONTAINS(this, needle)


