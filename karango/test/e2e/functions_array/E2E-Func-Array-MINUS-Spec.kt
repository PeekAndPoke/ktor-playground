package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.MINUS
import de.peekandpoke.karango.aql.TerminalExpr
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-MINUS-Spec` : StringSpec({

    val cases = listOf(
        row(
            "MINUS ([1,2,3,4], [3,4,5,6], [5,6,7,8])",
            MINUS(listOf(1, 2, 3, 4).aql, listOf(3, 4, 5, 6).aql, listOf(5, 6, 7, 8).aql),
            listOf(2, 1)
        ),
        row(
            "MINUS ([], [])",
            MINUS(ARRAY(), ARRAY()),
            listOf()
        ),
        row(
            "MINUS ([1], [])",
            MINUS(ARRAY(1.aql), ARRAY()),
            listOf(1L)
        ),
        row(
            "MINUS ([1, 2], [2, 3])",
            MINUS(ARRAY(1.aql, 2.aql), ARRAY(2.aql, 3.aql)),
            listOf(1L)
        ),
        row(
            "MINUS ([1, 2], ['a', 'b'])",
            MINUS(ARRAY(1.aql, 2.aql), ARRAY("a".aql, "b".aql)),
            listOf(2L, 1L)
        ),
        row(
            "MINUS ([1, 2], [1, 'a', 'b'])",
            MINUS(ARRAY(1.aql, 2.aql), ARRAY(1.aql, "a".aql, "b".aql)),
            listOf(2L)
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
