package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.POW
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-POW-Spec` : StringSpec({

    val cases = listOf(
        row(
            "POW( 2, 4 )",
            POW( 2.aql, 4.aql ),
            16.0
        ),
        row(
            "POW( 5, -1 )",
            POW( 5.aql, (-1).aql ),
            0.2
        ),
        row(
            "POW( 5, 0 )",
            POW( 5.aql, 0.aql ),
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
