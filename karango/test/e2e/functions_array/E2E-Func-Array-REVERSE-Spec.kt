package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-REVERSE-Spec` : StringSpec({

    val cases = listOf(
        row(
            "REVERSE( [] )",
            REVERSE(ARRAY()),
            listOf()
        ),
        row(
            "REVERSE( ['a'] )",
            REVERSE(ARRAY("a".aql)),
            listOf("a")
        ),
        row(
            "REVERSE( ['a', 'b'] ) - ARRAY",
            REVERSE(ARRAY("a".aql, "b".aql)),
            listOf("b", "a")
        ),
        row(
            "REVERSE( ['a', 'b'] ) - listOf",
            REVERSE(listOf("a", "b").aql),
            listOf("b", "a")
        ),
        row(
            "REVERSE( ['a', 'b', 'c'] ) - ARRAY",
            REVERSE(ARRAY("a".aql, "b".aql, "c".aql)),
            listOf("c", "b", "a")
        ),
        row(
            "REVERSE( ['a', 'b', 'c'] ) - listOf",
            REVERSE(listOf("a", "b", "c").aql),
            listOf("c", "b", "a")
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            repeat(10) {
                val result = driver.query {
                    RETURN(expression)
                }

                withClue(expression, expected) {
                    result.toList() shouldBe listOf(expected)
                }
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
