package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.INTERSECTION
import de.peekandpoke.karango.aql.TerminalExpr
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-INTERSECTION-Spec` : StringSpec({

    val cases = listOf(
        row(
            "INTERSECTION ([], [])",
            INTERSECTION(ARRAY(), ARRAY()),
            listOf()
        ),
        row(
            "INTERSECTION ([1], [])",
            INTERSECTION(ARRAY(1.aql), ARRAY()),
            listOf()
        ),
        row(
            "INTERSECTION ([1, 2], [2, 3])",
            INTERSECTION(ARRAY(1.aql, 2.aql), ARRAY(2.aql, 3.aql)),
            listOf(2L)
        ),
        row(
            "INTERSECTION ([1, 2], ['a', 'b'])",
            INTERSECTION(ARRAY(1.aql, 2.aql), ARRAY("a".aql, "b".aql)),
            listOf()
        ),
        row(
            "INTERSECTION ([1, 2], [1, 'a', 'b'])",
            INTERSECTION(ARRAY(1.aql, 2.aql), ARRAY(1.aql, "a".aql, "b".aql)),
            listOf(1L)
        ),
        row(
            "INTERSECTION ( [ [1, 2] ], [ [1, 2], ['a', 'b']] )",
            INTERSECTION(ARRAY(ARRAY(1.aql, 2.aql)), ARRAY(ARRAY(1.aql, 2.aql), ARRAY("a".aql, "b".aql))),
            listOf(listOf(1L, 2L))
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
