package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.REMOVE_NTH
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-REMOVE_NTH-Spec` : StringSpec({

    val cases = listOf(
        row(
            "REMOVE_NTH ([], 0)",
            REMOVE_NTH(ARRAY(), 0.aql),
            listOf()
        ),
        row(
            "REMOVE_NTH ([1], 0)",
            REMOVE_NTH(ARRAY(1.aql), 0.aql),
            listOf()
        ),
        row(
            "REMOVE_NTH ([1], -1)",
            REMOVE_NTH(ARRAY(1.aql), (-1).aql),
            listOf()
        ),
        row(
            "REMOVE_NTH ([1], 1)",
            REMOVE_NTH(ARRAY(1.aql), 1.aql),
            listOf(1L)
        ),
        row(
            "REMOVE_NTH ([1, 2], 0)",
            REMOVE_NTH(ARRAY(1.aql, 2.aql), 0.aql),
            listOf(2L)
        ),
        row(
            "REMOVE_NTH ([1, 2], 1)",
            REMOVE_NTH(ARRAY(1.aql, 2.aql), 1.aql),
            listOf(1L)
        ),
        row(
            "REMOVE_NTH ([1, 2], 2)",
            REMOVE_NTH(ARRAY(1.aql, 2.aql), 2.aql),
            listOf(1L, 2L)
        ),
        row(
            "REMOVE_NTH ([1, 2], -1)",
            REMOVE_NTH(ARRAY(1.aql, 2.aql), (-1).aql),
            listOf(1L)
        ),
        row(
            "REMOVE_NTH ([1, 2], -2)",
            REMOVE_NTH(ARRAY(1.aql, 2.aql), (-2).aql),
            listOf(2L)
        ),
        row(
            "REMOVE_NTH ([1, 2], -3)",
            REMOVE_NTH(ARRAY(1.aql, 2.aql), (-3).aql),
            listOf(1L, 2L)
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = db.query {
                RETURN(expression)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})