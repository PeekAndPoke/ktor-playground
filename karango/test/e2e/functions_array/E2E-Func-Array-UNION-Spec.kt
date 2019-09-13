package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-UNION-Spec` : StringSpec({

    val cases = listOf(
        row(
            "UNION ([], [])",
            UNION(ARRAY(), ARRAY()),
            listOf()
        ),
        row(
            "UNION ([1], [])",
            UNION(ARRAY(1.aql), ARRAY()),
            listOf(1)
        ),
        row(
            "UNION ([1, 2], [2, 3])",
            UNION(ARRAY(1.aql, 2.aql), ARRAY(2.aql, 3.aql)),
            listOf(1, 2, 2, 3)
        ),
        row(
            "UNION ([1, 2], ['a', 'b'])",
            UNION(ARRAY(1.aql, 2.aql), ARRAY("a".aql, "b".aql)),
            listOf(1L, 2L, "a", "b")
        ),
        row(
            "UNION ([1, 2], ['a', 'b'], ['c'])",
            UNION(ARRAY(1.aql, 2.aql), ARRAY("a".aql, "b".aql), ARRAY("c".aql)),
            listOf(1L, 2L, "a", "b", "c")
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
