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
class `E2E-Func-Numeric-MEDIAN-Spec` : StringSpec({

    "MEDIAN from multiple LETs" {

        val result = driver.query {
            val a = LET("a", 10.aql)
            val b = LET("b", 20.aql)

            RETURN(
                MEDIAN(
                    ARRAY(a, b, 30.aql)
                )
            )
        }

        result.first() shouldBe 20L
    }

    "MEDIAN from multiple objects" {

        val result = driver.query {
            val persons = LET("persons") {
                listOf(
                    E2ePerson("a", 10),
                    E2ePerson("b", 20),
                    E2ePerson("c", 30),
                    E2ePerson("d", 100)
                )
            }

            RETURN(
                MEDIAN(
                    FOR(persons) { p ->
                        FILTER(p.age GT 10)
                        RETURN(p.age)
                    }
                )
            )
        }

        result.first() shouldBe 30.0
    }


    val cases = listOf(
        row(
            "MEDIAN( [1, 2, 3] ) - listOf",
            MEDIAN(listOf(1, 2, 3).aql),
            2.0
        ),
        row(
            "MEDIAN( [1, 2, 3] ) - ARRAY",
            MEDIAN(ARRAY(1.aql, 2.aql, 3.aql)),
            2.0
        ),
        row(
            "MEDIAN( [ 1, 2, 3, 4 ] )",
            MEDIAN(listOf(1, 2, 3, 4).aql),
            2.5
        ),
        row(
            "MEDIAN( [ 4, 2, 3, 1 ] )",
            MEDIAN(listOf(4, 2, 3, 1).aql),
            2.5
        ),
        row(
            "MEDIAN( [ 999, 80, 4, 4, 4, 3, 3, 3 ] )",
            MEDIAN(listOf(999, 80, 4, 4, 4, 3, 3, 3).aql),
            4.0
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
