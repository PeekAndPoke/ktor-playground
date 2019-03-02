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
class `E2E-Func-Numeric-SUM-Spec` : StringSpec({

    "SUM from multiple LETs" {

        val result = db.query {
            val a = LET("a", 10.aql)
            val b = LET("b", 20.aql)

            RETURN(
                SUM(
                    ARRAY(a, b, 30.aql)
                )
            )
        }

        result.first() shouldBe 60.0
    }

    "SUM from multiple objects" {

        val result = db.query {
            val persons = LET("persons") {
                listOf(
                    Person("a", 10),
                    Person("b", 20),
                    Person("c", 30)
                )
            }

            RETURN(
                SUM(
                    FOR("p") IN (persons) { p ->
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
            "SUM( [1, 2, 3, 4] ) - listOf",
            SUM(
                listOf<Number>(1.5, 2, 3, 4).aql
            ),
            10.5
        ),
        row(
            "SUM( [1, 2, 3, 4] ) - ARRAY",
            SUM(
                ARRAY(1.aql, 2.aql, 3.aql, 4.aql)
            ),
            10.0
        ),
        row(
            "SUM( [null, -5, 6] )",
            SUM(
                ARRAY(null.aql.TO_NUMBER, (-5).aql, 6.aql)
            ),
            1.0
        ),
        row(
            "SUM( [ ] )",
            SUM(
                ARRAY()
            ),
            0.0
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
