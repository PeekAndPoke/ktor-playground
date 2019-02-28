package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.SUBSTRING
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-SUBSTRING-Spec` : StringSpec({

    val cases = listOf(
        row(
            "SUBSTRING(\"abc\", 0)",
            SUBSTRING("abc".aql, 0.aql),
            "abc"
        ),
        row(
            "SUBSTRING(\"abc\", 1)",
            SUBSTRING("abc".aql, 1.aql),
            "bc"
        ),
        row(
            "SUBSTRING(\"abc\", -1)",
            SUBSTRING("abc".aql, (-1).aql),
            "c"
        ),
        row(
            "SUBSTRING(\"abc\", -2, 1)",
            SUBSTRING("abc".aql, (-2).aql, 1.aql),
            "b"
        ),
        row(
            "SUBSTRING(\"abc\", 1, 0)",
            SUBSTRING("abc".aql, 1.aql, 0.aql),
            ""
        ),
        row(
            "SUBSTRING(\"abc\", 1, 1)",
            SUBSTRING("abc".aql, 1.aql, 1.aql),
            "b"
        ),
        row(
            "SUBSTRING(\"abc\", 1, 10)",
            SUBSTRING("abc".aql, 1.aql, 10.aql),
            "bc"
        ),
        row(
            "SUBSTRING(\"abc\", 1, -1)",
            SUBSTRING("abc".aql, 1.aql, (-1).aql),
            ""
        ),
        row(
            "SUBSTRING(\"abc\", -5, 2)",
            SUBSTRING("abc".aql, (-5).aql, 2.aql),
            "ab"
        ),
        row(
            "SUBSTRING(\"abc\", -5, 3)",
            SUBSTRING("abc".aql, (-5).aql, 3.aql),
            "abc"
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
