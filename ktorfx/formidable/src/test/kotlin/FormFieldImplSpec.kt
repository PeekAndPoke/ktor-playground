package de.peekandpoke.ktorfx.formidable

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.ktor.http.parametersOf

class FormFieldImplSpec : StringSpec({

    val parentForm = object : Form("") {}

    fun createSimpleField(setter: (String) -> Unit = {}) = FormFieldImpl(
        parentForm,
        "field",
        "initial value",
        setter,
        { it },
        { it }
    )

    "Field must be invalid when the fields value is missing in the submitted parameters" {

        val subject = createSimpleField()

        subject.submit(parametersOf())

        assertSoftly {
            subject.isValid() shouldBe false

            subject.errors shouldBe listOf(FormidableI18n.must_not_be_empty)
        }
    }

    "Field must be invalid when accept rules are violated" {

        val subject = createSimpleField()
            .acceptsNonBlank()
            .acceptsNonEmpty()

        subject.submit(parametersOf("field", " "))

        assertSoftly {
            subject.isValid() shouldBe false

            subject.errors shouldBe listOf(FormidableI18n.must_not_be_blank)
        }
    }

    "Field must be invalid when result rules are violated" {

        val subject = createSimpleField()
            .resultingInAnyOf(setOf("a", "b"))

        subject.submit(parametersOf("field", "c"))

        assertSoftly {
            subject.isValid() shouldBe false

            subject.errors shouldBe listOf(FormidableI18n.invalid_value)
        }
    }

    "Field must be valid when passing all rules" {

        val subject = createSimpleField()
            .acceptsNonEmpty()
            .resultingInAnyOf(setOf("a", "b"))

        subject.submit(parametersOf("field", "b"))

        assertSoftly {
            subject.isValid() shouldBe true
        }
    }

    "Field mappers must be applied" {

        var setValue = ""

        val subject = createSimpleField { setValue = it }
            .addMapper { it.toUpperCase() }

        assertSoftly {
            subject.textValue shouldBe "INITIAL VALUE"

            subject.submit(parametersOf("field", "something else"))
            setValue shouldBe "SOMETHING ELSE"
            subject.textValue shouldBe "SOMETHING ELSE"

            subject.mapToView("abc") shouldBe "ABC"
        }
    }
})
