package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.LET
import de.peekandpoke.karango.aql.LTRIM
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-LTRIM-Spec` : StringSpec({

    val cases = listOf(
        row(
            "LTRIM of \"foo bar\"",
            LTRIM("foo bar".aql),
            "foo bar"
        ),
        row(
            "LTRIM of \"  foo bar  \"",
            LTRIM("  foo bar  ".aql),
            "foo bar  "
        ),
        row(
            "LTRIM of \"--==[foo-bar]==--\" with chars \"-=[]\"",
            LTRIM("--==[foo-bar]==--".aql, "-=[]".aql),
            "foo-bar]==--"
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
