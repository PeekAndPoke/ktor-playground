package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.LOG2
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-LOG2-Spec` : StringSpec({

    val cases = listOf(
        row(
            "LOG2(1024)",
            LOG2(1024.aql),
            10.0
        ),
        row(
            "LOG2(8)",
            LOG2(8.aql),
            3.0
        ),
        row(
            "LOG2(0)",
            LOG2(0.aql),
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
