package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.CONCAT
import de.peekandpoke.karango.aql.TO_STRING
import de.peekandpoke.karango.aql.aql
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-CONCAT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "CONCAT with one empty string parameter",
            CONCAT("".aql),
            ""
        ),
        row(
            "CONCAT with two empty string parameters",
            CONCAT("".aql, "".aql),
            ""
        ),
        row(
            "CONCAT with multiple parameters",
            CONCAT("a".aql, "".aql, "b".aql),
            "ab"
        ),
        row(
            "CONCAT with more parameters",
            CONCAT("".aql, "a".aql, "_".aql, 123.aql.TO_STRING),
            "a_123"
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
