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
class `E2E-Func-Numeric-STDDEV_SAMPLE-Spec` : StringSpec({

    "STDDEV_SAMPLE from multiple LETs" {

        val result = driver.query {
            val a = LET("a", 10.aql)
            val b = LET("b", 20.aql)

            RETURN(
                STDDEV_SAMPLE(
                    ARRAY(a, b, 30.aql)
                )
            )
        }

        result.first() shouldBe 10L
    }

    "STDDEV_SAMPLE from multiple objects" {

        val result = driver.query {
            val persons = LET("persons") {
                listOf(
                    E2ePerson("a", 10),
                    E2ePerson("b", 20),
                    E2ePerson("c", 30)
                )
            }

            RETURN(
                STDDEV_SAMPLE(
                    FOR(persons) { p ->
                        FILTER(p.age GT 10)
                        RETURN(p.age)
                    }
                )
            )
        }

        result.first() shouldBe 7.0710678118654755
    }

    val cases = listOf(
        row(
            "STDDEV_SAMPLE( [] ) - ARRAY",
            STDDEV_SAMPLE(ARRAY()),
            null
        ),
        row(
            "STDDEV_SAMPLE( [1] ) - ARRAY",
            STDDEV_SAMPLE(ARRAY(1.aql)),
            null
        ),
        row(
            "STDDEV_SAMPLE( [1, 1] ) - ARRAY",
            STDDEV_SAMPLE(ARRAY(1.aql, 1.aql)),
            0.0
        ),
        row(
            "STDDEV_SAMPLE( [ 1, 3, 6, 5, 2 ] ) - ARRAY",
            STDDEV_SAMPLE(ARRAY(1.aql, 3.aql, 6.aql, 5.aql, 2.aql)),
            2.0736441353327724
        ),
        row(
            "STDDEV_SAMPLE( [ 1, 3, 6, 5, 2 ] ) - listOf",
            STDDEV_SAMPLE(listOf(1, 3, 6, 5, 2).aql),
            2.0736441353327724
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
