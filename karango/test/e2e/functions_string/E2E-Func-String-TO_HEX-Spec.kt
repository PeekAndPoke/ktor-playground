package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.TO_HEX
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-TO_HEX-Spec` : StringSpec({

    val cases = listOf(
        row(
            "TO_HEX (\"\")",
            TO_HEX("".aql),
            ""
        ),
        row(
            "TO_HEX (\"ABC\")",
            TO_HEX("ABC".aql),
            "414243"
        ),
        row(
            "TO_HEX (\"abc ?\")",
            TO_HEX("abc ?".aql),
            "616263203f"
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
