package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.ATAN
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-ATAN-Spec` : StringSpec({

    val cases = listOf(
        row(
            "ATAN (-1)",
            ATAN((-1).aql),
            -0.7853981633974483
        ),
        row(
            "ATAN (0)",
            ATAN(0.aql),
            0.0
        ),
        row(
            "ATAN (10)",
            ATAN(10.aql),
            1.4711276743037347
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
