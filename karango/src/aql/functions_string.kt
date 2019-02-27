@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql


/**
 * Return the number of characters in value (not byte length).
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#charlength
 */
fun CHAR_LENGTH(expr: Expression<String>) = AqlFunc.CHAR_LENGTH.numberCall(expr)

/**
 * Concatenate the values passed as value1 to valueN.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#concat
 */
fun CONCAT(first: Expression<String>, vararg rest: Expression<String>) = AqlFunc.CONCAT.stringCall(first, *rest)

/**
 * Concatenate the strings passed as arguments value1 to valueN using the separator string.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#concatseparator
 */
fun CONCAT_SEPARATOR(separator: Expression<String>, first: Expression<String>, vararg rest: Expression<String>) =
    AqlFunc.CONCAT_SEPARATOR.stringCall(separator, first, *rest)

/**
 * Check whether the string search is contained in the string text. The string matching performed by CONTAINS is case-sensitive.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#contains
 */
@JvmName("CONTAINS_")
fun CONTAINS(haystack: Expression<String>, needle: Expression<String>) = AqlFunc.CONTAINS.boolCall(haystack, needle)

/**
 * Check whether the string search is contained in the string text. The string matching performed by CONTAINS is case-sensitive.
 * 
 * Infix version
 */
infix fun Expression<String>.CONTAINS(needle: Expression<String>) = CONTAINS(this, needle)


