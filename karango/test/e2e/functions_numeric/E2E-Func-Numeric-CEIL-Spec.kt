package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.CEIL
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-CEIL-Spec` : StringSpec({

    val cases = listOf(
        row(
            "CEIL(2)",
            CEIL(2.aql),
            2.0
        ),
        row(
            "CEIL(2.49)",
            CEIL(2.49.aql),
            3.0
        ),
        row(
            "CEIL(2.50)",
            CEIL(2.50.aql),
            3.0
        ),
        row(
            "CEIL(-2.50)",
            CEIL((-2.50).aql),
            -2.0
        ),
        row(
            "CEIL(-2.51)",
            CEIL((-2.51).aql),
            -2.0
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
