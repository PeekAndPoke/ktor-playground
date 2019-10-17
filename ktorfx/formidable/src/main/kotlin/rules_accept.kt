package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.polyglot.Translatable

/**
 * Makes the field accept only non blank input (not only whitespace characters)
 */
fun <T> FormField<T>.acceptsNonBlank(message: Translatable = FormidableI18n.must_not_be_blank) =
    addAcceptRule(message, ::isNotBlank)

private fun isNotBlank(value: String) = value.isNotBlank()

/**
 * Make the field accept only non empty input
 */
fun <T> FormField<T>.acceptsNonEmpty(message: Translatable = FormidableI18n.must_not_be_empty) = addAcceptRule(message, ::isNotEmpty)

private fun isNotEmpty(value: String) = value.isNotEmpty()

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// BOOLEANS
/////

/**
 * Checks if the given string is a boolean string
 */
fun isBoolean(value: String) = value == "true" || value == "false"

/**
 * Makes the field accept only boolean values "true" or "false"
 */
fun <T> FormField<T>.acceptsBoolean(message: Translatable = FormidableI18n.must_be_a_boolean) =
    addAcceptRule(message, ::isBoolean)

/**
 * Makes a field accept a comma separated list of booleans
 */
fun <T> FormField<T>.acceptsBooleansCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_booleans) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isBoolean) }

/**
 * Checks if the given string is blank or a boolean string
 */
fun isBooleanOrBlank(value: String) = value.isBlank() || value == "true" || value == "false"

/**
 * Makes the field accept only boolean values or blank input
 */
fun <T> FormField<T?>.acceptsBooleanOrBlank(message: Translatable = FormidableI18n.must_be_a_boolean_or_blank) =
    addAcceptRule(message, ::isBooleanOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// BYTES
/////

/**
 * Checks if the given string is a byte string
 */
fun isByte(value: String) = value.toByteOrNull() != null

/**
 * Makes the field accept only byte values
 */
fun <T> FormField<T>.acceptsByte(message: Translatable = FormidableI18n.must_be_a_byte) =
    addAcceptRule(message, ::isByte)

/**
 * Makes a field accept a comma separated list of bytes
 */
fun <T> FormField<T>.acceptsBytesCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_bytes) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isByte) }

/**
 * Checks if the given string is blank or a byte value
 */
fun isByteOrBlank(value: String) = value.isBlank() || isByte(value)

/**
 * Makes the field accept only byte values or blank input
 */
fun <T> FormField<T?>.acceptsByteOrBlank(message: Translatable = FormidableI18n.must_be_a_byte_or_blank) =
    addAcceptRule(message, ::isByteOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// CHARS
/////

/**
 * Checks if the given string is a char value
 */
fun isChar(value: String) = value.length == 1

/**
 * Makes the field accept only char values
 */
fun <T> FormField<T>.acceptsChar(message: Translatable = FormidableI18n.must_be_a_char) =
    addAcceptRule(message, ::isChar)

/**
 * Makes a field accept a comma separated list of chars
 */
fun <T> FormField<T>.acceptsCharsCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_chars) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isChar) }

/**
 * Checks if the given string is blank or a char value
 */
fun isCharOrBlank(value: String) = value.isBlank() || isChar(value)

/**
 * Makes the field accept only char values or blank input
 */
fun <T> FormField<T?>.acceptsCharOrBlank(message: Translatable = FormidableI18n.must_be_a_char_or_blank) =
    addAcceptRule(message, ::isCharOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// SHORTS
/////

/**
 * Checks if the given string is a integer string
 */
fun isShort(value: String) = value.toShortOrNull() != null

/**
 * Makes the field accept only integer values
 */
fun <T> FormField<T>.acceptsShort(message: Translatable = FormidableI18n.must_be_a_short) =
    addAcceptRule(message, ::isShort)

/**
 * Makes a field accept a comma separated list of shorts
 */
fun <T> FormField<T>.acceptsShortsCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_shorts) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isShort) }

/**
 * Checks if the given string is blank or an integer string
 */
fun isShortOrBlank(value: String) = value.isBlank() || isShort(value)

/**
 * Makes the field accept only integer values or blank input
 */
fun <T> FormField<T?>.acceptsShortOrBlank(message: Translatable = FormidableI18n.must_be_a_short_or_blank) =
    addAcceptRule(message, ::isShortOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// INTEGERS
/////

/**
 * Checks if the given string is a integer string
 */
fun isInteger(value: String) = value.toIntOrNull() != null

/**
 * Makes the field accept only integer values
 */
fun <T> FormField<T>.acceptsInteger(message: Translatable = FormidableI18n.must_be_an_integer) =
    addAcceptRule(message, ::isInteger)

/**
 * Makes a field accept a comma separated list of integers
 */
fun <T> FormField<T>.acceptsIntegersCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_integers) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isInteger) }

/**
 * Checks if the given string is blank or an integer string
 */
fun isIntegerOrBlank(value: String) = value.isBlank() || isInteger(value)

/**
 * Makes the field accept only integer values or blank input
 */
fun <T> FormField<T?>.acceptsIntegerOrBlank(message: Translatable = FormidableI18n.must_be_an_integer_or_blank) =
    addAcceptRule(message, ::isIntegerOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// LONGS
/////

/**
 * Checks if the given string is a Long string
 */
fun isLong(value: String) = value.toLongOrNull() != null

/**
 * Makes the field accept only long values
 */
fun <T> FormField<T>.acceptsLong(message: Translatable = FormidableI18n.must_be_a_long) =
    addAcceptRule(message, ::isLong)

/**
 * Makes a field accept a comma separated list of longs
 */
fun <T> FormField<T>.acceptsLongsCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_longs) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isLong) }

/**
 * Checks if the given string is blank or an Long string
 */
fun isLongOrBlank(value: String) = value.isBlank() || isLong(value)

/**
 * Makes the field accept only integer values or blank input
 */
fun <T> FormField<T?>.acceptsLongOrBlank(message: Translatable = FormidableI18n.must_be_a_long_or_blank) =
    addAcceptRule(message, ::isLongOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// FLOATS
/////

/**
 * Checks if the given string is a Float string
 */
fun isFloat(value: String) = value.toFloatOrNull() != null

/**
 * Makes the field accept only double values
 */
fun <T> FormField<T>.acceptsFloat(message: Translatable = FormidableI18n.must_be_a_float) =
    addAcceptRule(message, ::isFloat)

/**
 * Makes a field accept a comma separated list of Floats
 */
fun <T> FormField<T>.acceptsFloatsCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_floats) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isFloat) }

/**
 * Checks if the given string is blank or a Float string
 */
fun isFloatOrBlank(value: String) = value.isBlank() || isFloat(value)

/**
 * Makes the field accept only float values or blank input
 */
fun <T> FormField<T?>.acceptsFloatOrBlank(message: Translatable = FormidableI18n.must_be_a_float_or_blank) =
    addAcceptRule(message, ::isFloatOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// DOUBLES
/////

/**
 * Checks if the given string is a Double string
 */
fun isDouble(value: String) = value.toDoubleOrNull() != null

/**
 * Makes the field accept only double values
 */
fun <T> FormField<T>.acceptsDouble(message: Translatable = FormidableI18n.must_be_a_double) =
    addAcceptRule(message, ::isDouble)

/**
 * Makes a field accept a comma separated list of Doubles
 */
fun <T> FormField<T>.acceptsDoublesCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_doubles) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isDouble) }

/**
 * Checks if the given string is a Double string or blank
 */
fun isDoubleOrBlank(value: String) = value.isBlank() || isDouble(value)

/**
 * Makes the field accept only Double values or blank
 */
fun <T> FormField<T?>.acceptsDoubleOrBlank(message: Translatable = FormidableI18n.must_be_a_double_or_blank) =
    addAcceptRule(message, ::isDoubleOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// BIG-INTEGERS
/////

/**
 * Checks if the given string is a BigInteger string
 */
fun isBigInteger(value: String) = value.toBigIntegerOrNull() != null

/**
 * Makes the field accept only BigInteger values
 */
fun <T> FormField<T>.acceptsBigInteger(message: Translatable = FormidableI18n.must_be_a_big_integer) =
    addAcceptRule(message, ::isBigInteger)

/**
 * Makes a field accept a comma separated list of BigIntegers
 */
fun <T> FormField<T>.acceptsBigIntegersCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_big_integers) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isBigInteger) }

/**
 * Checks if the given string is a BigInteger string or blank
 */
fun isBigIntegerOrBlank(value: String) = value.isBlank() || isBigInteger(value)

/**
 * Makes the field accept only BigInteger values or blank
 */
fun <T> FormField<T?>.acceptsBigIntegerOrBlank(message: Translatable = FormidableI18n.must_be_a_big_integer_or_blank) =
    addAcceptRule(message, ::isBigIntegerOrBlank)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// BIG-DECIMAL
/////

/**
 * Checks if the given string is a BigDecimal string
 */
fun isBigDecimal(value: String) = value.toBigDecimalOrNull() != null

/**
 * Makes the field accept only BigDecimal values
 */
fun <T> FormField<T>.acceptsBigDecimal(message: Translatable = FormidableI18n.must_be_a_big_decimal) =
    addAcceptRule(message, ::isBigDecimal)

/**
 * Makes a field accept a comma separated list of BigDecimals
 */
fun <T> FormField<T>.acceptsBigDecimalsCommaSeparated(separator: String, message: Translatable = FormidableI18n.must_be_a_list_of_big_decimals) =
    addAcceptRule(message) { it.split(separator).map(String::trim).filter(String::isNotEmpty).all(::isBigDecimal) }

/**
 * Checks if the given string is a BigDecimal string or blank
 */
fun isBigDecimalOrBlank(value: String) = value.isBlank() || isBigDecimal(value)

/**
 * Makes the field accept only BigInteger values or blank
 */
fun <T> FormField<T?>.acceptsBigDecimalOrBlank(message: Translatable = FormidableI18n.must_be_a_big_decimal_or_blank) =
    addAcceptRule(message, ::isBigDecimalOrBlank)
