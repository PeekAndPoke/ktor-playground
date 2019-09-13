package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-NTH-Spec` : StringSpec({

    val cases = listOf(
        row(
            "NTH ([], 0)",
            NTH(ARRAY(), 0.aql),
            null
        ),
        row(
            "NTH ([1], 0)",
            NTH(ARRAY(1.aql), 0.aql),
            1
        ),
        row(
            "NTH ([1], -1)",
            NTH(ARRAY(1.aql), (-1).aql),
            null
        ),
        row(
            "NTH ([1], 1)",
            NTH(ARRAY(1.aql), 1.aql),
            null
        ),
        row(
            "NTH ([1, 2], 0)",
            NTH(ARRAY(1.aql, 2.aql), 0.aql),
            1
        ),
        row(
            "NTH ([1, 2], 1)",
            NTH(ARRAY(1.aql, 2.aql), 1.aql),
            2
        ),
        row(
            "NTH ([1, 2], 2)",
            NTH(ARRAY(1.aql, 2.aql), 2.aql),
            null
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = db.query {
                @Suppress("UNCHECKED_CAST")
                RETURN(expression) as TerminalExpr<Any>
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                @Suppress("UNCHECKED_CAST")
                RETURN(l) as TerminalExpr<Any>
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
