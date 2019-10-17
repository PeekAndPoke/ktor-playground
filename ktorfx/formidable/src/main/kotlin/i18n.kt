@file:Suppress("FunctionName")

package de.peekandpoke.ktorfx.formidable

import de.peekandpoke.ultra.polyglot.*

val I18n.formidable get() = getGroup(FormidableI18n::class)

class FormidableI18n : I18nGroup, FormidableI18nTexts() {

    // make the texts statically available
    companion object : FormidableI18nTexts()

    override val texts: I18nTextsByLocale by lazy {
        FormidableI18n::class.java.classLoader.getResourceAsStream("ktorfx/formidable/i18n.json")!!.readPolyglotJson()
    }
}

@Suppress("PropertyName")
open class FormidableI18nTexts {

    private val mainGroup = "formidable"

    private val errorGroup = "$mainGroup.errors"

    val invalid_csrf_token = "$errorGroup.invalid_csrf_token".translatable()
    val must_not_be_blank = "$errorGroup.must_not_be_blank".translatable()
    val must_not_be_empty = "$errorGroup.must_not_be_empty".translatable()
    val invalid_value = "$errorGroup.invalid_value".translatable()

    val must_be_a_boolean = "$errorGroup.must_be_a_boolean".translatable()
    val must_be_a_boolean_or_blank = "$errorGroup.must_be_a_boolean_or_blank".translatable()
    val must_be_a_list_of_booleans = "$errorGroup.must_be_a_list_of_booleans".translatable()

    val must_be_a_byte = "$errorGroup.must_be_a_byte".translatable()
    val must_be_a_byte_or_blank = "$errorGroup.must_be_a_byte_or_blank".translatable()
    val must_be_a_list_of_bytes = "$errorGroup.must_be_a_list_of_bytes".translatable()

    val must_be_a_char = "$errorGroup.must_be_a_char".translatable()
    val must_be_a_char_or_blank = "$errorGroup.must_be_a_char_or_blank".translatable()
    val must_be_a_list_of_chars = "$errorGroup.must_be_a_list_of_chars".translatable()

    val must_be_a_short = "$errorGroup.must_be_a_short".translatable()
    val must_be_a_short_or_blank = "$errorGroup.must_be_a_short_or_blank".translatable()
    val must_be_a_list_of_shorts = "$errorGroup.must_be_a_list_of_shorts".translatable()

    val must_be_an_integer = "$errorGroup.must_be_an_integer".translatable()
    val must_be_an_integer_or_blank = "$errorGroup.must_be_an_integer_or_blank".translatable()
    val must_be_a_list_of_integers = "$errorGroup.must_be_a_list_of_integers".translatable()

    val must_be_a_long = "$errorGroup.must_be_a_long".translatable()
    val must_be_a_long_or_blank = "$errorGroup.must_be_a_long_or_blank".translatable()
    val must_be_a_list_of_longs = "$errorGroup.must_be_a_list_of_longs".translatable()

    val must_be_a_float = "$errorGroup.must_be_a_float".translatable()
    val must_be_a_float_or_blank = "$errorGroup.must_be_a_float_or_blank".translatable()
    val must_be_a_list_of_floats = "$errorGroup.must_be_a_list_of_floats".translatable()

    val must_be_a_double = "$errorGroup.must_be_a_double".translatable()
    val must_be_a_double_or_blank = "$errorGroup.must_be_a_double_or_blank".translatable()
    val must_be_a_list_of_doubles = "$errorGroup.must_be_a_list_of_doubles".translatable()

    val must_be_a_big_integer = "$errorGroup.must_be_a_big_integer".translatable()
    val must_be_a_big_integer_or_blank = "$errorGroup.must_be_a_big_integer_or_blank".translatable()
    val must_be_a_list_of_big_integers = "$errorGroup.must_be_a_list_of_big_integers".translatable()

    val must_be_a_big_decimal = "$errorGroup.must_be_a_big_decimal".translatable()
    val must_be_a_big_decimal_or_blank = "$errorGroup.must_be_a_big_decimal_or_blank".translatable()
    val must_be_a_list_of_big_decimals = "$errorGroup.must_be_a_list_of_big_decimals".translatable()

    fun <T : Comparable<T>> must_be_in_range(range: ClosedRange<T>) =
        "$errorGroup.must_be_in_range".translatable("range" to range.toString())

    fun <T : Comparable<T>> must_be_in_range_or_blank(range: ClosedRange<T>) =
        "$errorGroup.must_be_in_range_or_blank".translatable("range" to range.toString())
}

