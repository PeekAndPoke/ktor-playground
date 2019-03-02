package de.peekandpoke.karango.e2e.type_checks

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.IS_STRING
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.Person
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeCheck-IS_STRING-Spec` : StringSpec({

    val cases = listOf(
        row(
            "IS_STRING(true)",
            IS_STRING(true.aql),
            false
        ),
        row(
            "IS_STRING(false)",
            IS_STRING(true.aql),
            false
        ),
        row(
            "IS_STRING(null)",
            IS_STRING(null.aql),
            false
        ),
        row(
            "IS_STRING(0)",
            IS_STRING(0.aql),
            false
        ),
        row(
            "IS_STRING(1)",
            IS_STRING(1.aql),
            false
        ),
        row(
            "IS_STRING(\"a\")",
            IS_STRING("a".aql),
            true
        ),
        row(
            "IS_STRING(\"\")",
            IS_STRING("".aql),
            true
        ),
        row(
            "IS_STRING([0])",
            IS_STRING(ARRAY(0.aql)),
            false
        ),
        row(
            "IS_STRING(object)",
            IS_STRING(Person("name", 10).aql),
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
