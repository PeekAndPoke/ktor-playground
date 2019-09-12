package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.OUTERSECTION
import de.peekandpoke.karango.aql.TerminalExpr
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-OUTERSECTION-Spec` : StringSpec({

    val cases = listOf(
        row(
            "OUTERSECTION ([ 1, 2, 3 ], [ 2, 3, 4 ], [ 3, 4, 5 ])",
            OUTERSECTION(listOf(1, 2, 3).aql, listOf(2, 3, 4).aql, listOf(3, 4, 5).aql),
            listOf(5, 1)
        ),
        row(
            "OUTERSECTION ([], [])",
            OUTERSECTION(ARRAY(), ARRAY()),
            listOf()
        ),
        row(
            "OUTERSECTION ([1], [])",
            OUTERSECTION(ARRAY(1.aql), ARRAY()),
            listOf(1)
        ),
        row(
            "OUTERSECTION ([1, 2], [2, 3])",
            OUTERSECTION(ARRAY(1.aql, 2.aql), ARRAY(2.aql, 3.aql)),
            listOf(3, 1)
        ),
        row(
            "OUTERSECTION ([1, 2], ['a', 'b'])",
            OUTERSECTION(ARRAY(1.aql, 2.aql), ARRAY("a".aql, "b".aql)),
            listOf("b", "a", 2L, 1L)
        ),
        row(
            "OUTERSECTION ([1, 2, 'a'], [1, 'a', 'b'])",
            OUTERSECTION(ARRAY(1.aql, 2.aql, "a".aql), ARRAY(1.aql, "a".aql, "b".aql)),
            listOf("b", 2L)
        ),
        row(
            "OUTERSECTION ( [ [1, 2] ], [ [1, 2], ['a', 'b']] )",
            OUTERSECTION(ARRAY(ARRAY(1.aql, 2.aql)), ARRAY(ARRAY(1.aql, 2.aql), ARRAY("a".aql, "b".aql))),
            listOf(listOf("a", "b"))
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
