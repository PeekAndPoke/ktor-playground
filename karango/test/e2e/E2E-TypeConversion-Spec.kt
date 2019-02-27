package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.TO_BOOL
import de.peekandpoke.karango.aql.aql
import io.kotlintest.data.forall
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-TypeConversion-Spec` : StringSpec({

    "TO_BOOL conversion of 'null' directly" {
        
        val result = db.query { 
            RETURN(null.aql())
        }
        
        result.toList() shouldBe listOf(null)
    }

    "TO_BOOL conversion of 'null' from LET" {

        val result = db.query {
            
            val l = LET("l", null)
            
            RETURN(l)
        }

        result.toList() shouldBe listOf(null)
    }

    "TO_BOOL conversion of 'null' from LET expression" {

        val result = db.query {

            val l = LET("l", null.aql())

            RETURN(l)
        }

        result.toList() shouldBe listOf(null)
    }

    "TO_BOOL conversion" {

        val cases = arrayOf(
            row("TO_BOOL(false)", false, c = false),
            row("TO_BOOL(true)", true, c = true),

            row("TO_BOOL(0)", 0, false),
            row("TO_BOOL(1)", 1, true),
            row("TO_BOOL(-1)", 1, true),

            row("TO_BOOL(0.0)", 0.0, false),
            row("TO_BOOL(0.1)", 0.1, true),
            row("TO_BOOL(-0.1)", 0.1, true),

            row("TO_BOOL(\"\") empty string", "", false),
            row("TO_BOOL(\"a\") none empty string", "a", true),

            row("TO_BOOL([]) empty list", listOf<Int>(), true),
            row("TO_BOOL([0]) none empty list", listOf(0), true),

            row("TO_BOOL(object)", X("a", 1), true)
//            row("TO_BOOL(null)", null, false)

            // TODO: check return of a null expression ... can we un-serialize this ?
        )

        forall(*cases) { description, input, expected ->

            val result = db.query {
                RETURN(
                    TO_BOOL(input.aql())
                )
            }

            withClue(description + " - return directly - \n\n" + result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
            }
        }

        forall(*cases) { description: String, input: Any?, expected: Boolean ->

            val result = db.query {
                val l = LET("l", input)

                RETURN(TO_BOOL(l))
            }

            withClue(description + " - return from LET - \n\n" + result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
            }
        }

        forall(*cases) { description: String, input: Any?, expected: Boolean ->

            val result = db.query {
                val l = LET("l", input.aql())
                
                RETURN(TO_BOOL(l))
            }

            withClue(description + " - return from LET Expression - \n\n" + result.query.aql + "\n\n" + result.query.vars + "\n\n") {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
