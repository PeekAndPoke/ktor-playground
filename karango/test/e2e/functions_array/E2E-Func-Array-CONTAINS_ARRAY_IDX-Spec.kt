package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-CONTAINS_ARRAY_IDX-Spec` : StringSpec({

    val cases = listOf(
        row(
            "CONTAINS_ARRAY_IDX ([], 0)",
            CONTAINS_ARRAY_IDX(ARRAY(), 0.aql),
            -1L
        ),
        row(
            "CONTAINS_ARRAY_IDX ([1], 1)",
            CONTAINS_ARRAY_IDX(ARRAY(1.aql), 1.aql),
            0L
        ),
        row(
            "CONTAINS_ARRAY_IDX ([1, 2, 3], 3)",
            CONTAINS_ARRAY_IDX(ARRAY(1.aql, 2.aql, 3.aql), 3.aql),
            2L
        ),
        row(
            "CONTAINS_ARRAY_IDX ([1, 2, 3], 4)",
            CONTAINS_ARRAY_IDX(ARRAY(1.aql, 2.aql, 3.aql), 4.aql),
            -1L
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
