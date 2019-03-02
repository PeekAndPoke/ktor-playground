package de.peekandpoke.karango.e2e.type_checks

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.IS_DOCUMENT
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.Person
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeCheck-IS_DOCUMENT-Spec` : StringSpec({

    val cases = listOf(
        row(
            "IS_DOCUMENT(object) - empty objects seem to be documents",
            IS_DOCUMENT(mapOf<Any, Any>().aql),
            true
        ),
        row(
            "IS_DOCUMENT(object) - objects seem to be documents",
            IS_DOCUMENT(Person("name", 10).aql),
            true
        ),
        row(
            "IS_DOCUMENT(true)",
            IS_DOCUMENT(true.aql),
            false
        ),
        row(
            "IS_DOCUMENT(false)",
            IS_DOCUMENT(true.aql),
            false
        ),
        row(
            "IS_DOCUMENT(null)",
            IS_DOCUMENT(null.aql),
            false
        ),
        row(
            "IS_DOCUMENT(0)",
            IS_DOCUMENT(0.aql),
            false
        ),
        row(
            "IS_DOCUMENT(1)",
            IS_DOCUMENT(1.aql),
            false
        ),
        row(
            "IS_DOCUMENT(\"a\")",
            IS_DOCUMENT("a".aql),
            false
        ),
        row(
            "IS_DOCUMENT(\"\")",
            IS_DOCUMENT("".aql),
            false
        ),
        row(
            "IS_DOCUMENT([0]) - ARRAY",
            IS_DOCUMENT(ARRAY(0.aql)),
            false
        ),
        row(
            "IS_DOCUMENT([0]) - listOf",
            IS_DOCUMENT(listOf(0).aql),
            false
        ),
        row(
            "IS_DOCUMENT([object])",
            IS_DOCUMENT(ARRAY(Person("name", 10).aql)),
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
