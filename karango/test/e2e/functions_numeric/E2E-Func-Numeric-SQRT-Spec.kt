package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.SQRT
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-SQRT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "SQRT(9)",
            SQRT(9.aql),
            3.0
        ),
        row(
            "SQRT(2)",
            SQRT(2.aql),
            1.4142135623730951
        ),
        row(
            "SQRT(0)",
            SQRT(0.aql),
            0.0
        ),
        row(
            "SQRT(-1)",
            SQRT((-1).aql),
            null
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = db.query {
                RETURN(expression)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
