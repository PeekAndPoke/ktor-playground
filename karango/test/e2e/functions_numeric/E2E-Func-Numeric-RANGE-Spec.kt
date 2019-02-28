package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.RANGE
import de.peekandpoke.karango.aql.TerminalExpr
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-RANGE-Spec` : StringSpec({

    val cases = listOf(
        row(
            "RANGE( 1, 4 )",
            RANGE(1.aql, 4.aql),
            listOf(1L, 2L, 3L, 4L)
        ),
        row(
            "RANGE( 1, 4, 2 )",
            RANGE(1.aql, 4.aql, 2.aql),
            listOf(1.0, 3.0)
        ),
        row(
            "RANGE( 1, 4, -2 )",
            RANGE(1.aql, 4.aql, (-2).aql),
            null
        ),
        row(
            "RANGE( 1.5, 4.4 )",
            RANGE(1.5.aql, 4.4.aql),
            listOf(1L, 2L, 3L, 4L)
        ),
        row(
            "RANGE( 1.5, 4.4, 1.0 )",
            RANGE(1.5.aql, 4.4.aql, 1.0.aql),
            listOf(1.5, 2.5, 3.5)
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = db.query {
                @Suppress("UNCHECKED_CAST")
                RETURN(expression) as TerminalExpr<List<Number>> // Don't do this at home... just a workaround for the different return types 
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                @Suppress("UNCHECKED_CAST")
                RETURN(l) as TerminalExpr<List<Number>>  // Don't do this at home... just a workaround for the different return types 
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
