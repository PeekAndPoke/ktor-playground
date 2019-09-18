package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.ASIN
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-ASIN-Spec` : StringSpec({

    val cases = listOf(
        row(
            "ASIN (1)",
            ASIN(1.aql),
            1.5707963267948966
        ),
        row(
            "ASIN (0)",
            ASIN(0.aql),
            0.0
        ),
        row(
            "ASIN (-1)",
            ASIN((-1).aql),
            -1.5707963267948966
        ),
        row(
            "ASIN (2)",
            ASIN(2.aql),
            null
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
