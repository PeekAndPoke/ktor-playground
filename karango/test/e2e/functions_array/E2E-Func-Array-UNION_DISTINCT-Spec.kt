package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.TerminalExpr
import de.peekandpoke.karango.aql.UNION_DISTINCT
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
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
            listOf(1L)
        ),
        row(
            "UNION_DISTINCT ([1, 2], [2, 3])",
            UNION_DISTINCT(ARRAY(1.aql, 2.aql), ARRAY(2.aql, 3.aql)),
            listOf(3L, 2L, 1L)
        ),
        row(
            "UNION_DISTINCT ([1, 2], [2, 3], [3, 4])",
            UNION_DISTINCT(ARRAY(1.aql, 2.aql), ARRAY(2.aql, 3.aql), ARRAY(3.aql, 4.aql)),
            listOf(4L, 3L, 2L, 1L)
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
