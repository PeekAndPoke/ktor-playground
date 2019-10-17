package de.peekandpoke.ktorfx.formidable

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.ktor.http.parametersOf

class FormSpec : StringSpec({

    data class TestObj(
        var anInt: Int = 0,
        var anOptionalInt: Int? = 0
    )

    data class ParentObject(
        var name: String = "",
        val child: TestObj = TestObj()
    )

    class TestForm(target: TestObj, name: String = "", parent: Form? = null) : Form(name, parent) {
        init {
            csrfRequired = false
        }

        val anInt = field(target::anInt).resultingInRange(-100..100)

        val anOptionalInt = field(target::anOptionalInt).resultingInRange(-100..100)
    }

    class ParentForm(target: ParentObject) : Form() {
        init {
            csrfRequired = false
        }

        val name = field(target::name).acceptsNonBlank()

        val child = add(TestForm(target.child, "child", this))
    }

    "Submitting a form should by default throw an error when no csrf field is present" {

        class BrokenForm : Form()

        val form = BrokenForm()

        shouldThrow<InsecureFormException> {
            form.submit(parametersOf())
        }
    }

    "Submitting valid parameters to a form" {

        val obj = TestObj()
        val subject = TestForm(obj)

        subject.submit(
            parametersOf(
                "anInt" to listOf("10"),
                "anOptionalInt" to listOf("20")
            )
        )

        assertSoftly {
            subject.isValid() shouldBe true

            subject.anInt.isValid() shouldBe true
            subject.anOptionalInt.isValid() shouldBe true

            obj.anInt shouldBe 10
            obj.anOptionalInt shouldBe 20
        }
    }

    "Submitting with missing parameters" {

        val obj = TestObj()
        val subject = TestForm(obj)

        subject.submit(parametersOf())

        assertSoftly {
            subject.isValid() shouldBe false

            subject.anInt.isValid() shouldBe false
            subject.anOptionalInt.isValid() shouldBe false
        }
    }

    "Submitting a form with a nested form in it" {

        val obj = ParentObject()
        val subject = ParentForm(obj)

        subject.submit(
            parametersOf(
                "name" to listOf("name"),
                "child.anInt" to listOf("10"),
                "child.anOptionalInt" to listOf("20")
            )
        )

        assertSoftly {
            subject.isValid() shouldBe true

            subject.name.isValid() shouldBe true
            obj.name shouldBe "name"

            subject.child.isValid() shouldBe true

            subject.child.anInt.isValid() shouldBe true
            obj.child.anInt shouldBe 10

            subject.child.anOptionalInt.isValid() shouldBe true
            obj.child.anOptionalInt shouldBe 20
        }
    }
})
