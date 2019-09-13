package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-REMOVE_VALUESS-Spec` : StringSpec({

    val cases = listOf(
        row(
            "REMOVE_VALUES ([], [])",
            REMOVE_VALUES(ARRAY(), ARRAY()),
            listOf()
        ),
        row(
            "REMOVE_VALUES ([1], [])",
            REMOVE_VALUES(ARRAY(1.aql), ARRAY()),
            listOf(1)
        ),
        row(
            "REMOVE_VALUES ([1], [1])",
            REMOVE_VALUES(ARRAY(1.aql), ARRAY(1.aql)),
            listOf()
        ),
        row(
            "REMOVE_VALUES ([1, 1, 2, 2, 3, 3, 4, 5], [1, 3, 4])",
            REMOVE_VALUES(ARRAY(1.aql, 1.aql, 2.aql, 2.aql, 3.aql, 4.aql, 5.aql), ARRAY(1.aql, 3.aql, 5.aql)),
            listOf(2, 2, 4)
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
