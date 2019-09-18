package de.peekandpoke.karango.e2e.type_checks

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.E2ePerson
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeCheck-IS_OBJECT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "IS_OBJECT(object) - empty object",
            IS_OBJECT(mapOf<Any, Any>().aql),
            true
        ),
        row(
            "IS_OBJECT(object) - object with properties",
            IS_OBJECT(E2ePerson("name", 10).aql),
            true
        ),
        row(
            "IS_OBJECT(true)",
            IS_OBJECT(true.aql),
            false
        ),
        row(
            "IS_OBJECT(false)",
            IS_OBJECT(true.aql),
            false
        ),
        row(
            "IS_OBJECT(null)",
            IS_OBJECT(null.aql),
            false
        ),
        row(
            "IS_OBJECT(0)",
            IS_OBJECT(0.aql),
            false
        ),
        row(
            "IS_OBJECT(1)",
            IS_OBJECT(1.aql),
            false
        ),
        row(
            "IS_OBJECT(\"a\")",
            IS_OBJECT("a".aql),
            false
        ),
        row(
            "IS_OBJECT(\"\")",
            IS_OBJECT("".aql),
            false
        ),
        row(
            "IS_OBJECT([0]) - ARRAY",
            IS_OBJECT(ARRAY(0.aql)),
            false
        ),
        row(
            "IS_OBJECT([0]) - listOf",
            IS_OBJECT(listOf(0).aql),
            false
        ),
        row(
            "IS_OBJECT([object])",
            IS_OBJECT(ARRAY(E2ePerson("name", 10).aql)),
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
