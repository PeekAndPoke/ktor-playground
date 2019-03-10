package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.POSITION_IDX
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-POSITION_IDX-Spec` : StringSpec({

    val cases = listOf(
        row(
            "POSITION_IDX ([], 0)",
            POSITION_IDX(ARRAY(), 0.aql),
            -1L
        ),
        row(
            "POSITION_IDX ([1], 1)",
            POSITION_IDX(ARRAY(1.aql), 1.aql),
            0L
        ),
        row(
            "POSITION_IDX ([1, 2, 3], 3)",
            POSITION_IDX(ARRAY(1.aql, 2.aql, 3.aql), 3.aql),
            2L
        ),
        row(
            "POSITION_IDX ([1, 2, 3], 4)",
            POSITION_IDX(ARRAY(1.aql, 2.aql, 3.aql), 4.aql),
            -1L
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
