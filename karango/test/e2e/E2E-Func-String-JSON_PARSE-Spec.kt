package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.JSON_PARSE
import de.peekandpoke.karango.aql.aql
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
open class `E2E-Func-String-JSON_PARSE-Spec` : StringSpec({

    val cases = listOf(
        row(
            "JSON_PARSE of single quoted string -> parse error",
            JSON_PARSE("'string'".aql),
            null
        ),
        row(
            "JSON_PARSE of broken json -> parse error",
            JSON_PARSE("broken".aql),
            null
        ),
        row(
            "JSON_PARSE of 'null'",
            JSON_PARSE("null".aql),
            null
        ),
        row(
            "JSON_PARSE of '1'",
            JSON_PARSE("1".aql),
            1L
        ),
        row(
            "JSON_PARSE of '-1.1'",
            JSON_PARSE("-1.1".aql),
            -1.1
        ),
        row(
            "JSON_PARSE of 'string'",
            JSON_PARSE("\"string\"".aql),
            "string"
        ),
        row(
            "JSON_PARSE of [1, \"a\"]",
            JSON_PARSE("""[1, "a"]""".aql),
            listOf(1L, "a")
        ),
        row(
            "JSON_PARSE of {a:1, b:[1, 2]}",
            JSON_PARSE("""{"a":1, "b":[1, 2]}""".aql),
            mapOf("a" to 1L, "b" to listOf(1L, 2L))
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
