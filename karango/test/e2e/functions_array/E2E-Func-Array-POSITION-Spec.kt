package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-POSITION-Spec` : StringSpec({

    val cases = listOf(
        row(
            "POSITION ([], 0)",
            POSITION(ARRAY(), 0.aql),
            false
        ),
        row(
            "POSITION ([1], 1)",
            POSITION(ARRAY(1.aql), 1.aql),
            true
        ),
        row(
            "POSITION ([1, 2, 3], 3)",
            POSITION(ARRAY(1.aql, 2.aql, 3.aql), 3.aql),
            true
        ),
        row(
            "POSITION ([1, 2, 3], 4)",
            POSITION(ARRAY(1.aql, 2.aql, 3.aql), 4.aql),
            false
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
