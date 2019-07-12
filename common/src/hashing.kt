package io.ultra.common

import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

fun ByteArray.base64(): String = Base64.getEncoder().encodeToString(this)

fun ByteArray.md5(): String {
    val md = MessageDigest.getInstance("MD5")

    return BigInteger(1, md.digest(this))
        .toString(16)
        .padStart(32, '0')
}

fun String.md5(): String = toByteArray().md5()

fun ByteArray.sha256(): ByteArray = MessageDigest.getInstance("SHA-256").digest(this)

fun String.sha256(): ByteArray = toByteArray().sha256()

fun ByteArray.sha384(): ByteArray = MessageDigest.getInstance("SHA-384").digest(this)

fun String.sha384(): ByteArray = toByteArray().sha384()
