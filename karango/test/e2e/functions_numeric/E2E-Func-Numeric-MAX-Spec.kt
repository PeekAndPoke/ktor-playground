package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.MAX
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-MAX-Spec` : StringSpec({

    val cases = listOf(
        row(
            "MAX( [] )",
            MAX(listOf<Number>().aql),
            null
        ),        
        row(
            "MAX( [5, 9, -2, 1] )",
            MAX(listOf(5, 9, -2, 1).aql),
            9L
        ),
        row(
            "MAX( [5, 9, -2, null, 1] )",
            MAX(listOf(5, 9, -2, null, 1).aql),
            9L
        ),
        row(
            "MAX( [null, null] )",
            MAX(listOf(null, null).aql),
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
