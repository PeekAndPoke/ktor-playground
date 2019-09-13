package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.FLOOR
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-FLOOR-Spec` : StringSpec({

    val cases = listOf(
        row(
            "FLOOR(2.50)",
            FLOOR(2.50.aql),
            2.0
        ),
        row(
            "FLOOR(2.51)",
            FLOOR(2.51.aql),
            2.0
        ),
        row(
            "FLOOR(-2.49)",
            FLOOR((-2.49).aql),
            -3.0
        ),
        row(
            "FLOOR(-2.50)",
            FLOOR((-2.50).aql),
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
