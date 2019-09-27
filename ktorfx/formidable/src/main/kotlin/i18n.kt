@file:Suppress("FunctionName")

package de.peekandpoke.ktorfx.formidable

import io.ultra.polyglot.translatable

const val mainGroup = "formidable"
const val errorGroup = "$mainGroup.errors"

val must_be_a_boolean = "$errorGroup.must_be_a_boolean".translatable()

val must_be_a_boolean_or_blank = "$errorGroup.must_be_a_boolean_or_blank".translatable()

val must_be_a_byte = "$errorGroup.must_be_a_byte".translatable()

val must_be_a_byte_or_blank = "$errorGroup.must_be_a_byte_or_blank".translatable()

val must_be_an_integer = "$errorGroup.must_be_an_integer".translatable()

val must_be_an_integer_or_blank = "$errorGroup.must_be_an_integer_or_blank".translatable()

val must_not_be_blank = "$errorGroup.must_not_be_blank".translatable()

val invalid_value = "$errorGroup.invalid_value".translatable()

fun <T : Comparable<T>> must_be_in_range(range: ClosedRange<T>) =
    "$errorGroup.must_be_in_range".translatable("range" to range.toString())

fun <T : Comparable<T>> must_be_in_range_or_blank(range: ClosedRange<T>) =
    "$errorGroup.must_be_in_range_or_blank".translatable("range" to range.toString())
