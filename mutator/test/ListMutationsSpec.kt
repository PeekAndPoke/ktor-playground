package de.peekandpoke.mutator

import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class ListMutationsSpec : StringSpec({

    "Mutating properties of objects in a list" {

        val source = ListOfAddress(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            with(draft.addresses[0]) {
                city { toUpperCase() }
            }
        }

        withClue("Source object must NOT be modified") {
            source shouldNotBe result
            source.addresses shouldNotBe result.addresses
        }

        withClue("First list element must be modified") {
            source.addresses[0] shouldNotBe result.addresses[0]
        }

        withClue("Second list element must stay the same") {
            source.addresses[1] shouldBe result.addresses[1]
        }

        withClue("Result must be modified properly") {
            result.addresses[0] shouldBe Address("BERLIN", "10115")
            result.addresses[1] shouldBe Address("Leipzig", "04109")
        }
    }

    "Mutating all objects in a list via forEach" {

        val source = ListOfAddress(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.forEach {
                it.city += " x"
            }
        }

        withClue("Source object must NOT be modified") {
            source shouldNotBe result
            source.addresses shouldNotBe result.addresses
        }

        withClue("Result must be modified properly") {
            result.addresses[0] shouldBe Address("Berlin x", "10115")
            result.addresses[1] shouldBe Address("Leipzig x", "04109")
        }
    }
})
