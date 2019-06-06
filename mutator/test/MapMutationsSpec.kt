package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class MapMutationsSpec : StringSpec({

    "Mutating a list ... using size and isEmpty" {

        val source = MapOfAddresses(
            addresses = mapOf()
        )

        source.mutate { draft ->

            assertSoftly {
                draft.addresses.size shouldBe 0
                draft.addresses.isEmpty() shouldBe true

                draft.addresses.put(
                    "B" to Address("Berlin", "10115"),
                    "L" to Address("Leipzig", "04109")
                )

                draft.addresses.size shouldBe 2
                draft.addresses.isEmpty() shouldBe false
            }
        }
    }

    "Mutating properties of elements in a map" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            with(draft.addresses["B"]) {
                city { toUpperCase() }
            }
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("First list element must be modified") {
                source.addresses["B"] shouldNotBe result.addresses["B"]
            }

            withClue("Second list element must stay the same") {
                source.addresses["L"] shouldBe result.addresses["L"]
            }

            withClue("Result must be modified properly") {

                result.addresses shouldBe mapOf(
                    "B" to Address("BERLIN", "10115"),
                    "L" to Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating a map by overwriting an element via set" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses["B"] = Address("Bonn", "53111")
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("First list element must be modified") {
                source.addresses["B"] shouldNotBe result.addresses["B"]
            }

            withClue("Second list element must stay the same") {
                source.addresses["L"] shouldBe result.addresses["L"]
            }

            withClue("Result must be modified properly") {

                result.addresses shouldBe mapOf(
                    "B" to Address("Bonn", "53111"),
                    "L" to Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating a map by adding an element via set" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses["C"] = Address("Chemnitz", "09111")
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Source map elements must stay the same") {
                source.addresses shouldBe mapOf(
                    "B" to Address("Berlin", "10115"),
                    "L" to Address("Leipzig", "04109")
                )
            }

            withClue("Result must be modified properly") {

                result.addresses shouldBe mapOf(
                    "B" to Address("Berlin", "10115"),
                    "L" to Address("Leipzig", "04109"),
                    "C" to Address("Chemnitz", "09111")
                )
            }
        }
    }

    "Mutating a map by adding elements via put" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.put(
                "C" to Address("Chemnitz", "09111"),
                "O" to Address("Bonn", "53111")
            )
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Source map elements must stay the same") {
                source.addresses shouldBe mapOf(
                    "B" to Address("Berlin", "10115"),
                    "L" to Address("Leipzig", "04109")
                )
            }

            withClue("Result must be modified properly") {

                result.addresses shouldBe mapOf(
                    "B" to Address("Berlin", "10115"),
                    "L" to Address("Leipzig", "04109"),
                    "C" to Address("Chemnitz", "09111"),
                    "O" to Address("Bonn", "53111")
                )
            }
        }
    }

    "Mutating all elements in a map via forEach" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses.forEach {
                it.value.city += " x"
            }
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe mapOf(
                    "B" to Address("Berlin x", "10115"),
                    "L" to Address("Leipzig x", "04109")
                )
            }
        }
    }

    "Mutating a map by clearing it via clear" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109")
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
                result.addresses shouldBe mapOf()
            }
        }
    }

    "Mutating a map by removing elements via filter" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109")
            )
        )

        1 to 2

        val result = source.mutate { draft ->
            draft.addresses += draft.addresses.filter { it.value.city == "Leipzig"}
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe mapOf(
                    "L" to Address("Leipzig", "04109")
                )
            }
        }
    }

    "Mutating some elements in a map via filter and forEach" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109"),
                "C" to Address("Chemnitz", "09111"),
                "O" to Address("Bonn", "53111")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses
                .filter { it.key == "L" || it.value.city.startsWith("B") }
                .forEach { it.value.city += " x" }
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe mapOf(
                    "B" to Address("Berlin x", "10115"),
                    "L" to Address("Leipzig x", "04109"),
                    "C" to Address("Chemnitz", "09111"), // This entry does not match the filter
                    "O" to Address("Bonn x", "53111")
                )
            }
        }
    }

    "Mutating a map by replacing the whole map" {

        val source = MapOfAddresses(
            addresses = mapOf(
                "B" to Address("Berlin", "10115"),
                "L" to Address("Leipzig", "04109")
            )
        )

        val result = source.mutate { draft ->
            draft.addresses += mapOf(
                "C" to Address("Chemnitz", "09111")
            )
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.addresses shouldNotBe result.addresses
            }

            withClue("Result must be modified properly") {
                result.addresses shouldBe mapOf(
                    "C" to Address("Chemnitz", "09111")
                )
            }
        }
    }
//
//    "Mutating a list by clearing it via clear" {
//
//        val source = ListOfAddresses(
//            addresses = listOf(
//                Address("Berlin", "10115"),
//                Address("Leipzig", "04109")
//            )
//        )
//
//        val result = source.mutate { draft ->
//            draft.addresses.clear()
//        }
//
//        assertSoftly {
//
//            withClue("Source object must NOT be modified") {
//                source shouldNotBe result
//                source.addresses shouldNotBe result.addresses
//            }
//
//            withClue("Result must be modified properly") {
//                result.addresses shouldBe listOf()
//            }
//        }
//    }
//
//    "Mutating a list by removing elements via filter" {
//
//        val source = ListOfAddresses(
//            addresses = listOf(
//                Address("Berlin", "10115"),
//                Address("Leipzig", "04109")
//            )
//        )
//
//        val result = source.mutate { draft ->
//            draft.addresses += draft.addresses.filter { it.city == "Leipzig"}
//        }
//
//        assertSoftly {
//
//            withClue("Source object must NOT be modified") {
//                source shouldNotBe result
//                source.addresses shouldNotBe result.addresses
//            }
//
//            withClue("Result must be modified properly") {
//                result.addresses shouldBe listOf(
//                    Address("Leipzig", "04109")
//                )
//            }
//        }
//    }
//
//    "Mutating a list by removing elements via removeWhere" {
//
//        val source = ListOfAddresses(
//            addresses = listOf(
//                Address("Berlin", "10115"),
//                Address("Leipzig", "04109")
//            )
//        )
//
//        val result = source.mutate { draft ->
//            draft.addresses.removeWhere { it.city == "Leipzig"}
//        }
//
//        assertSoftly {
//
//            withClue("Source object must NOT be modified") {
//                source shouldNotBe result
//                source.addresses shouldNotBe result.addresses
//            }
//
//            withClue("Result must be modified properly") {
//                result.addresses shouldBe listOf(
//                    Address("Berlin", "10115")
//                )
//            }
//        }
//    }
//
//    "Mutating a list by removing elements via retainWhere" {
//
//        val source = ListOfAddresses(
//            addresses = listOf(
//                Address("Berlin", "10115"),
//                Address("Leipzig", "04109"),
//                Address("Bonn", "53111")
//            )
//        )
//
//        val result = source.mutate { draft ->
//            draft.addresses.retainWhere { it.city.startsWith("B")}
//        }
//
//        assertSoftly {
//
//            withClue("Source object must NOT be modified") {
//                source shouldNotBe result
//                source.addresses shouldNotBe result.addresses
//            }
//
//            withClue("Result must be modified properly") {
//                result.addresses shouldBe listOf(
//                    Address("Berlin", "10115"),
//                    Address("Bonn", "53111")
//                )
//            }
//        }
//    }
//
//    "Mutating a list by removing elements via remove" {
//
//        val source = ListOfAddresses(
//            addresses = listOf(
//                Address("Berlin", "10115"),
//                Address("Leipzig", "04109"),
//                Address("Bonn", "53111")
//            )
//        )
//
//        val result = source.mutate { draft ->
//            draft.addresses.remove(
//                Address("Berlin", "10115"),
//                Address("Bonn", "53111")
//            )
//        }
//
//        assertSoftly {
//
//            withClue("Source object must NOT be modified") {
//                source shouldNotBe result
//                source.addresses shouldNotBe result.addresses
//            }
//
//            withClue("Result must be modified properly") {
//                result.addresses shouldBe listOf(
//                    Address("Leipzig", "04109")
//                )
//            }
//        }
//    }
//
//    "Mutating a list by removing elements via removeAt" {
//
//        val source = ListOfAddresses(
//            addresses = listOf(
//                Address("Berlin", "10115"),
//                Address("Leipzig", "04109"),
//                Address("Bonn", "53111")
//            )
//        )
//
//        val result = source.mutate { draft ->
//            draft.addresses.removeAt(1)
//        }
//
//        assertSoftly {
//
//            withClue("Source object must NOT be modified") {
//                source shouldNotBe result
//                source.addresses shouldNotBe result.addresses
//            }
//
//            withClue("Result must be modified properly") {
//                result.addresses shouldBe listOf(
//                    Address("Berlin", "10115"),
//                    Address("Bonn", "53111")
//                )
//            }
//        }
//    }
})