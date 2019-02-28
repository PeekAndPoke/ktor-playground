package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.LOWER
import de.peekandpoke.karango.aql.aql
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-LOWER-Spec` : StringSpec({

    val cases = listOf(
        row(
            "LOWER of string",
            LOWER("abc-ABC-123-ÄÖÜ-SS".aql),
            "abc-abc-123-äöü-ss"
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
