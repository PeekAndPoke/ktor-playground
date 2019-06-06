package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class ListMutationsSpec : StringSpec({

    "Mutating a list ... using size and isEmpty" {

        val source = ListOfAddresses(
            addresses = listOf()
        )

        source.mutate { draft ->

            assertSoftly {
                draft.addresses.size shouldBe 0
                draft.addresses.isEmpty() shouldBe true

                draft.addresses.push(
                    Address("Berlin", "10115"),
                    Address("Leipzig", "04109")
                )

                draft.addresses.size shouldBe 2
                draft.addresses.isEmpty() shouldBe false
            }
        }
    }

    "Mutating properties of elements in a list" {

        val source = ListOfAddresses(
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

        assertSoftly {

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
    }

    "Mutating all elements in a list via forEach" {

        val source = ListOfAddresses(
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

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses[0] shouldBe Address("Berlin x", "10115")
                result.addresses[1] shouldBe Address("Leipzig x", "04109")
            }
        }
    }

    "Mutating some elements in a list via filter and forEach" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses
                .filter { it.city == "Berlin" }
                .forEach {
                    it.city += " x"
                }
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses[0] shouldBe Address("Berlin x", "10115")
                result.addresses[1] shouldBe Address("Leipzig", "04109")
            }
        }
    }

    "Mutating a list by adding elements via push" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.push(
                Address("Chemnitz", "09111"),
                Address("Bonn", "53111")
            )
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Berlin", "10115"),
                    Address("Leipzig", "04109"),
                    Address("Chemnitz", "09111"),
                    Address("Bonn", "53111")
                )
            }
        }
    }

    "Mutating a list by removing elements via pop" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        lateinit var popped: Address

        val result = source.mutate { draft ->
            popped = draft.addresses.pop()
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("The last entry must be popped correctly") {
                popped shouldBe Address("Leipzig", "04109")
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Berlin", "10115")
                )
            }
        }
    }

    "Mutating a list by adding elements via unshift" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.unshift(
                Address("Chemnitz", "09111"),
                Address("Bonn", "53111")
            )
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Chemnitz", "09111"),
                    Address("Bonn", "53111"),
                    Address("Berlin", "10115"),
                    Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating a list by removing elements via shift" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        lateinit var shifted: Address

        val result = source.mutate { draft ->
            shifted = draft.addresses.shift()
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("The first entry must be shifted correctly") {
                shifted shouldBe Address("Berlin", "10115")
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating a list by replacing a whole list" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses += listOf(
                Address("Chemnitz", "09111")
            )
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Chemnitz", "09111")
                )
            }
        }
    }

    "Mutating a list by clearing it via clear" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.clear()
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf()
            }
        }
    }

    "Mutating a list by removing elements via filter" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses += draft.addresses.filter { it.city == "Leipzig"}
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating a list by removing elements via removeWhere" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.removeWhere { it.city == "Leipzig"}
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Berlin", "10115")
                )
            }
        }
    }

    "Mutating a list by removing elements via retainWhere" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109"),
                Address("Bonn", "53111")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.retainWhere { it.city.startsWith("B")}
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Berlin", "10115"),
                    Address("Bonn", "53111")
                )
            }
        }
    }

    "Mutating a list by removing elements via remove" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109"),
                Address("Bonn", "53111")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.remove(
                Address("Berlin", "10115"),
                Address("Bonn", "53111")
            )
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating a list by removing elements via removeAt" {

        val source = ListOfAddresses(
            addresses = listOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109"),
                Address("Bonn", "53111")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.removeAt(1)
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe listOf(
                    Address("Berlin", "10115"),
                    Address("Bonn", "53111")
                )
            }
        }
    }
})
