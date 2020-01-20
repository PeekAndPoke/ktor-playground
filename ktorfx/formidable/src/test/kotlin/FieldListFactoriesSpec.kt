package de.peekandpoke.ktorfx.formidable

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.ktor.http.parametersOf

class FieldListFactoriesSpec : StringSpec({

    "Submitting a form with a List field for an object with a list of strings" {

        data class ListOfStrings(
            var strings: MutableList<String> = mutableListOf("a", "b")
        )

        val listWithStrings = ListOfStrings()

        class ListWithStringsForm(target: ListOfStrings) : Form("form") {

            init {
                noCsrf()
                noSubmissionCheck()
            }

            val strings = list(target::strings) {
                field(it::value).acceptsNonEmpty()
            }
        }

        val form = ListWithStringsForm(listWithStrings)

        form.submit(
            parametersOf(
                Pair("form.strings.0.value", listOf("NEW")),
                Pair("form.strings.1.value", listOf("VALUE"))
            )
        )

        assertSoftly {

            form.getId() shouldBe FormElementId("form")
            form.isValid() shouldBe true

            form.strings.getId() shouldBe FormElementId("form.strings")
            form.strings.isValid() shouldBe true

            form.strings.items[0].getId() shouldBe FormElementId("form.strings.0.value")

            listWithStrings.strings shouldBe listOf("NEW", "VALUE")
        }

    }

    "Submitting a form with a List field for a nested object with a list of strings" {

        data class ListOfStrings(
            var strings: MutableList<String> = mutableListOf("a", "b")
        )

        class ListWithStringsForm(target: ListOfStrings) : Form("nested") {

            val strings = list(target::strings) {
                field(it::value).acceptsNonEmpty()
            }
        }

        data class SomeObject(
            var nested: ListOfStrings = ListOfStrings()
        )

        class SomeObjectForm(target: SomeObject) : Form("some-object") {

            init {
                noCsrf()
                noSubmissionCheck()
            }

            val nested = subForm(ListWithStringsForm(target.nested))
        }

        val someObject = SomeObject()

        val form = SomeObjectForm(someObject)

        form.submit(
            parametersOf(
                Pair("some-object.nested.strings.0.value", listOf("NEW")),
                Pair("some-object.nested.strings.1.value", listOf("VALUE"))
            )
        )

        assertSoftly {
            form.getId() shouldBe FormElementId("some-object")
            form.isValid() shouldBe true

            form.nested.getId() shouldBe FormElementId("some-object.nested")
            form.nested.isValid() shouldBe true

            form.nested.strings.getId() shouldBe FormElementId("some-object.nested.strings")
            form.nested.strings.isValid() shouldBe true

            someObject.nested.strings shouldBe listOf("NEW", "VALUE")
        }
    }
})
