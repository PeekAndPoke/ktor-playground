package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-PUSH-Spec` : StringSpec({

    val cases = listOf(
        row(
            "PUSH ([], 0)",
            PUSH(ARRAY(), 1.aql),
            listOf(1)
        ),
        row(
            "PUSH ([1], 'a')",
            PUSH(ARRAY(1.aql), "a".aql),
            listOf(1L, "a")
        ),
        row(
            "PUSH ([1], 1, true)",
            PUSH(ARRAY(1.aql), 1.aql, true.aql),
            listOf(1)
        ),
        row(
            "PUSH ([1, 1], 2, true)",
            PUSH(ARRAY(1.aql, 1.aql), 2.aql, true.aql),
            listOf(1, 1, 2)
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = driver.query {
                @Suppress("UNCHECKED_CAST")
                RETURN(expression) as TerminalExpr<Any>
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = driver.query {
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
