package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.CONCAT_SEPARATOR
import de.peekandpoke.karango.aql.TO_STRING
import de.peekandpoke.karango.aql.aql
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-CONCAT_SEPARATOR-Spec` : StringSpec({

    val cases = listOf(
        row(
            "CONCAT_SEPARATOR with empty separator one empty string parameter",
            CONCAT_SEPARATOR("".aql, "".aql),
            ""
        ),
        row(
            "CONCAT_SEPARATOR with non empty separator one empty string parameter",
            CONCAT_SEPARATOR(", ".aql, "".aql),
            ""
        ),
        row(
            "CONCAT_SEPARATOR with none empty separator and two empty string parameters",
            CONCAT_SEPARATOR(", ".aql, "a".aql, "b".aql),
            "a, b"
        ),
        row(
            "CONCAT_SEPARATOR with empty separator more parameters",
            CONCAT_SEPARATOR("".aql, "a".aql, "_".aql, 123.aql.TO_STRING),
            "a_123"
        ),
        row(
            "CONCAT_SEPARATOR with none empty separator more parameters",
            CONCAT_SEPARATOR(", ".aql, "a".aql, "_".aql, 123.aql.TO_STRING),
            "a, _, 123"
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = db.query {
                RETURN(expression)
            }

            withClue("|| $expression || $expected ||") {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue("|| $expression || $expected ||") {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
