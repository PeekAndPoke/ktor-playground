package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-UNSHIFT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "UNSHIFT ([], 0)",
            UNSHIFT(ARRAY(), 1.aql),
            listOf(1)
        ),
        row(
            "UNSHIFT ([1], 'a')",
            UNSHIFT(ARRAY(1.aql), "a".aql),
            listOf("a", 1L)
        ),
        row(
            "UNSHIFT ([1], 1, true)",
            UNSHIFT(ARRAY(1.aql), 1.aql, true.aql),
            listOf(1)
        ),
        row(
            "UNSHIFT ([1], 2, true)",
            UNSHIFT(ARRAY(1.aql), 2.aql, true.aql),
            listOf(2, 1)
        ),
        row(
            "UNSHIFT ([1, 1], 2, true)",
            UNSHIFT(ARRAY(1.aql, 1.aql), 2.aql, true.aql),
            listOf(2, 1, 1)
        ),
        row(
            "UNSHIFT ([1, 1, 2], 2, true)",
            UNSHIFT(ARRAY(1.aql, 1.aql, 2.aql), 2.aql, true.aql),
            listOf(1, 1, 2)
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
