package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.EXP
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-EXP-Spec` : StringSpec({

    val cases = listOf(
        row(
            "EXP(1)",
            EXP(1.aql),
            2.7182818284590455
        ),
        row(
            "EXP(10)",
            EXP(10.aql),
            22026.465794806718
        ),
        row(
            "EXP(0)",
            EXP(0.aql),
            1.0
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
