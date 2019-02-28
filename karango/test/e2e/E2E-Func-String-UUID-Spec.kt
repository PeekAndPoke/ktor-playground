package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.UUID
import io.kotlintest.matchers.string.shouldMatch
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-UUID-Spec` : StringSpec({

    val cases = listOf(
        row(
            "UPPER of string",
            UUID(),
            "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            repeat(10) {
                val result = db.query {
                    RETURN(expression)
                }

                withClue(expression, expected) {
                    result.first() shouldMatch expected
                }
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue(expression, expected) {
                result.first() shouldMatch expected
            }
        }
    }
})
