package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.ACOS
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-ACOS-Spec` : StringSpec({

    val cases = listOf(
        row(
            "ACOS (-1)",
            ACOS((-1).aql),
            3.141592653589793
        ),
        row(
            "ACOS (0)",
            ACOS(0.aql),
            1.5707963267948966
        ),
        row(
            "ACOS (1)",
            ACOS(1.aql),
            0.0
        ),
        row(
            "ACOS (2)",
            ACOS(2.aql),
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
