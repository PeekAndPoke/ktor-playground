package de.peekandpoke.karango.e2e.type_checks

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.E2ePerson
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeCheck-IS_DATESTRING-Spec` : StringSpec({

    val cases = listOf(
        row(
            "IS_DATESTRING('2019')",
            IS_DATESTRING("2019".aql),
            true
        ),
        row(
            "IS_DATESTRING('2019-03')",
            IS_DATESTRING("2019-03".aql),
            true
        ),
        row(
            "IS_DATESTRING('2019-03-01')",
            IS_DATESTRING("2019-03-01".aql),
            true
        ),
        row(
            "IS_DATESTRING('2019-03-01 00:00')",
            IS_DATESTRING("2019-03-01 00:00".aql),
            true
        ),
        row(
            "IS_DATESTRING('2019-03-01 23:59:59')",
            IS_DATESTRING("2019-03-01 23:59:59".aql),
            true
        ),
        row(
            "IS_DATESTRING('2019-03-01T23:59:59')",
            IS_DATESTRING("2019-03-01T23:59:59".aql),
            true
        ),
        row(
            "IS_DATESTRING('2019-03-01T23:59:59.000')",
            IS_DATESTRING("2019-03-01T23:59:59.000".aql),
            true
        ),
        row(
            "IS_DATESTRING('2019-03-01T23:59:59.000Z')",
            IS_DATESTRING("2019-03-01T23:59:59.000Z".aql),
            true
        ),
        row(
            "IS_DATESTRING('2019-03-01T23:59:59+01:00')",
            IS_DATESTRING("2019-03-01T23:59:59+01:00".aql),
            true
        ),
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        row(
            "IS_DATESTRING('now')",
            IS_DATESTRING("NOW".aql),
            false
        ),
        row(
            "IS_DATESTRING('tomorrow')",
            IS_DATESTRING("tomorrow".aql),
            false
        ),
        row(
            "IS_DATESTRING(true)",
            IS_DATESTRING(true.aql),
            false
        ),
        row(
            "IS_DATESTRING(false)",
            IS_DATESTRING(true.aql),
            false
        ),
        row(
            "IS_DATESTRING(null)",
            IS_DATESTRING(null.aql),
            false
        ),
        row(
            "IS_DATESTRING(0)",
            IS_DATESTRING(0.aql),
            false
        ),
        row(
            "IS_DATESTRING(1)",
            IS_DATESTRING(1.aql),
            false
        ),
        row(
            "IS_DATESTRING(\"a\")",
            IS_DATESTRING("a".aql),
            false
        ),
        row(
            "IS_DATESTRING(\"\")",
            IS_DATESTRING("".aql),
            false
        ),
        row(
            "IS_DATESTRING([0]) - ARRAY",
            IS_DATESTRING(ARRAY(0.aql)),
            false
        ),
        row(
            "IS_DATESTRING([0]) - listOf",
            IS_DATESTRING(listOf(0).aql),
            false
        ),
        row(
            "IS_DATESTRING([object])",
            IS_DATESTRING(ARRAY(E2ePerson("name", 10).aql)),
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
