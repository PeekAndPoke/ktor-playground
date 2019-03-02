package de.peekandpoke.karango.e2e.type_checks

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.IS_BOOL
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.Person
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeCheck-IS_BOOL-Spec` : StringSpec({

    val cases = listOf(
        row(
            "IS_BOOL(true)",
            IS_BOOL(true.aql),
            true
        ),
        row(
            "IS_BOOL(false)",
            IS_BOOL(true.aql),
            true
        ),
        row(
            "IS_BOOL(null)",
            IS_BOOL(null.aql),
            false
        ),
        row(
            "IS_BOOL(0)",
            IS_BOOL(0.aql),
            false
        ),
        row(
            "IS_BOOL(1)",
            IS_BOOL(1.aql),
            false
        ),
        row(
            "IS_BOOL(\"a\")",
            IS_BOOL("a".aql),
            false
        ),
        row(
            "IS_BOOL(\"\")",
            IS_BOOL("".aql),
            false
        ),
        row(
            "IS_BOOL([0])",
            IS_BOOL(ARRAY(0.aql)),
            false
        ),
        row(
            "IS_BOOL(object)",
            IS_BOOL(Person("name", 10).aql),
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
