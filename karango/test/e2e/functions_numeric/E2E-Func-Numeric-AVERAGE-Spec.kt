package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.AVERAGE
import de.peekandpoke.karango.aql.AVG
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-AVERAGE-Spec` : StringSpec({

    val cases = listOf(
        row(
            "AVERAGE( [5, 2, 9, 2] )",
            AVERAGE(listOf(5, 2, 9, 2).aql),
            4.5
        ),
        row(
            "AVERAGE( [ -3, -5, 2 ] )",
            AVERAGE(listOf(-3, -5, 2).aql),
            -2.0
        ),
        row(
            "AVG( [ 999, 80, 4, 4, 4, 3, 3, 3 ] )",
            AVG(listOf(999, 80, 4, 4, 4, 3, 3, 3).aql),
            137.5
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
