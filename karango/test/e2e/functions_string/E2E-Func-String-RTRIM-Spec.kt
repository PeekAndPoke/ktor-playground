package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.RTRIM
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-RTRIM-Spec` : StringSpec({

    val cases = listOf(
        row(
            "RTRIM of \"foo bar\"",
            RTRIM("foo bar".aql),
            "foo bar"
        ),
        row(
            "RTRIM of \"  foo bar  \"",
            RTRIM("  foo bar  ".aql),
            "  foo bar"
        ),
        row(
            "RTRIM of \"--==[foo-bar]==--\" with chars \"-=[]\"",
            RTRIM("--==[foo-bar]==--".aql, "-=[]".aql),
            "--==[foo-bar"
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
