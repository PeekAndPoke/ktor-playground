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
 * Return an AQL value described by the JSON-encoded input string.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#jsonparse
 */
fun JSON_PARSE(expr: Expression<String>) = AqlFunc.JSON_PARSE.anyCall(expr)

/**
 * Return a JSON string representation of the input value.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#jsonstringify
 */
fun <T> JSON_STRINGIFY(expr: Expression<T>) = AqlFunc.JSON_STRINGIFY.stringCall(expr)

/**
 * Return the n leftmost characters of the string value.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#left
 */
fun LEFT(expr: Expression<String>, n: Expression<Number>) = AqlFunc.LEFT.stringCall(expr, n)

/**
 * Determine the character length of a string.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#length
 */
fun LENGTH(expr: Expression<String>) = AqlFunc.LENGTH.numberCall(expr)

/**
 * Calculate the Levenshtein distance between two strings.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#levenshteindistance
 */
fun LEVENSHTEIN_DISTANCE(left: Expression<String>, right: Expression<String>) 
        = AqlFunc.LEVENSHTEIN_DISTANCE.numberCall(left, right)

/**
 * Convert upper-case letters in value to their lower-case counterparts. All other characters are returned unchanged.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#lower
 */
fun LOWER(expr: Expression<String>) = AqlFunc.LOWER.stringCall(expr)

/**
 * Return the string value with whitespace stripped from the start only.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#ltrim
 */
fun LTRIM(subject: Expression<String>) = AqlFunc.LTRIM.stringCall(subject)

/**
 * Return the string value with whitespace stripped from the start only.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#ltrim
 */
fun LTRIM(subject: Expression<String>, chars: Expression<String>) = AqlFunc.LTRIM.stringCall(subject, chars)

/**
 * Calculate the MD5 checksum for text and return it in a hexadecimal string representation.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#md5
 */
fun MD5(value: Expression<String>) = AqlFunc.MD5.stringCall(value)

/**
 * Return the n rightmost characters of the string value.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#right
 */
fun RIGHT(value: Expression<String>, n: Expression<Number>) = AqlFunc.RIGHT.stringCall(value, n)

/**
 * Return the string value with whitespace stripped at the start only.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#rtrim
 */
fun RTRIM(subject: Expression<String>) = AqlFunc.RTRIM.stringCall(subject)

/**
 * Return the string value with whitespace stripped at the start only.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#rtrim
 */
fun RTRIM(subject: Expression<String>, chars: Expression<String>) = AqlFunc.RTRIM.stringCall(subject, chars)

/**
 * Calculate the SHA1 checksum for text and returns it in a hexadecimal string representation.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#sha1
 */
fun SHA1(expr: Expression<String>) = AqlFunc.SHA1.stringCall(expr)

/**
 * Calculate the SHA512 checksum for text and returns it in a hexadecimal string representation.
 *
 * https://docs.arangodb.com/current/AQL/Functions/String.html#sha256
 */
fun SHA512(expr: Expression<String>) = AqlFunc.SHA512.stringCall(expr)

/**
 * Split the given string value into a list of strings, using the separator.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#split  
 */
fun SPLIT(value: Expression<String>, separator: Expression<String>)  
        = AqlFunc.SPLIT.arrayCall(typeRef<List<String>>(), value, separator)

/**
 * Split the given string value into a list of strings, using the separator.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#split
 */
@JvmName("SPLIT2")
fun SPLIT(value: Expression<String>, separator: Expression<List<String>>)
        = AqlFunc.SPLIT.arrayCall(typeRef<List<String>>(), value, separator)

/**
 * Split the given string value into a list of strings, using the separator.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#split
 */
fun SPLIT(value: Expression<String>, separator: Expression<String>, limit: Expression<Number>)
        = AqlFunc.SPLIT.arrayCall(typeRef<List<String>>(), value, separator, limit)

/**
 * Split the given string value into a list of strings, using the separator.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#split
 */
@JvmName("SPLIT2")
fun SPLIT(value: Expression<String>, separator: Expression<List<String>>, limit: Expression<Number>)
        = AqlFunc.SPLIT.arrayCall(typeRef<List<String>>(), value, separator, limit)

/**
 * Return the soundex fingerprint of value.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#soundex
 */
fun SOUNDEX(value: Expression<String>) = AqlFunc.SOUNDEX.stringCall(value)

/**
 * Return the string value with whitespace stripped from start and end
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#trim
 */
fun TRIM(subject: Expression<String>) = AqlFunc.TRIM.stringCall(subject)

/**
 * Return the string value with whitespace stripped from start and end
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#trim
 */
fun TRIM(subject: Expression<String>, chars: Expression<String>) = AqlFunc.TRIM.stringCall(subject, chars)

/**
 * Return the base64 representation of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#tobase64
 */
fun TO_BASE64(value: Expression<String>) = AqlFunc.TO_BASE64.stringCall(value)

/**
 * Return the hex representation of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#tobase64
 */
fun TO_HEX(value: Expression<String>) = AqlFunc.TO_HEX.stringCall(value)

/**
 * Convert lower-case letters in value to their upper-case counterparts. All other characters are returned unchanged.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#upper
 */
fun UPPER(expr: Expression<String>) = AqlFunc.UPPER.stringCall(expr)

/**
 * Return a universally unique identifier value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/String.html#uuid
 */
fun UUID() = AqlFunc.UUID.stringCall()
