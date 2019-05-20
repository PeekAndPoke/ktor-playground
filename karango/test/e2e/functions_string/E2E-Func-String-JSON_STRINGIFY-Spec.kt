package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.JSON_STRINGIFY
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.E2ePerson
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
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
            JSON_STRINGIFY(E2ePerson("a", 1).aql),
            """{"name":"a","age":1}"""
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
