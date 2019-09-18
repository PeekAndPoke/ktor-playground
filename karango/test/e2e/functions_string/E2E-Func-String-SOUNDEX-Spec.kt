package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.SOUNDEX
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-SOUNDEX-Spec` : StringSpec({

    val cases = listOf(
        row(
            "SOUNDEX( \"example\" )",
            SOUNDEX("example".aql),
            "E251"
        ),
        row(
            "SOUNDEX( \"ekzampul\")",
            SOUNDEX("ekzampul".aql),
            "E251"
        ),
        row(
            "SOUNDEX( \"soundex\" )",
            SOUNDEX("soundex".aql),
            "S532"
        ),
        row(
            "SOUNDEX( \"sounteks\" )",
            SOUNDEX("sounteks".aql),
            "S532"
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            repeat(10) {
                val result = driver.query {
                    RETURN(expression)
                }

                withClue(expression, expected) {
                    result.toList() shouldBe listOf(expected)
                }
            }
        }

        "$description - return from LET" {

            val result = driver.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
