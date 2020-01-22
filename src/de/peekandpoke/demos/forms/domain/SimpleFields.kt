package de.peekandpoke.demos.forms.domain

import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.field
import io.ktor.application.ApplicationCall
import java.math.BigDecimal
import java.math.BigInteger

class SimpleFields {

    data class Data(
        var boolean: Boolean = true,
        var booleanOptional: Boolean? = null,
        var byte: Byte = 1,
        var byteOptional: Byte? = null,
        var char: Char = 'a',
        var charOptional: Char? = null,
        var short: Short = 1,
        var shortOptional: Short? = null,
        var int: Int = 1,
        var intOptional: Int? = null,
        var long: Long = 1,
        var longOptional: Long? = null,
        var float: Float = 1.1f,
        var floatOptional: Float? = null,
        var double: Double = 1.1,
        var doubleOptional: Double? = null,
        var string: String = "hello",
        var stringOptional: String? = null,
        var bigInteger: BigInteger = 1.toBigInteger(),
        var bigIntegerOptional: BigInteger? = null,
        var bigDecimal: BigDecimal = 1.1.toBigDecimal(),
        var bigDecimalOptional: BigDecimal? = null
    )

    class DataForm(data: Data) : Form("data") {
        val boolean = field(data::boolean)
        val booleanOptional = field(data::booleanOptional)
        val byte = field(data::byte)
        val byteOptional = field(data::byteOptional)
        val char = field(data::char)
        val charOptional = field(data::charOptional)
        val short = field(data::short)
        val shortOptional = field(data::shortOptional)
        val int = field(data::int)
        val intOptional = field(data::intOptional)
        val long = field(data::long)
        val longOptional = field(data::longOptional)
        val float = field(data::float)
        val floatOptional = field(data::floatOptional)
        val double = field(data::double)
        val doubleOptional = field(data::doubleOptional)
        val string = field(data::string)
        val stringOptional = field(data::stringOptional)
        val bigInteger = field(data::bigInteger)
        val bigIntegerOptional = field(data::bigIntegerOptional)
        val bigDecimal = field(data::bigDecimal)
        val bigDecimalOptional = field(data::bigDecimalOptional)
    }

    suspend fun all(call: ApplicationCall): Pair<Data, DataForm> {

        val data = Data()

        val form = DataForm(data).apply { submit(call) }

        return Pair(data, form)
    }
}
