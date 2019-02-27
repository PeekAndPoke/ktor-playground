package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.TO_STRING
import de.peekandpoke.karango.aql.aql
import io.kotlintest.data.forall
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeConversion-TO_STRING-Spec` : StringSpec({

    "TO_STRING conversion of 'null' directly" {

        val result = db.query {
            RETURN(
                TO_STRING(null.aql())
            )
        }

        result.toList() shouldBe listOf("")

        val result2 = db.query {
            RETURN(
                TO_STRING(null.aql)
            )
        }

        result2.toList() shouldBe listOf("")
    }

    "TO_STRING conversion of 'null' from LET" {

        val result = db.query {
            val l = LET("l", null)
            RETURN(
                TO_STRING(l)
            )
        }

        result.toList() shouldBe listOf("")

        val result2 = db.query {
            val l = LET("l", null.aql())
            RETURN(
                TO_STRING(l)
            )
        }

        result2.toList() shouldBe listOf("")

        val result3 = db.query {
            val l = LET("l", null.aql)
            RETURN(
                TO_STRING(l)
            )
        }

        result3.toList() shouldBe listOf("")
    }

    "TO_STRING conversion" {

        val cases = arrayOf(
            row("TO_STRING(false)", false, "false"),
            row("TO_STRING(true)", true, "true"),

            row("TO_STRING(0)", 0, "0"),
            row("TO_STRING(1)", 1, "1"),
            row("TO_STRING(-1)", -1, "-1"),

            row("TO_STRING(0.0)", 0.0, "0"),
            row("TO_STRING(0.1)", 0.1, "0.1"),
            row("TO_STRING(-0.1)", -0.1, "-0.1"),

            row("TO_STRING(\"\") empty string", "", ""),
            row("TO_STRING(\"a\") none empty string", "a", "a"),

            row("TO_STRING([]) empty list", listOf<Int>(), "[]"),
            row("TO_STRING([0]) none empty list", listOf(0), "[0]"),
            row("TO_STRING([1]) none empty list", listOf(1), "[1]"),
            row("TO_STRING([0, 0]) none empty list", listOf(0, 0), "[0,0]"),
            row("TO_STRING([0, 1]) none empty list", listOf(0, 1), "[0,1]"),
            row("TO_STRING([1, 1]) none empty list", listOf(1, 1), "[1,1]"),
            row("TO_STRING([1, 0]) none empty list", listOf(1, 0), "[1,0]"),
            row("TO_STRING([1, 0]) none empty list", listOf(1, listOf(2, 3)), "[1,[2,3]]"),
            row("TO_STRING(['x']) none empty list", listOf("x"), """["x"]"""),
            row("TO_STRING(['x', 'x']) none empty list", listOf("x", "x"), """["x","x"]"""),

            row("TO_STRING(object)", X("a", 1), """{"age":1,"name":"a"}"""),
            row(
                "TO_STRING([object]) list with one objects",
                listOf(X("a", 1)),
                """[{"age":1,"name":"a"}]"""
            ),
            row(
                "TO_STRING([object, object]) list with two objects",
                listOf(X("a", 1), X("b", 2)),
                """[{"age":1,"name":"a"},{"age":2,"name":"b"}]"""
            )
        )

        forall(*cases) { description, input, expected ->

            val result = db.query {
                RETURN(
                    TO_STRING(input.aql())
                )
            }

            val result2 = db.query {
                RETURN(
                    TO_STRING(input.aql)
                )
            }

            withClue(description + " - return directly - \n\n" + result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
                result2.toList() shouldBe listOf(expected)
            }
        }

        forall(*cases) { description, input, expected ->

            val result = db.query {
                val l = LET("l", input)

                RETURN(TO_STRING(l))
            }

            withClue(description + " - return from LET - \n\n" + result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
            }
        }

        forall(*cases) { description, input, expected ->

            val result = db.query {
                val l = LET("l", input.aql())

                RETURN(TO_STRING(l))
            }

            val result2 = db.query {
                val l = LET("l", input.aql)

                RETURN(TO_STRING(l))
            }

            withClue(description + " - return from LET Expression - \n\n" + result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
                result2.toList() shouldBe listOf(expected)
            }
        }
    }
})
