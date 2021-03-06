package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-UNIQUE-Spec` : StringSpec({

    val cases = listOf(
        row(
            "UNIQUE ( [] )",
            UNIQUE(ARRAY()),
            listOf()
        ),
        row(
            "UNIQUE ( [ 8, 8, 4, 4, 2, 2, 10, 6 ] )",
            UNIQUE(listOf(8, 8, 4, 4, 2, 2, 10, 6).aql),
            listOf(2, 4, 6, 8, 10)
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = driver.query {
                RETURN(expression)
            }

            withClue(expression, expected) {
                result.first() shouldContainExactlyInAnyOrder expected
            }
        }

        "$description - return from LET" {

            val result = driver.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue(expression, expected) {
                result.first() shouldContainExactlyInAnyOrder expected
            }
        }
    }
})
