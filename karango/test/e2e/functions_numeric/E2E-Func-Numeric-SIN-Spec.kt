package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-SIN-Spec` : StringSpec({

    val cases = listOf(
        row(
            "SIN(3.141592653589783 / 2)",
            SIN((3.141592653589783 / 2).aql),
            1.0
        ),
        row(
            "SIN(0)",
            SIN(0.aql),
            0.0
        ),
        row(
            "SIN(-3.141592653589783 / 2)",
            SIN((-3.141592653589783 / 2).aql),
            -1.0
        ),
        row(
            "SIN(RADIANS(270))",
            SIN(RADIANS(270.aql)),
            -1.0
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
