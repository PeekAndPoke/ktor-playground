package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.TRIM
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-TRIM-Spec` : StringSpec({

    val cases = listOf(
        row(
            "TRIM of \"foo bar\"",
            TRIM("foo bar".aql),
            "foo bar"
        ),
        row(
            "TRIM of \"  foo bar  \"",
            TRIM("  foo bar  ".aql),
            "foo bar"
        ),
        row(
            "TRIM of \"--==[foo-bar]==--\" with chars \"-=[]\"",
            TRIM("--==[foo-bar]==--".aql, "-=[]".aql),
            "foo-bar"
        ),
        row(
            "TRIM of \"  foobar\\t \\r\\n \"",
            TRIM("  foobar\t \r\n ".aql),
            "foobar"
        ),
        row(
            "TRIM of \";foo;bar;baz, \" with chars \",; \"",
            TRIM(";foo;bar;baz, ".aql, ",; ".aql),
            "foo;bar;baz"
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
