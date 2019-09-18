package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-PERCENTILE-Spec` : StringSpec({

    val cases = listOf(
        row(
            "PERCENTILE( [1, 2, 3, 4], 50 )",
            PERCENTILE(listOf(1, 2, 3, 4).aql, 50.aql),
            2.0
        ),
        row(
            "PERCENTILE( [1, 2, 3, 4], 50, \"rank\" )",
            PERCENTILE(listOf(1, 2, 3, 4).aql, 50.aql, PercentileMethod.RANK),
            2.0
        ),
        row(
            "PERCENTILE( [1, 2, 3, 4], 50, \"interpolation\" )",
            PERCENTILE(listOf(1, 2, 3, 4).aql, 50.aql, PercentileMethod.INTERPOLATION),
            2.5
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
