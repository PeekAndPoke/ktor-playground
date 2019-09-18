package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-FIRST-Spec` : StringSpec({

    val cases = listOf(
        row(
            "FIRST ([])",
            FIRST(ARRAY<Any>()),
            null
        ),
        row(
            "IS_NULL( FIRST ([]) )",
            IS_NULL(FIRST(ARRAY<Any>())),
            true
        ),
        row(
            "FIRST  (['a'])",
            FIRST(ARRAY("a".aql)),
            "a"
        ),
        row(
            "FIRST  (['a', 'b'])",
            FIRST(ARRAY("a".aql, "b".aql)),
            "a"
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
