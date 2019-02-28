package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.JSON_STRINGIFY
import de.peekandpoke.karango.aql.aql
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
open class `E2E-Func-String-JSON_STRINGIFY-Spec` : StringSpec({

    val cases = listOf(
        row(
            "JSON_STRINGIFY of null",
            JSON_STRINGIFY(null.aql),
            "null"
        ),
        row(
            "JSON_STRINGIFY of integer",
            JSON_STRINGIFY(1.aql),
            "1"
        ),
        row(
            "JSON_STRINGIFY of string",
            JSON_STRINGIFY("string".aql),
            "\"string\""
        ),
        row(
            "JSON_STRINGIFY of array",
            JSON_STRINGIFY(listOf("a", 1).aql),
            """["a",1]"""
        ),
        row(
            "JSON_STRINGIFY of object",
            JSON_STRINGIFY(X("a", 1).aql),
            """{"age":1,"name":"a"}"""
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = db.query {
                RETURN(expression)
            }

            withClue("|| $expression || $expected ||") {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue("|| $expression || $expected ||") {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})