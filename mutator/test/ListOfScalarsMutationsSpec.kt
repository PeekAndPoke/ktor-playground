package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class ListOfScalarsMutationsSpec : StringSpec({

    ////  List of strings  //////////////////////////////////////////////////////////////////

    "Mutating a list of strings by removing elements via removeWhere" {

        val source = ListOfStrings(values = listOf("Berlin", "Leipzig"))

        val result = source.mutate { draft ->
            draft.values.removeWhere { it.startsWith("L")}
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.values shouldNotBe result.values
            }

            withClue("Result must be modified properly") {
                result.values shouldBe listOf("Berlin")
            }
        }
    }

    "Mutating a list of strings by removing elements via retainWhere" {

        val source = ListOfStrings(values = listOf("Berlin", "Leipzig"))

        val result = source.mutate { draft ->
            draft.values.retainWhere { it.startsWith("L")}
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.values shouldNotBe result.values
            }

            withClue("Result must be modified properly") {
                result.values shouldBe listOf("Leipzig")
            }
        }
    }

    ////  List of ints  //////////////////////////////////////////////////////////////////////

    "Mutating a list of ints by removing elements via removeWhere" {

        val source = ListOfInts(values = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9))

        val result = source.mutate { draft ->
            draft.values.removeWhere { it  > 5 }
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.values shouldNotBe result.values
            }

            withClue("Result must be modified properly") {
                result.values shouldBe listOf(1, 2, 3, 4, 5)
            }
        }
    }

    "Mutating a list of ints by removing elements via retainWhere" {

        val source = ListOfInts(values = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9))

        val result = source.mutate { draft ->
            draft.values.retainWhere { it > 5}
        }

        assertSoftly {

            withClue("Source object must NOT be modified") {
                source shouldNotBe result
                source.values shouldNotBe result.values
            }

            withClue("Result must be modified properly") {
                result.values shouldBe listOf(6, 7, 8, 9)
            }
        }
    }

})
