package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.TO_LIST
import de.peekandpoke.karango.aql.aql
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeConversion-TO_LIST-Spec` : StringSpec({

    "TO_LIST conversion of 'null' directly" {

        val result = db.query {
            RETURN(
                TO_LIST(null.aql())
            )
        }

        result.toList() shouldBe listOf(listOf<Any>())

        val result2 = db.query {
            RETURN(
                TO_LIST(null.aql)
            )
        }

        result2.toList() shouldBe listOf(listOf<Any>())
    }

    "TO_LIST conversion of 'null' from LET" {

        val result = db.query {
            val l = LET("l", null)
            RETURN(
                TO_LIST(l)
            )
        }

        result.toList() shouldBe listOf(listOf<Any>())

        val result2 = db.query {
            val l = LET("l", null.aql())
            RETURN(
                TO_LIST(l)
            )
        }

        result2.toList() shouldBe listOf(listOf<Any>())

        val result3 = db.query {
            val l = LET("l", null.aql)
            RETURN(
                TO_LIST(l)
            )
        }

        result3.toList() shouldBe listOf(listOf<Any>())
    }

    val cases = listOf(
        row("TO_LIST(false)", false, listOf(false)),
        row("TO_LIST(true)", true, listOf(true)),

        row("TO_LIST(0)", 0, listOf(0L)),
        row("TO_LIST(1)", 1, listOf(1L)),
        row("TO_LIST(-1)", -1, listOf(-1L)),

        row("TO_LIST(0.0)", 0.0, listOf(0.0)),
        row("TO_LIST(0.1)", 0.1, listOf(0.1)),
        row("TO_LIST(-0.1)", -0.1, listOf(-0.1)),

        row("TO_LIST(\"\") empty string", "", listOf("")),
        row("TO_LIST(\"a\") none empty string", "a", listOf("a")),

        row("TO_LIST([]) empty list", listOf<Int>(), listOf<Int>()),
        row("TO_LIST([0]) none empty list", listOf(0), listOf(0L)),
        row("TO_LIST([1]) none empty list", listOf(1), listOf(1L)),
        row("TO_LIST([0, 0]) none empty list", listOf(0, 0), listOf(0L, 0L)),
        row("TO_LIST([0, 1]) none empty list", listOf(0, 1), listOf(0L, 1L)),
        row("TO_LIST([1, 1]) none empty list", listOf(1, 1), listOf(1L, 1L)),
        row("TO_LIST([1, 0]) none empty list", listOf(1, 0), listOf(1L, 0L)),
        row("TO_LIST([1, [2, 3]]) none empty list", listOf(1, listOf(2, 3)), listOf(1L, listOf(2L, 3L))),
        row("TO_LIST(['x']) none empty list", listOf("x"), listOf("x")),
        row("TO_LIST(['x', 'x']) none empty list", listOf("x", "x"), listOf("x", "x")),

        row("TO_LIST(object)", Person("a", 1), listOf(1L, "a")),
        row(
            "TO_LIST([object]) list with one objects",
            listOf(Person("a", 1)),
            listOf(mapOf("name" to "a", "age" to 1L))
        ),
        row(
            "TO_LIST([object, object]) list with two objects",
            listOf(Person("a", 1), Person("b", 2)),
            listOf(mapOf("name" to "a", "age" to 1L), mapOf("name" to "b", "age" to 2L))
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - return directly" {

            val result = db.query {
                RETURN(
                    TO_LIST(expression.aql())
                )
            }

            val result2 = db.query {
                RETURN(
                    TO_LIST(expression.aql)
                )
            }

            withClue(result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
                result2.toList() shouldBe listOf(expected)
            }
        }
    }

    for ((description, expression, expected) in cases) {

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(
                    TO_LIST(l)
                )
            }

            withClue(result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
            }
        }
    }

    for ((description, expression, expected) in cases) {

        "$description - return from LET Expression" {

            val result = db.query {
                val l = LET("l", expression.aql())

                RETURN(
                    TO_LIST(l)
                )
            }

            val result2 = db.query {
                val l = LET("l", expression.aql)

                RETURN(
                    TO_LIST(l)
                )
            }

            val result3 = db.query {
                val l = LET("l", expression.aql)

                RETURN(
                    l.TO_LIST
                )
            }

            withClue(result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
                result2.toList() shouldBe listOf(expected)
                result3.toList() shouldBe listOf(expected)
            }
        }
    }
})
