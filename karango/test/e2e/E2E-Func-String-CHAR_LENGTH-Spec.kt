package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.CHAR_LENGTH
import de.peekandpoke.karango.aql.aql
import io.kotlintest.assertSoftly
import io.kotlintest.data.forall
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-CHAR_LENGTH-Spec` : StringSpec({

    val cases = arrayOf(
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

    forall(*cases) { description, expression, expected ->

        withClue(description) {

            val result = db.query {
                RETURN(expression)
            }

            val result2 = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            assertSoftly {
                result.toList() shouldBe listOf(expected)
                result2.toList() shouldBe listOf(expected)
            }
        }
    }
})
