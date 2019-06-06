package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class SetMutationsSpec : StringSpec({

    "Mutating a set ... using size and isEmpty" {

        val source = SetOfAddresses(
            addresses = setOf()
        )

        source.mutate { draft ->

            assertSoftly {
                draft.addresses.size shouldBe 0
                draft.addresses.isEmpty() shouldBe true

                draft.addresses.add(
                    Address("Berlin", "10115"),
                    Address("Leipzig", "04109")
                )

                draft.addresses.size shouldBe 2
                draft.addresses.isEmpty() shouldBe false
            }
        }
    }

    "Mutating all objects in a set via forEach" {

        val source = SetOfAddresses(
            addresses = setOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.forEach {
                it.city += "-x"
            }
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {

                result.addresses shouldBe setOf(
                    Address("Berlin-x", "10115"),
                    Address("Leipzig-x", "04109")
                )
            }
        }
    }

    "Mutating some objects in a set via filter and forEach" {

        val source = SetOfAddresses(
            addresses = setOf(
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
                result.addresses shouldBe setOf(
                    Address("Berlin x", "10115"),
                    Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating an object by replacing a whole set" {

        val source = SetOfAddresses(
            addresses = setOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses += setOf(
                Address("Chemnitz", "09111")
            )
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe setOf(
                    Address("Chemnitz", "09111")
                )
            }
        }
    }

    "Mutating a set by clearing it via clear" {

        val source = SetOfAddresses(
            addresses = setOf(
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
                result.addresses shouldBe setOf()
            }
        }
    }

    "Mutating a set by removing elements via filter" {

        val source = SetOfAddresses(
            addresses = setOf(
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
                result.addresses shouldBe setOf(
                    Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating a set by adding elements via add" {

        val source = SetOfAddresses(
            addresses = setOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.add(
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
                result.addresses shouldBe setOf(
                    Address("Berlin", "10115"),
                    Address("Leipzig", "04109"),
                    Address("Chemnitz", "09111"),
                    Address("Bonn", "53111")
                )
            }
        }
    }

    "Mutating a set by removing elements via add remove" {

        val source = SetOfAddresses(
            addresses = setOf(
                Address("Berlin", "10115"),
                Address("Leipzig", "04109"),
                Address("Chemnitz", "09111")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.remove(
                Address("Leipzig", "04109"),
                Address("Chemnitz", "09111")
            )
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe setOf(
                    Address("Berlin", "10115")
                )
            }
        }
    }

    "Mutating a set by removing elements via removeWhere" {

        val source = SetOfAddresses(
            addresses = setOf(
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
                result.addresses shouldBe setOf(
                    Address("Berlin", "10115")
                )
            }
        }
    }

    "Mutating a set by removing elements via retainWhere" {

        val source = SetOfAddresses(
            addresses = setOf(
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
                result.addresses shouldBe setOf(
                    Address("Berlin", "10115"),
                    Address("Bonn", "53111")
                )
            }
        }
    }

})
