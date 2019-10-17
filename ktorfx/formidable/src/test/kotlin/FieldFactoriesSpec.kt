package de.peekandpoke.ktorfx.formidable

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.ktor.http.parametersOf
import java.math.BigDecimal
import java.math.BigInteger

class FieldFactoriesSpec : StringSpec({

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BOOLEAN fields
    /////

    "Field from Boolean property" {

        data class Data(var data: Boolean)

        val obj = Data(true)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_boolean)
            obj.data shouldBe true

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_boolean)
            obj.data shouldBe true

            subject.submit(parametersOf("data", "false"))
            subject.isValid() shouldBe true
            obj.data shouldBe false

            subject.submit(parametersOf("data", "true"))
            subject.isValid() shouldBe true
            obj.data shouldBe true
        }
    }

    "Field from Boolean? property" {

        data class Data(var data: Boolean?)

        val obj = Data(true)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_boolean_or_blank)
            obj.data shouldBe true

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "false"))
            subject.isValid() shouldBe true
            obj.data shouldBe false

            subject.submit(parametersOf("data", "true"))
            subject.isValid() shouldBe true
            obj.data shouldBe true
        }
    }

    "Field from List<Boolean> property" {

        data class Data(var data: List<Boolean>)

        val obj = Data(listOf(true, false))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_booleans)
            obj.data shouldBe listOf(true, false)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "false, true"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(false, true)
        }
    }

    "Field from List<Boolean>? property" {

        data class Data(var data: List<Boolean>?)

        val obj = Data(listOf(true, false))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_booleans)
            obj.data shouldBe listOf(true, false)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "false, true"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(false, true)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BYTE fields
    /////

    "Field from Byte property" {

        data class Data(var data: Byte)

        val obj = Data(10)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_byte)
            obj.data shouldBe 10.toByte()

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10.toByte()

            subject.submit(parametersOf("data", "-129"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10.toByte()

            subject.submit(parametersOf("data", "128"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10.toByte()

            subject.submit(parametersOf("data", "-1"))
            subject.isValid() shouldBe true
            obj.data shouldBe (-1).toByte()

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.toByte()
        }
    }

    "Field from Byte? property" {

        data class Data(var data: Byte?)

        val obj = Data(10)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_byte_or_blank)
            obj.data shouldBe 10.toByte()

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.toByte()
        }
    }

    "Field from List<Byte> property" {

        data class Data(var data: List<Byte>)

        val obj = Data(listOf(1, 2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_bytes)
            obj.data shouldBe listOf(1.toByte(), 2.toByte())

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.toByte(), 5.toByte())
        }
    }

    "Field from List<Byte>? property" {

        data class Data(var data: List<Byte>?)

        val obj = Data(listOf(1, 2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_bytes)
            obj.data shouldBe listOf(1.toByte(), 2.toByte())

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.toByte(), 5.toByte())
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CHAR fields
    /////

    "Field from Char property" {

        data class Data(var data: Char)

        val obj = Data('a')

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_char)
            obj.data shouldBe 'a'

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 'a'

            subject.submit(parametersOf("data", "b"))
            subject.isValid() shouldBe true
            obj.data shouldBe 'b'
        }
    }

    "Field from Char? property" {

        data class Data(var data: Char?)

        val obj = Data('a')

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_char_or_blank)
            obj.data shouldBe 'a'

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "b"))
            subject.isValid() shouldBe true
            obj.data shouldBe 'b'
        }
    }

    "Field from List<Char> property" {

        data class Data(var data: List<Char>)

        val obj = Data(listOf('a', 'b'))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_chars)
            obj.data shouldBe listOf('a', 'b')

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf('3', '5')
        }
    }

    "Field from List<Char>? property" {

        data class Data(var data: List<Char>?)

        val obj = Data(listOf('a', 'b'))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_chars)
            obj.data shouldBe listOf('a', 'b')

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf('3', '5')
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SHORT fields
    /////

    "Field from Short property" {

        data class Data(var data: Short)

        val obj = Data(10)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_short)
            obj.data shouldBe 10.toShort()

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10.toShort()

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.toShort()
        }
    }

    "Field from Short? property" {

        data class Data(var data: Short?)

        val obj = Data(10)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_short_or_blank)
            obj.data shouldBe 10.toShort()

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.toShort()
        }
    }

    "Field from List<Short> property" {

        data class Data(var data: List<Short>)

        val obj = Data(listOf(1, 2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_shorts)
            obj.data shouldBe listOf(1.toShort(), 2.toShort())

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.toShort(), 5.toShort())
        }
    }

    "Field from List<Short>? property" {

        data class Data(var data: List<Short>?)

        val obj = Data(listOf(1, 2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_shorts)
            obj.data shouldBe listOf(1.toShort(), 2.toShort())

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.toShort(), 5.toShort())
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INTEGER fields
    /////

    "Field from Int property" {

        data class Data(var data: Int)

        val obj = Data(10)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_an_integer)
            obj.data shouldBe 10

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20
        }
    }

    "Field from Int? property" {

        data class Data(var data: Int?)

        val obj = Data(10)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_an_integer_or_blank)
            obj.data shouldBe 10

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20
        }
    }

    "Field from List<Int> property" {

        data class Data(var data: List<Int>)

        val obj = Data(listOf(1, 2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_integers)
            obj.data shouldBe listOf(1, 2)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3, 5)
        }
    }

    "Field from List<Int>? property" {

        data class Data(var data: List<Int>?)

        val obj = Data(listOf(1, 2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_integers)
            obj.data shouldBe listOf(1, 2)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3, 5)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // LONG fields
    /////

    "Field from Long property" {

        data class Data(var data: Long)

        val obj = Data(10L)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_long)
            obj.data shouldBe 10L

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10L

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20L
        }
    }

    "Field from Long? property" {

        data class Data(var data: Long?)

        val obj = Data(10L)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_long_or_blank)
            obj.data shouldBe 10L

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20L
        }
    }

    "Field from List<Long> property" {

        data class Data(var data: List<Long>)

        val obj = Data(listOf(1, 2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_longs)
            obj.data shouldBe listOf(1L, 2L)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3L, 5L)
        }
    }

    "Field from List<Long>? property" {

        data class Data(var data: List<Long>?)

        val obj = Data(listOf(1, 2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_longs)
            obj.data shouldBe listOf(1L, 2L)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3L, 5L)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FLOAT fields
    /////

    "Field from Float property" {

        data class Data(var data: Float)

        val obj = Data(10.0f)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_float)
            obj.data shouldBe 10.0f

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10.0f

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.0f

            subject.submit(parametersOf("data", "20.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.5f
        }
    }

    "Field from Float? property" {

        data class Data(var data: Float?)

        val obj = Data(10.0f)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_float_or_blank)
            obj.data shouldBe 10.0f

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.0f

            subject.submit(parametersOf("data", "20.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.5f
        }
    }

    "Field from List<Float> property" {

        data class Data(var data: List<Float>)

        val obj = Data(listOf(1.1f, 2.2f))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_floats)
            obj.data shouldBe listOf(1.1f, 2.2f)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3.3, 5.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.3f, 5.5f)
        }
    }

    "Field from List<Float>? property" {

        data class Data(var data: List<Float>?)

        val obj = Data(listOf(1.1f, 2.2f))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_floats)
            obj.data shouldBe listOf(1.1f, 2.2f)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3.3, 5.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.3f, 5.5f)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FLOAT fields
    /////

    "Field from Double property" {

        data class Data(var data: Double)

        val obj = Data(10.0)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_double)
            obj.data shouldBe 10.0

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10.0

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.0

            subject.submit(parametersOf("data", "20.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.5
        }
    }

    "Field from Double? property" {

        data class Data(var data: Double?)

        val obj = Data(10.0)

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_double_or_blank)
            obj.data shouldBe 10.0

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.0

            subject.submit(parametersOf("data", "20.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.5
        }
    }

    "Field from List<Double> property" {

        data class Data(var data: List<Double>)

        val obj = Data(listOf(1.1, 2.2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_doubles)
            obj.data shouldBe listOf(1.1, 2.2)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3.3, 5.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.3, 5.5)
        }
    }

    "Field from List<Double>? property" {

        data class Data(var data: List<Double>?)

        val obj = Data(listOf(1.1, 2.2))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_doubles)
            obj.data shouldBe listOf(1.1, 2.2)

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3.3, 5.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.3, 5.5)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // STRING fields
    /////

    "Field from String property" {

        data class Data(var data: String)

        val obj = Data("str")

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe ""

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe true
            obj.data shouldBe "ABC"
        }
    }

    "Field from String? property" {

        data class Data(var data: String?)

        val obj = Data("str")

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe true
            obj.data shouldBe "ABC"

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe ""
        }
    }

    "Field from List<String> property" {

        data class Data(var data: List<String>)

        val obj = Data(listOf("a", "b"))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf("ABC")

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3.3, 5.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf("3.3", "5.5")
        }
    }

    "Field from List<String>? property" {

        data class Data(var data: List<String>?)

        val obj = Data(listOf("a", "b"))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe true
            subject.data.isValid() shouldBe true
            obj.data shouldBe listOf("ABC")

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3.3, 5.5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf("3.3", "5.5")
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BIG-INTEGER fields
    /////

    "Field from BigInteger property" {

        data class Data(var data: BigInteger)

        val obj = Data(1.toBigInteger())

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_big_integer)
            obj.data shouldBe 1.toBigInteger()

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 1.toBigInteger()

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.toBigInteger()
        }
    }

    "Field from BigInteger? property" {

        data class Data(var data: BigInteger?)

        val obj = Data(1.toBigInteger())

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_big_integer_or_blank)
            obj.data shouldBe 1.toBigInteger()

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.toBigInteger()
        }
    }

    "Field from List<BigInteger> property" {

        data class Data(var data: List<BigInteger>)

        val obj = Data(listOf(1.toBigInteger(), 2.toBigInteger()))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_big_integers)
            obj.data shouldBe listOf(1.toBigInteger(), 2.toBigInteger())

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.toBigInteger(), 5.toBigInteger())
        }
    }

    "Field from List<BigInteger>? property" {

        data class Data(var data: List<BigInteger>?)

        val obj = Data(listOf(1.toBigInteger(), 2.toBigInteger()))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_big_integers)
            obj.data shouldBe listOf(1.toBigInteger(), 2.toBigInteger())

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "3, 5"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(3.toBigInteger(), 5.toBigInteger())
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BIG-DECIMAL fields
    /////

    "Field from BigDecimal property" {

        data class Data(var data: BigDecimal)

        val obj = Data(1.1.toBigDecimal())

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_big_decimal)
            obj.data shouldBe 1.1.toBigDecimal()

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 1.1.toBigDecimal()

            subject.submit(parametersOf("data", "20.2"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.2.toBigDecimal()
        }
    }

    "Field from BigDecimal? property" {

        data class Data(var data: BigDecimal?)

        val obj = Data(1.1.toBigDecimal())

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_big_decimal_or_blank)
            obj.data shouldBe 1.1.toBigDecimal()

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20.2"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.2.toBigDecimal()
        }
    }

    "Field from List<BigDecimal> property" {

        data class Data(var data: List<BigDecimal>)

        val obj = Data(listOf(10.1.toBigDecimal(), 20.2.toBigDecimal()))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_big_decimals)
            obj.data shouldBe listOf(10.1.toBigDecimal(), 20.2.toBigDecimal())

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "20.2, 10.1"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(20.2.toBigDecimal(), 10.1.toBigDecimal())
        }
    }

    "Field from List<BigDecimal>? property" {

        data class Data(var data: List<BigDecimal>?)

        val obj = Data(listOf(10.1.toBigDecimal(), 20.2.toBigDecimal()))

        class DataForm : Form() {
            val data = field(obj::data)
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            subject.data.errors shouldBe listOf(FormidableI18n.must_be_a_list_of_big_decimals)
            obj.data shouldBe listOf(10.1.toBigDecimal(), 20.2.toBigDecimal())

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf()

            subject.submit(parametersOf("data", "20.2, 10.1"))
            subject.isValid() shouldBe true
            obj.data shouldBe listOf(20.2.toBigDecimal(), 10.1.toBigDecimal())
        }
    }
})
