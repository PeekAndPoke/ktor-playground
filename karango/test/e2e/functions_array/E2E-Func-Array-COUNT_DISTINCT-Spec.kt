package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-COUNT_DISTINCT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "COUNT_DISTINCT ([])",
            COUNT_DISTINCT(ARRAY<Any>()),
            0L
        ),
        row(
            "COUNT_DISTINCT (['a'])",
            COUNT_DISTINCT(ARRAY("a".aql)),
            1L
        ),
        row(
            "COUNT_DISTINCT (['a', 'a'])",
            COUNT_DISTINCT(ARRAY("a".aql, "a".aql)),
            1L
        ),
        row(
            "COUNT_DISTINCT (['a', 'b', 'a'])",
            COUNT_DISTINCT(ARRAY("a".aql, "b".aql, "a".aql)),
            2L
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = driver.query {
                RETURN(expression)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = driver.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
