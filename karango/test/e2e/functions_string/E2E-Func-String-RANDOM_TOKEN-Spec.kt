package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.RANDOM_TOKEN
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.matchers.string.shouldMatch
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-RANDOM_TOKEN-Spec` : StringSpec({

    val cases = listOf(
        row(
            "RANDOM_TOKEN( 5 )",
            RANDOM_TOKEN(5.aql),
            5
        ),
        row(
            "RANDOM_TOKEN( 100 )",
            RANDOM_TOKEN(100.aql),
            100
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            repeat(10) {
                val result = db.query {
                    RETURN(expression)
                }

                val first = result.first()

                withClue(expression, expected) {
                    first.length shouldBe expected
                    first shouldMatch "[a-zA-Z0-9]{$expected}"
                }
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue(expression, expected) {
                result.first().length shouldBe expected
            }
        }
    }
})
