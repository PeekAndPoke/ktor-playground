package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.E2ePerson
import de.peekandpoke.karango.e2e.age
import de.peekandpoke.karango.e2e.driver
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-AVG-Spec` : StringSpec({

    "AVG from multiple LETs" {

        val result = driver.query {
            val a = LET("a", 10.aql)
            val b = LET("b", 20.aql)

            RETURN(
                AVG(
                    ARRAY(a, b, 30.aql)
                )
            )
        }

        result.first() shouldBe 20L
    }

    "AVG from multiple objects" {

        val result = driver.query {
            val persons = LET("persons") {
                listOf(
                    E2ePerson("a", 10),
                    E2ePerson("b", 20),
                    E2ePerson("c", 30)
                )
            }

            RETURN(
                AVG(
                    FOR(persons) { p ->
                        FILTER(p.age GT 10)
                        RETURN(p.age)
                    }
                )
            )
        }

        result.first() shouldBe 25.0
    }

    val cases = listOf(
        row(
            "AVG( [5, 2, 9, 2] )",
            AVG(listOf(5, 2, 9, 2).aql),
            4.5
        ),
        row(
            "AVG( [ -3, -5, 2 ] )",
            AVG(listOf(-3, -5, 2).aql),
            -2.0
        ),
        row(
            "AVG( [ 999, 80, 4, 4, 4, 3, 3, 3 ] )",
            AVG(listOf(999, 80, 4, 4, 4, 3, 3, 3).aql),
            137.5
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
