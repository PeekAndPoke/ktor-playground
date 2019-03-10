package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.FLATTEN
import de.peekandpoke.karango.aql.TerminalExpr
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-FLATTEN-Spec` : StringSpec({

    val cases = listOf(
        row(
            "FLATTEN ([])",
            FLATTEN(ARRAY<Any>()),
            listOf()
        ),
        row(
            "FLATTEN ([1])",
            FLATTEN(ARRAY(1.aql)),
            listOf(1L)
        ),
        row(
            "FLATTEN ([1], 0)",
            FLATTEN(ARRAY(1.aql), 0.aql),
            listOf(1L)
        ),
        row(
            "FLATTEN ([1], 1)",
            FLATTEN(ARRAY(1.aql), 1.aql),
            listOf(1L)
        ),
        row(
            "FLATTEN ([1], 2)",
            FLATTEN(ARRAY(1.aql), 2.aql),
            listOf(1L)
        ),
        row(
            "FLATTEN ([[1, 2]], -1)",
            FLATTEN(ARRAY(ARRAY(1.aql, 2.aql)), (-1).aql),
            listOf(1L, 2L)
        ),
        row(
            "FLATTEN ([[1, 2]], 0)",
            FLATTEN(ARRAY(ARRAY(1.aql, 2.aql)), 0.aql),
            listOf(1L, 2L)
        ),
        row(
            "FLATTEN ([[1]], 1)",
            FLATTEN(ARRAY(ARRAY(1.aql, 2.aql)), 1.aql),
            listOf(1L, 2L)
        ),
        row(
            "FLATTEN ([[1]], 2)",
            FLATTEN(ARRAY(ARRAY(1.aql, 2.aql)), 2.aql),
            listOf(1L, 2L)
        ),
        row(
            "FLATTEN ( [ [1, 2], 3 ])",
            FLATTEN(
                ARRAY(ARRAY(1.aql, 2.aql), 3.aql)
            ),
            listOf(1L, 2L, 3L)
        ),
        row(
            "FLATTEN ( [ [1, 2], 3, [ [4, 5], 6] ] )",
            FLATTEN(
                ARRAY(
                    ARRAY(1.aql, 2.aql),
                    3.aql,
                    ARRAY(
                        ARRAY(4.aql, 5.aql),
                        6.aql
                    )
                )
            ),
            listOf(1L, 2L, 3L, listOf(4L, 5L), 6L)
        ),
        row(
            "FLATTEN ( [ [1, 2], 3, [ [4, 5], 6] ], 2 )",
            FLATTEN(
                ARRAY(
                    ARRAY(1.aql, 2.aql),
                    3.aql,
                    ARRAY(
                        ARRAY(4.aql, 5.aql),
                        6.aql
                    )
                ),
                2.aql
            ),
            listOf(1L, 2L, 3L, 4L, 5L, 6L)
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
