package de.peekandpoke.mutator

import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class NestedObjectMutationsSpec : StringSpec({

    "Mutating properties of root object only" {

        val source = Company("Corp", Person("Sam", 25, Address("Berlin", "10115")))

        val result = source.mutate { draft ->

            draft.name { plus("oration").toUpperCase() }
        }

        withClue("Source object must NOT be modified") {
            source shouldNotBe result
        }

        withClue("Nested object must stay identical, as it was not modified") {
            source.boss shouldBe result.boss
        }

        withClue("Result must be modified properly") {
            result.name shouldBe "CORPORATION"
        }
    }

    "Mutating properties of a nested object" {

        val source = Company("Corp", Person("Sam", 25, Address("Berlin", "10115")))

        val result = source.mutate { draft ->

            draft.name { plus("oration").toUpperCase() }

            with(draft.boss.address) {
                city = "Leipzig"
                zip = "04109"
            }
        }

        withClue("Source object must NOT be modified") {
            source shouldNotBe result
        }

        withClue("Result must be modified properly") {
            result.name shouldBe "CORPORATION"

            result.boss shouldNotBe source.boss

            result.boss.address shouldNotBe source.boss.address
            result.boss.address.city shouldBe "Leipzig"
            result.boss.address.zip shouldBe "04109"
        }
    }
})
