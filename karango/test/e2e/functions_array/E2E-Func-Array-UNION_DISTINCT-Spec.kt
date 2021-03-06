package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-UNION_DISTINCT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "UNION_DISTINCT ([], [])",
            UNION_DISTINCT(ARRAY(), ARRAY()),
            listOf()
        ),
        row(
            "UNION_DISTINCT ([1], [])",
            UNION_DISTINCT(ARRAY(1.aql), ARRAY()),
            listOf(1)
        ),
        row(
            "UNION_DISTINCT ([1, 2], [2, 3])",
            UNION_DISTINCT(ARRAY(1.aql, 2.aql), ARRAY(2.aql, 3.aql)),
            listOf(3, 2, 1)
        ),
        row(
            "UNION_DISTINCT ([1, 2], [2, 3], [3, 4])",
            UNION_DISTINCT(ARRAY(1.aql, 2.aql), ARRAY(2.aql, 3.aql), ARRAY(3.aql, 4.aql)),
            listOf(4, 3, 2, 1)
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
