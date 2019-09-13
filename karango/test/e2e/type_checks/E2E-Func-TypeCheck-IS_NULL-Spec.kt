package de.peekandpoke.karango.e2e.type_checks

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.E2ePerson
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeCheck-IS_NULL-Spec` : StringSpec({

    val cases = listOf(
        row(
            "IS_NULL(true)",
            IS_NULL(true.aql),
            false
        ),
        row(
            "IS_NULL(false)",
            IS_NULL(true.aql),
            false
        ),
        row(
            "IS_NULL(null)",
            IS_NULL(null.aql),
            true
        ),
        row(
            "IS_NULL(0)",
            IS_NULL(0.aql),
            false
        ),
        row(
            "IS_NULL(1)",
            IS_NULL(1.aql),
            false
        ),
        row(
            "IS_NULL(\"a\")",
            IS_NULL("a".aql),
            false
        ),
        row(
            "IS_NULL(\"\")",
            IS_NULL("".aql),
            false
        ),
        row(
            "IS_NULL([0])",
            IS_NULL(ARRAY(0.aql)),
            false
        ),
        row(
            "IS_NULL(object)",
            IS_NULL(E2ePerson("name", 10).aql),
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
