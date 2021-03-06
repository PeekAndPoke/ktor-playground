package de.peekandpoke.karango.e2e.type_checks

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.E2ePerson
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeCheck-IS_LIST-Spec` : StringSpec({

    val cases = listOf(
        row(
            "IS_LIST(true)",
            IS_LIST(true.aql),
            false
        ),
        row(
            "IS_LIST(false)",
            IS_LIST(true.aql),
            false
        ),
        row(
            "IS_LIST(null)",
            IS_LIST(null.aql),
            false
        ),
        row(
            "IS_LIST(0)",
            IS_LIST(0.aql),
            false
        ),
        row(
            "IS_LIST(1)",
            IS_LIST(1.aql),
            false
        ),
        row(
            "IS_LIST(\"a\")",
            IS_LIST("a".aql),
            false
        ),
        row(
            "IS_LIST(\"\")",
            IS_LIST("".aql),
            false
        ),
        row(
            "IS_LIST([0]) - ARRAY",
            IS_LIST(ARRAY(0.aql)),
            true
        ),
        row(
            "IS_LIST([0]) - listOf",
            IS_LIST(listOf(0).aql),
            true
        ),
        row(
            "IS_LIST(object)",
            IS_LIST(E2ePerson("name", 10).aql),
            false
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = driver.query {
                RETURN(expression)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
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
