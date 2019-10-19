package de.peekandpoke.module.demos.forms.domain

import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.FormField
import de.peekandpoke.ktorfx.formidable.field
import io.ktor.application.ApplicationCall
import java.math.BigDecimal
import java.math.BigInteger

abstract class CommaSeparatedForm(name: String) : Form(name) {
    abstract val field: FormField<*>
}

class CommaSeparatedFields {

    data class BoolList(var data: List<Boolean> = listOf(true, false, true))

    class BoolListForm(name: String, data: BoolList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class ByteList(var data: List<Byte> = listOf(1, 2, 3))

    class ByteListForm(name: String, data: ByteList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class CharList(var data: List<Char> = listOf('a', 'b', 'c'))

    class CharListForm(name: String, data: CharList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class ShortList(var data: List<Short> = listOf(1, 2, 3))

    class ShortListForm(name: String, data: ShortList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class IntList(var data: List<Int> = listOf(1, 2, 3))

    class IntListForm(name: String, data: IntList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class LongList(var data: List<Long> = listOf(1, 2, 3))

    class LongListForm(name: String, data: LongList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class FloatList(var data: List<Float> = listOf(1.1f, 2.2f, 3.3f))

    class FloatListForm(name: String, data: FloatList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class DoubleList(var data: List<Double> = listOf(1.1, 2.2, 3.3))

    class DoubleListForm(name: String, data: DoubleList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class StringList(var data: List<String> = listOf("one", "two", "three"))

    class StringListForm(name: String, data: StringList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class BigIntegerList(var data: List<BigInteger> = listOf(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))

    class BigIntegerListForm(name: String, data: BigIntegerList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }


    data class BigDecimalList(var data: List<BigDecimal> = listOf(1.1.toBigDecimal(), 2.2.toBigDecimal(), 3.3.toBigDecimal()))

    class BigDecimalListForm(name: String, data: BigDecimalList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }

    suspend fun all(call: ApplicationCall) = listOf(
        boolListSeparateByCommas(call),
        boolListSeparateBySpaces(call),

        byteListSeparateByCommas(call),
        byteListSeparateBySpaces(call),

        charListSeparateByCommas(call),
        charListSeparateBySpaces(call),

        shortListSeparateByCommas(call),
        shortListSeparateBySpaces(call),

        intListSeparateByCommas(call),
        intListSeparateBySpaces(call),

        longListSeparateByCommas(call),
        longListSeparateBySpaces(call),

        floatListSeparateByCommas(call),
        floatListSeparateBySpaces(call),

        doubleListSeparateByCommas(call),
        doubleListSeparateBySpaces(call),

        stringListSeparateByCommas(call),
        stringListSeparateBySpaces(call),

        bigIntegerListSeparateByCommas(call),
        bigIntegerListSeparateBySpaces(call),

        bigDecimalListSeparateByCommas(call),
        bigDecimalListSeparateBySpaces(call)
    )

    private suspend fun boolListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = BoolList()
        val form = BoolListForm("list_bool_comma", data, ",").apply { submit(call) }

        return Triple("Booleans separated by commas", data, form)
    }

    private suspend fun boolListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = BoolList()
        val form = BoolListForm("list_bool_space", data, " ").apply { submit(call) }

        return Triple("Booleans separated by spaces", data, form)
    }

    private suspend fun byteListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = ByteList()
        val form = ByteListForm("list_byte_comma", data, ",").apply { submit(call) }

        return Triple("Bytes separated by commas", data, form)
    }

    private suspend fun byteListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = ByteList()
        val form = ByteListForm("list_byte_space", data, " ").apply { submit(call) }

        return Triple("Bytes separated by spaces", data, form)
    }

    private suspend fun charListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = CharList()
        val form = CharListForm("list_char_comma", data, ",").apply { submit(call) }

        return Triple("Chars separated by commas", data, form)
    }

    private suspend fun charListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = CharList()
        val form = CharListForm("list_char_space", data, " ").apply { submit(call) }

        return Triple("Chars separated by spaces", data, form)
    }

    private suspend fun shortListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = ShortList()
        val form = ShortListForm("list_short_comma", data, ",").apply { submit(call) }

        return Triple("Shorts separated by commas", data, form)
    }

    private suspend fun shortListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = ShortList()
        val form = ShortListForm("list_short_space", data, " ").apply { submit(call) }

        return Triple("Shorts separated by spaces", data, form)
    }

    private suspend fun intListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = IntList()
        val form = IntListForm("list_int_comma", data, ",").apply { submit(call) }

        return Triple("Integers separated by commas", data, form)
    }

    private suspend fun intListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = IntList()
        val form = IntListForm("list_int_space", data, " ").apply { submit(call) }

        return Triple("Integers separated by spaces", data, form)
    }

    private suspend fun longListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = LongList()
        val form = LongListForm("list_long_comma", data, ",").apply { submit(call) }

        return Triple("Longs separated by commas", data, form)
    }

    private suspend fun longListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = LongList()
        val form = LongListForm("list_long_space", data, " ").apply { submit(call) }

        return Triple("Longs separated by spaces", data, form)
    }

    private suspend fun floatListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = FloatList()
        val form = FloatListForm("list_float_comma", data, ",").apply { submit(call) }

        return Triple("Floats separated by commas", data, form)
    }

    private suspend fun floatListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = FloatList()
        val form = FloatListForm("list_float_space", data, " ").apply { submit(call) }

        return Triple("Floats separated by spaces", data, form)
    }

    private suspend fun doubleListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = DoubleList()
        val form = DoubleListForm("list_double_comma", data, ",").apply { submit(call) }

        return Triple("Doubles separated by commas", data, form)
    }

    private suspend fun doubleListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = DoubleList()
        val form = DoubleListForm("list_double_space", data, " ").apply { submit(call) }

        return Triple("Doubles separated by spaces", data, form)
    }

    private suspend fun stringListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = StringList()
        val form = StringListForm("list_string_comma", data, ",").apply { submit(call) }

        return Triple("Strings separated by commas", data, form)
    }

    private suspend fun stringListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = StringList()
        val form = StringListForm("list_string_space", data, " ").apply { submit(call) }

        return Triple("Strings separated by spaces", data, form)
    }

    private suspend fun bigIntegerListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = BigIntegerList()
        val form = BigIntegerListForm("list_big_integer_comma", data, ",").apply { submit(call) }

        return Triple("BigIntegers separated by commas", data, form)
    }

    private suspend fun bigIntegerListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = BigIntegerList()
        val form = BigIntegerListForm("list_big_integer_space", data, " ").apply { submit(call) }

        return Triple("BigIntegers separated by spaces", data, form)
    }

    private suspend fun bigDecimalListSeparateByCommas(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = BigDecimalList()
        val form = BigDecimalListForm("list_big_decimal_comma", data, ",").apply { submit(call) }

        return Triple("BigDecimals separated by commas", data, form)
    }

    private suspend fun bigDecimalListSeparateBySpaces(call: ApplicationCall): Triple<String, Any, CommaSeparatedForm> {

        val data = BigDecimalList()
        val form = BigDecimalListForm("list_big_decimal_space", data, " ").apply { submit(call) }

        return Triple("BigDecimals separated by spaces", data, form)
    }
}
