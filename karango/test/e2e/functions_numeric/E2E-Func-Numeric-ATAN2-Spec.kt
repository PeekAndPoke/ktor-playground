package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.ATAN2
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-ATAN2-Spec` : StringSpec({

    val cases = listOf(
        row(
            "ATAN2(0, 0)",
            ATAN2(0.aql, 0.aql),
            0.0
        ),
        row(
            "ATAN2(1, 0)",
            ATAN2(1.aql, 0.aql),
            1.5707963267948966
        ),
        row(
            "ATAN2(1, 1)",
            ATAN2(1.aql, 1.aql),
            0.7853981633974483
        ),
        row(
            "ATAN2(-10, 20)",
            ATAN2((-10).aql, 20.aql),
            -0.4636476090008061
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
