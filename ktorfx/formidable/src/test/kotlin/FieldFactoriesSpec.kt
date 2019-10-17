package de.peekandpoke.ktorfx.formidable

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.ktor.http.parametersOf

class FieldFactoriesSpec : StringSpec({

    "Field from Boolean property" {

        data class Data(var data: Boolean)

        val obj = Data(true)

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
            obj.data shouldBe true

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
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
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
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

    "Field from Byte property" {

        data class Data(var data: Byte)

        val obj = Data(10)

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
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
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {

            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10.toByte()

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.toByte()
        }
    }

    "Field from Char property" {

        data class Data(var data: Char)

        val obj = Data('a')

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
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
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 'a'

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "b"))
            subject.isValid() shouldBe true
            obj.data shouldBe 'b'
        }
    }

    "Field from Short property" {

        data class Data(var data: Short)

        val obj = Data(10)

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
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
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10.toShort()

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20.toShort()
        }
    }

    "Field from Int property" {

        data class Data(var data: Int)

        val obj = Data(10)

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
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
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
            obj.data shouldBe 10

            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe true
            obj.data shouldBe null

            subject.submit(parametersOf("data", "20"))
            subject.isValid() shouldBe true
            obj.data shouldBe 20
        }
    }

    "Field from Float property" {

        data class Data(var data: Float)

        val obj = Data(10.0f)

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
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
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
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

    "Field from Double property" {

        data class Data(var data: Double)

        val obj = Data(10.0)

        class DataForm : Form() {
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", ""))
            subject.isValid() shouldBe false
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
            init {
                field(obj::data)
            }
        }

        val subject = DataForm().noCsrf()

        assertSoftly {
            subject.submit(parametersOf("data", "ABC"))
            subject.isValid() shouldBe false
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
})
