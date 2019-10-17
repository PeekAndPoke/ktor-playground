@file:Suppress("unused")

package de.peekandpoke.ktorfx.formidable

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KMutableProperty0

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Helpers for comma separates lists
/////

/**
 * Adds a field for list and displays the values as a comma separated list
 */
@JvmName("separatedListField_List")
fun <T> Form.separatedListField(prop: KMutableProperty0<List<T>>, toStr: (T) -> String, fromStr: (String) -> T, separator: String) = add(
    name = prop.name,
    value = prop.getter(),
    setter = prop.setter,
    toStr = { it.joinToString(separator, transform = toStr) },
    fromStr = { str -> str.split(separator).map(String::trim).filter(String::isNotEmpty).map(fromStr) }
)

/**
 * Adds a field for list and displays the values as a comma separated list
 */
@JvmName("separatedListField_List?")
fun <T> Form.separatedListField(prop: KMutableProperty0<List<T>?>, toStr: (T) -> String, fromStr: (String) -> T, separator: String) = add(
    name = prop.name,
    value = prop.getter(),
    setter = prop.setter,
    toStr = { it?.joinToString(separator, transform = toStr) ?: "" },
    fromStr = { str -> str.split(separator).map(String::trim).filter(String::isNotEmpty).map(fromStr) }
)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// BOOLEAN fields
/////

/**
 * Adds a field for a Boolean property
 */
@JvmName("field_Boolean")
fun Form.field(prop: KMutableProperty0<Boolean>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toBoolean() })
        .trimmed()
        .acceptsBoolean()

/**
 * Adds a field for a Boolean? property
 */
@JvmName("field_Boolean?")
fun Form.field(prop: KMutableProperty0<Boolean?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { if (it.isNotEmpty()) it.toBoolean() else null })
        .trimmed()
        .acceptsBooleanOrBlank()

/**
 * Adds a field for a List<Boolean> property with all entries as comma separated list
 */
@JvmName("field_List_Boolean")
fun Form.field(prop: KMutableProperty0<List<Boolean>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toBoolean() }, separator)
        .acceptsBooleansCommaSeparated(separator)

/**
 * Adds a field for a List<Boolean> property with all entries as comma separated list
 */
@JvmName("field_List?_Boolean")
fun Form.field(prop: KMutableProperty0<List<Boolean>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toBoolean() }, separator)
        .acceptsBooleansCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// BYTE fields
/////

/**
 * Adds a field for a Byte property
 */
@JvmName("field_Byte")
fun Form.field(prop: KMutableProperty0<Byte>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toByte() })
        .trimmed()
        .acceptsByte()

/**
 * Adds a field for a Byte? property
 */
@JvmName("field_Byte?")
fun Form.field(prop: KMutableProperty0<Byte?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toByteOrNull() })
        .trimmed()
        .acceptsByteOrBlank()

/**
 * Adds a field for a List<Byte> property with all entries as comma separated list
 */
@JvmName("field_List_Byte")
fun Form.field(prop: KMutableProperty0<List<Byte>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toByte() }, separator)
        .acceptsBytesCommaSeparated(separator)

/**
 * Adds a field for a List<Byte>? property with all entries as comma separated list
 */
@JvmName("field_List?_Byte")
fun Form.field(prop: KMutableProperty0<List<Byte>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toByte() }, separator)
        .acceptsBytesCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// CHAR fields
/////

/**
 * Adds a field for a Char property
 */
@JvmName("field_Char")
fun Form.field(prop: KMutableProperty0<Char>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it[0] })
        .acceptsChar()

/**
 * Adds a field for a Char? property
 */
@JvmName("field_Char?")
fun Form.field(prop: KMutableProperty0<Char?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { if (it.isNotEmpty()) it[0] else null })
        .acceptsCharOrBlank()

/**
 * Adds a field for a List<Char> property with all entries as comma separated list
 */
@JvmName("field_List_Char")
fun Form.field(prop: KMutableProperty0<List<Char>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it[0] }, separator)
        .acceptsCharsCommaSeparated(separator)

/**
 * Adds a field for a List<Char>? property with all entries as comma separated list
 */
@JvmName("field_List?_Char")
fun Form.field(prop: KMutableProperty0<List<Char>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it[0] }, separator)
        .acceptsCharsCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// SHORT fields
/////

/**
 * Adds a field for a Short property
 */
@JvmName("field_Short")
fun Form.field(prop: KMutableProperty0<Short>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toShort() })
        .trimmed()
        .acceptsShort()

/**
 * Adds a field for a Short? property
 */
@JvmName("field_Short?")
fun Form.field(prop: KMutableProperty0<Short?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toShortOrNull() })
        .trimmed()
        .acceptsShortOrBlank()

/**
 * Adds a field for a List<Short> property with all entries as comma separated list
 */
@JvmName("field_List_Short")
fun Form.field(prop: KMutableProperty0<List<Short>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toShort() }, separator)
        .acceptsShortsCommaSeparated(separator)

/**
 * Adds a field for a List<Short>? property with all entries as comma separated list
 */
@JvmName("field_List?_Short")
fun Form.field(prop: KMutableProperty0<List<Short>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toShort() }, separator)
        .acceptsShortsCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// INT fields
/////

/**
 * Adds a field for am Int property
 */
@JvmName("field_Int")
fun Form.field(prop: KMutableProperty0<Int>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toInt() })
        .trimmed()
        .acceptsInteger()

/**
 * Adds a field for an Int? property
 */
@JvmName("field_Int?")
fun Form.field(prop: KMutableProperty0<Int?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toIntOrNull() })
        .trimmed()
        .acceptsIntegerOrBlank()

/**
 * Adds a field for a List<Int> property with all entries as comma separated list
 */
@JvmName("field_List_Int")
fun Form.field(prop: KMutableProperty0<List<Int>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toInt() }, separator)
        .acceptsIntegersCommaSeparated(separator)

/**
 * Adds a field for a List<Int>? property with all entries as comma separated list
 */
@JvmName("field_List?_Int")
fun Form.field(prop: KMutableProperty0<List<Int>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toInt() }, separator)
        .acceptsIntegersCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// LONG fields
/////

/**
 * Adds a field for a Long property
 */
@JvmName("field_Long")
fun Form.field(prop: KMutableProperty0<Long>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toLong() })
        .trimmed()
        .acceptsLong()

/**
 * Adds a field for a Long? property
 */
@JvmName("field_Long?")
fun Form.field(prop: KMutableProperty0<Long?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toLongOrNull() })
        .trimmed()
        .acceptsLongOrBlank()

/**
 * Adds a field for a List<Long> property with all entries as comma separated list
 */
@JvmName("field_List_Long")
fun Form.field(prop: KMutableProperty0<List<Long>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toLong() }, separator)
        .acceptsLongsCommaSeparated(separator)

/**
 * Adds a field for a List<Long>? property with all entries as comma separated list
 */
@JvmName("field_List?_Long")
fun Form.field(prop: KMutableProperty0<List<Long>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toLong() }, separator)
        .acceptsLongsCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// FLOAT fields
/////

/**
 * Adds a field for a Float property
 */
@JvmName("field_Float")
fun Form.field(prop: KMutableProperty0<Float>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toFloat() })
        .trimmed()
        .acceptsFloat()

/**
 * Adds a field for a Float? property
 */
@JvmName("field_Float?")
fun Form.field(prop: KMutableProperty0<Float?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toFloatOrNull() })
        .trimmed()
        .acceptsFloatOrBlank()

/**
 * Adds a field for a List<Float> property with all entries as comma separated list
 */
@JvmName("field_List_Float")
fun Form.field(prop: KMutableProperty0<List<Float>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toFloat() }, separator)
        .acceptsFloatsCommaSeparated(separator)

/**
 * Adds a field for a List<Float>? property with all entries as comma separated list
 */
@JvmName("field_List?_Float")
fun Form.field(prop: KMutableProperty0<List<Float>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toFloat() }, separator)
        .acceptsFloatsCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// DOUBLE fields
/////

/**
 * Adds a field for a Double property
 */
@JvmName("field_Double")
fun Form.field(prop: KMutableProperty0<Double>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toDouble() })
        .trimmed()
        .acceptsDouble()

/**
 * Adds a field for a Double? property
 */
@JvmName("field_Double?")
fun Form.field(prop: KMutableProperty0<Double?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toDoubleOrNull() })
        .trimmed()
        .acceptsDoubleOrBlank()

/**
 * Adds a field for a List<Double> property with all entries as comma separated list
 */
@JvmName("field_List_Double")
fun Form.field(prop: KMutableProperty0<List<Double>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toDouble() }, separator)
        .acceptsDoublesCommaSeparated(separator)

/**
 * Adds a field for a List<Double>? property with all entries as comma separated list
 */
@JvmName("field_List?_Double")
fun Form.field(prop: KMutableProperty0<List<Double>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toDouble() }, separator)
        .acceptsDoublesCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// STRING fields
/////

@JvmName("field_String")
fun Form.field(prop: KMutableProperty0<String>) = add(prop.name, prop.getter(), prop.setter, { it }, { it })

@JvmName("field_String?")
fun Form.field(prop: KMutableProperty0<String?>) = add(prop.name, prop.getter(), prop.setter, { it ?: "" }, { it })

/**
 * Adds a field for a List<String> property with all entries as comma separated list
 */
@JvmName("field_List_String")
fun Form.field(prop: KMutableProperty0<List<String>>, separator: String = ",") =
    separatedListField(prop, { it }, { it }, separator)

/**
 * Adds a field for a List<String>? property with all entries as comma separated list
 */
@JvmName("field_List?_String")
fun Form.field(prop: KMutableProperty0<List<String>?>, separator: String = ",") =
    separatedListField(prop, { it }, { it }, separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// BIG-INTEGER fields
/////

/**
 * Adds a field for am BigDecimal property
 */
@JvmName("field_BigInteger")
fun Form.field(prop: KMutableProperty0<BigInteger>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toBigInteger() })
        .trimmed()
        .acceptsBigInteger()

/**
 * Adds a field for an BigDecimal? property
 */
@JvmName("field_field_BigInteger?")
fun Form.field(prop: KMutableProperty0<BigInteger?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toBigIntegerOrNull() })
        .trimmed()
        .acceptsBigIntegerOrBlank()

/**
 * Adds a field for a List<BigInteger> property with all entries as comma separated list
 */
@JvmName("field_List_BigInteger")
fun Form.field(prop: KMutableProperty0<List<BigInteger>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toBigInteger() }, separator)
        .acceptsBigIntegersCommaSeparated(separator)

/**
 * Adds a field for a List<BigInteger>? property with all entries as comma separated list
 */
@JvmName("field_List?_BigInteger")
fun Form.field(prop: KMutableProperty0<List<BigInteger>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toBigInteger() }, separator)
        .acceptsBigIntegersCommaSeparated(separator)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// BIG-DECIMAL fields
/////

/**
 * Adds a field for am BigDecimal property
 */
@JvmName("field_BigDecimal")
fun Form.field(prop: KMutableProperty0<BigDecimal>) =
    add(prop.name, prop.getter(), prop.setter, { it.toString() }, { it.toBigDecimal() })
        .trimmed()
        .acceptsBigDecimal()

/**
 * Adds a field for an BigDecimal? property
 */
@JvmName("field_field_BigDecimal?")
fun Form.field(prop: KMutableProperty0<BigDecimal?>) =
    add(prop.name, prop.getter(), prop.setter, { it?.toString() ?: "" }, { it.toBigDecimalOrNull() })
        .trimmed()
        .acceptsBigDecimalOrBlank()

/**
 * Adds a field for a List<BigDecimal> property with all entries as comma separated list
 */
@JvmName("field_List_BigDecimal")
fun Form.field(prop: KMutableProperty0<List<BigDecimal>>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toBigDecimal() }, separator)
        .acceptsBigDecimalsCommaSeparated(separator)

/**
 * Adds a field for a List<BigDecimal>? property with all entries as comma separated list
 */
@JvmName("field_List?_BigDecimal")
fun Form.field(prop: KMutableProperty0<List<BigDecimal>?>, separator: String = ",") =
    separatedListField(prop, { it.toString() }, { it.toBigDecimal() }, separator)
        .acceptsBigDecimalsCommaSeparated(separator)

