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
class `E2E-Func-Numeric-MEDIAN-Spec` : StringSpec({

    "MEDIAN from multiple LETs" {

        val result = db.query {
            val a = LET("a", 10.aql)
            val b = LET("b", 20.aql)

            RETURN(
                MEDIAN(
                    ARRAY(a, b, 30.aql)
                )
            )
        }

        result.first() shouldBe 20.0
    }

    "MEDIAN from multiple objects" {

        val result = db.query {
            val persons = LET("persons") {
                listOf(
                    Person("a", 10),
                    Person("b", 20),
                    Person("c", 30),
                    Person("d", 100)
                )
            }

            RETURN(
                MEDIAN(
                    FOR("p") IN (persons) { p ->
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