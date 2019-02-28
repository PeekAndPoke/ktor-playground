package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.LEFT
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-LEFT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "LEFT of part of string",
            LEFT("abc".aql, 2.aql),
            "ab"
        ),
        row(
            "LEFT with n greater than length of string",
            LEFT("abc".aql, 10.aql),
            "abc"
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
