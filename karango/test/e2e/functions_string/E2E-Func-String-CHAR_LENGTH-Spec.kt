package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.CHAR_LENGTH
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-CHAR_LENGTH-Spec` : StringSpec({

    val cases = listOf(
        row(
            "CHAR_LENGTH on an empty string parameter",
            CHAR_LENGTH("".aql),
            0L
        ),
        row(
            "CHAR_LENGTH on a simple string parameter",
            CHAR_LENGTH("1".aql),
            1L
        ),
        row(
            "CHAR_LENGTH on another simple string parameter",
            CHAR_LENGTH("12".aql),
            2L
        ),
        row(
            "CHAR_LENGTH on a string with UTF-8 characters",
            CHAR_LENGTH("äöüß".aql),
            4L
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
