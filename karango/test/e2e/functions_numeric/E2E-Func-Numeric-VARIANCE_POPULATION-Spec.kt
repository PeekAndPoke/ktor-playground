package de.peekandpoke.karango.e2e.functions_numeric

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.Person
import de.peekandpoke.karango.e2e.age
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Numeric-VARIANCE_POPULATION-Spec` : StringSpec({

    "VARIANCE_POPULATION from multiple LETs" {

        val result = db.query {
            val a = LET("a", 10.aql)
            val b = LET("b", 20.aql)

            RETURN(
                VARIANCE_POPULATION(
                    ARRAY(a, b, 30.aql)
                )
            )
        }

        result.first() shouldBe 66.66666666666667
    }

    "VARIANCE_POPULATION from multiple objects" {

        val result = db.query {
            val persons = LET("persons") {
                listOf(
                    Person("a", 10),
                    Person("b", 20),
                    Person("c", 30)
                )
            }

            RETURN(
                VARIANCE_POPULATION(
                    FOR("p") IN (persons) { p ->
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
            "VARIANCE_POPULATION( [] ) - ARRAY",
            VARIANCE_POPULATION(ARRAY()),
            null
        ),
        row(
            "VARIANCE_POPULATION( [1] ) - ARRAY",
            VARIANCE_POPULATION(ARRAY(1.aql)),
            0.0
        ),
        row(
            "VARIANCE_POPULATION( [1, 1] ) - ARRAY",
            VARIANCE_POPULATION(ARRAY(1.aql, 1.aql)),
            0.0
        ),
        row(
            "VARIANCE_POPULATION( [ 1, 3, 6, 5, 2 ] ) - ARRAY",
            VARIANCE_POPULATION(listOf(1, 3, 6, 5, 2).aql),
            3.4400000000000004
        ),
        row(
            "VARIANCE_POPULATION( [ 1, 3, 6, 5, 2 ] ) - listOf",
            VARIANCE_POPULATION(listOf(1, 3, 6, 5, 2).aql),
            3.4400000000000004
        ),
        row(
            "STDDEV( [ 1, 3, 6, 5, 2 ] ) - ARRAY",
            VARIANCE(ARRAY(1.aql, 3.aql, 6.aql, 5.aql, 2.aql)),
            3.4400000000000004
        ),
        row(
            "STDDEV( [ 1, 3, 6, 5, 2 ] ) - listOf",
            VARIANCE(listOf(1, 3, 6, 5, 2).aql),
            3.4400000000000004
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