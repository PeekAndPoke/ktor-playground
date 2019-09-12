package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.CONTAINS_ARRAY
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-CONTAINS_ARRAY-Spec` : StringSpec({

    val cases = listOf(
        row(
            "CONTAINS_ARRAY ([], 0)",
            CONTAINS_ARRAY(ARRAY(), 0.aql),
            false
        ),
        row(
            "CONTAINS_ARRAY ([1], 1)",
            CONTAINS_ARRAY(ARRAY(1.aql), 1.aql),
            true
        ),
        row(
            "CONTAINS_ARRAY ([1, 2, 3], 3)",
            CONTAINS_ARRAY(ARRAY(1.aql, 2.aql, 3.aql), 3.aql),
            true
        ),
        row(
            "CONTAINS_ARRAY ([1, 2, 3], 4)",
            CONTAINS_ARRAY(ARRAY(1.aql, 2.aql, 3.aql), 4.aql),
            false
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
