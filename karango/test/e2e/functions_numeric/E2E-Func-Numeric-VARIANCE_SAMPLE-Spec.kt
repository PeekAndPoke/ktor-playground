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
class `E2E-Func-Numeric-VARIANCE_SAMPLE-Spec` : StringSpec({

    "VARIANCE_SAMPLE from multiple LETs" {

        val result = driver.query {
            val a = LET("a", 10.aql)
            val b = LET("b", 20.aql)

            RETURN(
                VARIANCE_SAMPLE(
                    ARRAY(a, b, 30.aql)
                )
            )
        }

        result.first() shouldBe 100.0
    }

    "VARIANCE_SAMPLE from multiple objects" {

        val result = driver.query {
            val persons = LET("persons") {
                listOf(
                    E2ePerson("a", 10),
                    E2ePerson("b", 20),
                    E2ePerson("c", 30)
                )
            }

            RETURN(
                VARIANCE_SAMPLE(
                    FOR(persons) { p ->
                        FILTER(p.age GT 10)
                        RETURN(p.age)
                    }
                )
            )
        }

        result.first() shouldBe 50.0
    }


    val cases = listOf(
        row(
            "VARIANCE_SAMPLE( [] ) - ARRAY",
            VARIANCE_SAMPLE(ARRAY()),
            null
        ),
        row(
            "VARIANCE_SAMPLE( [1] ) - ARRAY",
            VARIANCE_SAMPLE(ARRAY(1.aql)),
            null
        ),
        row(
            "VARIANCE_SAMPLE( [1, 1] ) - ARRAY",
            VARIANCE_SAMPLE(ARRAY(1.aql, 1.aql)),
            0.0
        ),
        row(
            "VARIANCE_SAMPLE( [ 1, 3, 6, 5, 2.0 ] ) - ARRAY",
            VARIANCE_SAMPLE(ARRAY<Number>(1.aql, 3.aql, 6.aql, 5.aql, 2.0.aql)),
            4.300000000000001
        ),
        row(
            "VARIANCE_SAMPLE( [ 1, 3, 6, 5, 2 ] ) - listOf",
            VARIANCE_SAMPLE(listOf<Number>(1, 3, 6, 5, 2).aql),
            4.300000000000001
        ),
        row(
            "VARIANCE_SAMPLE( [ 1.0, 3, 6, 5, 2 ] ) - listOf",
            VARIANCE_SAMPLE(listOf<Number>(1.0, 3, 6, 5, 2).aql),
            4.300000000000001
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
