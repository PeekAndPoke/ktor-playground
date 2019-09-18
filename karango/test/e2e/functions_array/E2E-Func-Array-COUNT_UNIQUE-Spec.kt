package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-COUNT_UNIQUE-Spec` : StringSpec({

    val cases = listOf(
        row(
            "COUNT_UNIQUE ([])",
            COUNT_UNIQUE(ARRAY<Any>()),
            0L
        ),
        row(
            "COUNT_UNIQUE (['a'])",
            COUNT_UNIQUE(ARRAY("a".aql)),
            1L
        ),
        row(
            "COUNT_UNIQUE (['a', 'a'])",
            COUNT_UNIQUE(ARRAY("a".aql, "a".aql)),
            1L
        ),
        row(
            "COUNT_UNIQUE (['a', 'b', 'a'])",
            COUNT_UNIQUE(ARRAY("a".aql, "b".aql, "a".aql)),
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
