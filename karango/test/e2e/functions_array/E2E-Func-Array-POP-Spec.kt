package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.POP
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-POP-Spec` : StringSpec({

    val cases = listOf(
        row(
            "POP ([])",
            POP(ARRAY()),
            listOf()
        ),
        row(
            "POP ([1])",
            POP(ARRAY(1.aql)),
            listOf()
        ),
        row(
            "POP ([1, 2])",
            POP(ARRAY(1.aql, 2.aql)),
            listOf(1)
        ),
        row(
            "POP ([1, 2, 3])",
            POP(ARRAY(1.aql, 2.aql, 3.aql)),
            listOf(1, 2)
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
