package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.REGEX_MATCHES
import de.peekandpoke.karango.aql.aql
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-REGEX_MATCHES-Spec` : StringSpec({

    val cases = listOf(
        row(
            "REGEX_MATCHES(\"My-us3r_n4m3\", \"^[a-z0-9_-]{3,16}\$\", true)",
            REGEX_MATCHES("My-us3r_n4m3".aql, "^[a-z0-9_-]{3,16}$".aql, true.aql),
            listOf("My-us3r_n4m3")
        ),
        row(
            "REGEX_MATCHES(\"#4d82h4\", \"^#?([a-f0-9]{6}|[a-f0-9]{3})\$\", true)",
            REGEX_MATCHES("#4d82h4".aql, "^#?([a-f0-9]{6}|[a-f0-9]{3})$".aql, true.aql),
            null
        ),
        row(
            "REGEX_MATCHES(\"john@doe.com\", \"^([a-z0-9_\\.-]+)@([\\da-z-]+)\\.([a-z\\.]{2,6})\$\")",
            REGEX_MATCHES("john@doe.com".aql, "^([a-z0-9_\\.-]+)@([\\da-z-]+)\\.([a-z\\.]{2,6})$".aql),
            listOf("john@doe.com", "john", "doe", "com")
        ),
        row(
            "REGEX_MATCHES(\"john@doe.com\", \"^([a-z0-9_\\.-]+)@([\\da-z-]+)\\.([a-z\\.]{2,6})\$\", false)",
            REGEX_MATCHES("john@doe.com".aql, "^([a-z0-9_\\.-]+)@([\\da-z-]+)\\.([a-z\\.]{2,6})$".aql, false.aql),
            listOf("john@doe.com", "john", "doe", "com")
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            repeat(10) {
                val result = db.query {
                    RETURN(expression)
                }

                withClue(expression, expected) {
                    result.toList() shouldBe listOf(expected)
                }
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
