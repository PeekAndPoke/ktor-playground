package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.ENCODE_URI_COMPONENT
import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-ENCODE_URI_COMPONENT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "ENCODE_URI_COMPONENT ( \"foo bar\" ))",
            ENCODE_URI_COMPONENT("foo bar".aql),
            "foo%20bar"
        ),
        row(
            "ENCODE_URI_COMPONENT ( \"föö bär\" ))",
            ENCODE_URI_COMPONENT("föö bär".aql),
            "f%C3%B6%C3%B6%20b%C3%A4r"
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = driver.query {
                RETURN(expression)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
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
