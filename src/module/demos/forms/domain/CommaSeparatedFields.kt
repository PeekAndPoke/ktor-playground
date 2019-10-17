package de.peekandpoke.module.demos.forms.domain

import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.FormField
import de.peekandpoke.ktorfx.formidable.field
import io.ktor.application.ApplicationCall

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


    data class IntList(var data: List<Int> = listOf(1, 2, 3))

    class IntListForm(name: String, data: IntList, separator: String) : CommaSeparatedForm(name) {
        override val field = field(data::data, separator)
    }

    suspend fun all(call: ApplicationCall) = listOf(
        boolListSeparateByCommas(call),
        boolListSeparateBySpaces(call),

        byteListSeparateByCommas(call),
        byteListSeparateBySpaces(call),

        charListSeparateByCommas(call),
        charListSeparateBySpaces(call),

        intListSeparateByCommas(call),
        intListSeparateBySpaces(call)
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
}
