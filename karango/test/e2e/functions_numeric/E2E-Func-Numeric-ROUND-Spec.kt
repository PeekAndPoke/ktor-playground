package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.ROUND
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-ROUND-Spec` : StringSpec({

    val cases = listOf(
        row(
            "ROUND(2.49)",
            ROUND(2.49.aql),
            2.0
        ),
        row(
            "ROUND(2.50)",
            ROUND(2.50.aql),
            3.0
        ),
        row(
            "ROUND(-2.50)",
            ROUND((-2.50).aql),
            -2.0
        ),
        row(
            "ROUND(-2.51)",
            ROUND((-2.51).aql),
            -3.0
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
