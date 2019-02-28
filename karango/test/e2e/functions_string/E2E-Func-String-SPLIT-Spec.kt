package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.SPLIT
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-SPLIT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "SPLIT( \"foo-bar-baz\", \"-\" )",
            SPLIT("foo-bar-baz".aql, "-".aql),
            listOf("foo", "bar", "baz")
        ),
        row(
            "SPLIT( \"foo-bar-baz\", \"-\", 1 )",
            SPLIT("foo-bar-baz".aql, "-".aql, 2.aql()),
            listOf("foo", "bar")
        ),
        row(
            "SPLIT( \"foo, bar & baz\", [ \", \", \" & \" ] )",
            SPLIT("foo, bar & baz".aql, listOf(", ", " & ").aql),
            listOf("foo", "bar", "baz")
        ),
        row(
            "SPLIT( \"foo, bar & baz & buzz\", [ \", \", \" & \" ], 1 )",
            SPLIT("foo, bar & baz & buzz".aql, listOf(", ", " & ").aql, 3.aql),
            listOf("foo", "bar", "baz")
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
