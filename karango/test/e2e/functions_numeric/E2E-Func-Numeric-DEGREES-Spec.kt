package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.DEGREES
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-DEGREES-Spec` : StringSpec({

    val cases = listOf(
        row(
            "DEGREES(0.7853981633974483)",
            DEGREES(0.7853981633974483.aql),
            45.0
        ),
        row(
            "DEGREES(0)",
            DEGREES(0.aql),
            0.0
        ),
        row(
            "DEGREES(3.141592653589793)",
            DEGREES(3.141592653589793.aql),
            180.0
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
