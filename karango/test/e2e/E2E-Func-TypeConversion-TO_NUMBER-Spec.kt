package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.TO_NUMBER
import de.peekandpoke.karango.aql.aql
import io.kotlintest.data.forall
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-TypeConversion-TO_NUMBER-Spec` : StringSpec({

    "TO_NUMBER conversion of 'null' directly" {

        val result = db.query {
            RETURN(
                TO_NUMBER(null.aql())
            )
        }

        result.toList() shouldBe listOf(0.0)

        val result2 = db.query {
            RETURN(
                TO_NUMBER(null.aql)
            )
        }

        result2.toList() shouldBe listOf(0.0)
    }

    "TO_NUMBER conversion of 'null' from LET" {

        val result = db.query {
            val l = LET("l", null)
            RETURN(
                TO_NUMBER(l)
            )
        }

        result.toList() shouldBe listOf(0.0)

        val result2 = db.query {
            val l = LET("l", null.aql())
            RETURN(
                TO_NUMBER(l)
            )
        }

        result2.toList() shouldBe listOf(0.0)

        val result3 = db.query {
            val l = LET("l", null.aql)
            RETURN(
                TO_NUMBER(l)
            )
        }

        result3.toList() shouldBe listOf(0.0)
    }

    "TO_NUMBER conversion" {

        val cases = arrayOf(
            row("TO_NUMBER(false)", false, 0.0),
            row("TO_NUMBER(true)", true, 1.0),

            row("TO_NUMBER(0)", 0, 0.0),
            row("TO_NUMBER(1)", 1, 1.0),
            row("TO_NUMBER(-1)", -1, -1.0),

            row("TO_NUMBER(0.0)", 0.0, 0.0),
            row("TO_NUMBER(0.1)", 0.1, 0.1),
            row("TO_NUMBER(-0.1)", -0.1, -0.1),

            row("TO_NUMBER(\"\") empty string", "", 0.0),
            row("TO_NUMBER(\"a\") none empty string", "a", 0L),

            row("TO_NUMBER([]) empty list", listOf<Int>(), 0.0),
            row("TO_NUMBER([0]) none empty list", listOf(0), 0.0),
            row("TO_NUMBER([1]) none empty list", listOf(1), 1.0),
            row("TO_NUMBER([0, 0]) none empty list", listOf(0, 0), 0L),
            row("TO_NUMBER([0, 1]) none empty list", listOf(0, 1), 0L),
            row("TO_NUMBER([1, 1]) none empty list", listOf(1, 1), 0L),
            row("TO_NUMBER([1, 0]) none empty list", listOf(1, 0), 0L),
            row("TO_NUMBER([1, [2, 3]]) none empty list", listOf(1, listOf(2, 3)), 0L),
            row("TO_NUMBER(['x']) none empty list", listOf("x"), 0L),
            row("TO_NUMBER(['x', 'x']) none empty list", listOf("x", "x"), 0L),

            row("TO_NUMBER(object)", X("a", 1), 0L),
            row("TO_NUMBER([object]) list with one objects", listOf(X("a", 1)), 0L),
            row("TO_NUMBER([object, object]) list with two objects", listOf(X("a", 1), X("b", 2)), 0L)
        )

        forall(*cases) { description, input, expected ->

            val result = db.query {
                RETURN(
                    TO_NUMBER(input.aql())
                )
            }

            val result2 = db.query {
                RETURN(
                    TO_NUMBER(input.aql)
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

                RETURN(TO_NUMBER(l))
            }

            withClue(description + " - return from LET - \n\n" + result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
            }
        }

        forall(*cases) { description, input, expected ->

            val result = db.query {
                val l = LET("l", input.aql())

                RETURN(
                    TO_NUMBER(l)
                )
            }

            val result2 = db.query {
                val l = LET("l", input.aql)

                RETURN(
                    TO_NUMBER(l)
                )
            }

            val result3 = db.query {
                val l = LET("l", input.aql)

                RETURN(
                    l.TO_NUMBER
                )
            }

            withClue(description + " - return from LET Expression - \n\n" + result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
                result2.toList() shouldBe listOf(expected)
                result3.toList() shouldBe listOf(expected)
            }
        }
    }
})
