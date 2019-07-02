package io.ultra.common

import java.math.BigInteger
import java.security.MessageDigest

fun String.surround(with: String) = "$with${this}$with"

/**
 * Converts the first letter of the String to uppercase
 */
fun String.ucFirst(): String {

    if (length == 0) return this

    return substring(0, 1).toUpperCase() + substring(1)
}

/**
 * Converts the first letter of the String to lowercase
 */
fun String.lcFirst(): String {

    if (length == 0) return this

    return substring(0, 1).toLowerCase() + substring(1)
}

/**
 * Returns 'true' when the string starts with any of the given prefixes
 */
fun String.startsWithAny(vararg prefix: String) = prefix.any { startsWith(it) }

/**
 * Returns 'true' when the string does NOT start with any of the given prefixes
 */
fun String.startsWithNone(vararg prefix: String) = !startsWithAny(*prefix)


fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}
