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

/**
 * Determine the character length of a string.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#count
 */
fun COUNT(expr: Expression<String>) = AqlFunc.COUNT.numberCall(expr)

/**
 * Return the position of the first occurrence of the string search inside the string text. Positions start at 0.
 * 
 * https://docs.arangodb.com/current/AQL/Functions/String.html#findfirst
 */
fun FIND_FIRST(haystack: Expression<String>, needle: Expression<String>) =
        AqlFunc.FIND_FIRST.numberCall(haystack, needle)

/**
 * Return the position of the first occurrence of the string search inside the string text. Positions start at 0.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#findfirst
 */
fun FIND_FIRST(haystack: Expression<String>, needle: Expression<String>, start: Expression<Number>) =
    AqlFunc.FIND_FIRST.numberCall(haystack, needle, start)

/**
 * Return the position of the first occurrence of the string search inside the string text. Positions start at 0.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#findfirst
 */
fun FIND_FIRST(haystack: Expression<String>, needle: Expression<String>, start: Expression<Number>, end: Expression<Number>) =
    AqlFunc.FIND_FIRST.numberCall(haystack, needle, start, end)

/**
 * Return the position of the first occurrence of the string search inside the string text. Positions start at 0.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#findlast
 */
fun FIND_LAST(haystack: Expression<String>, needle: Expression<String>) =
    AqlFunc.FIND_LAST.numberCall(haystack, needle)

/**
 * Return the position of the first occurrence of the string search inside the string text. Positions start at 0.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#findlast
 */
fun FIND_LAST(haystack: Expression<String>, needle: Expression<String>, start: Expression<Number>) =
    AqlFunc.FIND_LAST.numberCall(haystack, needle, start)

/**
 * Return the position of the first occurrence of the string search inside the string text. Positions start at 0.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#findlast
 */
fun FIND_LAST(haystack: Expression<String>, needle: Expression<String>, start: Expression<Number>, end: Expression<Number>) =
    AqlFunc.FIND_LAST.numberCall(haystack, needle, start, end)

/**
 * Determine the character length of a string.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#length
 */
fun LENGTH(expr: Expression<String>) = AqlFunc.LENGTH.numberCall(expr)
