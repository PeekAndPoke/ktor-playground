package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.CONCAT
import de.peekandpoke.karango.aql.CONTAINS
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-CONTAINS-Spec` : StringSpec({

    val cases = listOf(
        row(
            "infix CONTAINS matching an input value",
            "abc".aql() CONTAINS "b".aql(),
            true
        ),
        row(
            "prefix CONTAINS matching an input value",
            CONTAINS("abc".aql(), "b".aql()),
            true
        ),
        row(
            "infix CONTAINS not matching an input value",
            "abc".aql() CONTAINS "X".aql(),
            false
        ),
        row(
            "prefix CONTAINS not matching on input value expression",
            CONTAINS("abc".aql(), "X".aql()),
            false
        ),
        row(
            "infix CONTAINS matching two expressions",
            CONCAT("abc".aql(), "def".aql()) CONTAINS CONCAT("c".aql(), "d".aql()),
            true
        ),
        row(
            "infix CONTAINS not matching two expressions",
            CONCAT("abc".aql(), "def".aql()) CONTAINS CONCAT("X".aql(), "Y".aql()),
            false
        ),
        row(
            "prefix CONTAINS matching two expressions",
            CONTAINS(CONCAT("abc".aql(), "def".aql()), CONCAT("c".aql(), "d".aql())),
            true
        ),
        row(
            "prefix CONTAINS not matching two expression",
            CONTAINS(CONCAT("abc".aql(), "def".aql()), CONCAT("X".aql(), "Y".aql())),
            false
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
