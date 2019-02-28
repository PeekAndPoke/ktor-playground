package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.MIN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-MIN-Spec` : StringSpec({

    val cases = listOf(
        row(
            "MIN( [] )",
            MIN(listOf<Number>().aql),
            null
        ),        
        row(
            "MIN( [5, 9, -2, 1] )",
            MIN(listOf(5, 9, -2, 1).aql),
            -2L
        ),
        row(
            "MIN( [5, 9, -2, null, 1] )",
            MIN(listOf(5, 9, -2, null, 1).aql),
            -2L
        ),
        row(
            "MIN( [1, null] )",
            MIN(listOf(1, null).aql),
            1L
        ),
        row(
            "MIN( [null, null] )",
            MIN(listOf(null, null).aql),
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
