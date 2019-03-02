package de.peekandpoke.karango.e2e.type_checks

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.IS_ARRAY
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.Person
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeCheck-IS_ARRAY-Spec` : StringSpec({

    val cases = listOf(
        row(
            "IS_ARRAY(true)",
            IS_ARRAY(true.aql),
            false
        ),
        row(
            "IS_ARRAY(false)",
            IS_ARRAY(true.aql),
            false
        ),
        row(
            "IS_ARRAY(null)",
            IS_ARRAY(null.aql),
            false
        ),
        row(
            "IS_ARRAY(0)",
            IS_ARRAY(0.aql),
            false
        ),
        row(
            "IS_ARRAY(1)",
            IS_ARRAY(1.aql),
            false
        ),
        row(
            "IS_ARRAY(\"a\")",
            IS_ARRAY("a".aql),
            false
        ),
        row(
            "IS_ARRAY(\"\")",
            IS_ARRAY("".aql),
            false
        ),
        row(
            "IS_ARRAY([0]) - ARRAY",
            IS_ARRAY(ARRAY(0.aql)),
            true
        ),
        row(
            "IS_ARRAY([0]) - listOf",
            IS_ARRAY(listOf(0).aql),
            true
        ),
        row(
            "IS_ARRAY(object)",
            IS_ARRAY(Person("name", 10).aql),
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
