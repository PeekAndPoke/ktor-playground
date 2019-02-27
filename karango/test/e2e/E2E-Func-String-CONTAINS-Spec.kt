package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.CONCAT
import de.peekandpoke.karango.aql.CONTAINS
import de.peekandpoke.karango.aql.aql
import io.kotlintest.assertSoftly
import io.kotlintest.data.forall
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-CONTAINS-Spec` : StringSpec({

    val cases = arrayOf(
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

    forall(*cases) { description, expression, expected ->

        withClue(description) {

            val result = db.query {
                RETURN(expression)
            }

            val result2 = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }
            
            assertSoftly {
                result.toList() shouldBe listOf(expected)
                result2.toList() shouldBe listOf(expected)
            }
        }
    }
})
