package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.Cursor
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

@Suppress("ClassName")
class `E2E-LetReturnSpec` : StringSpec({

    "Returning a String defined by LET" {

        val result = db.query {
            val a = LET("a", "string")
            RETURN(a)
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |LET `l_a` = @l_a_1
                    |RETURN `l_a`
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("l_a_1" to "string")
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf("string")
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "class java.lang.String"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.lang.String>"
            }
        }
    }

    "Returning a List<String> defined by LET" {

        val result = db.query {
            val a = LET("a") { listOf("s1", "s2") }
            RETURN(a)
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |LET `l_a` = @l_a_1
                    |RETURN `l_a`
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("l_a_1" to listOf("s1", "s2"))
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(listOf("s1", "s2"))
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "java.util.List<? extends java.lang.String>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<? extends java.lang.String>>"
            }
        }
    }

    "Returning a Map<String, String> defined by LET" {

        val result: Cursor<Map<String, String>> = db.query {
            val a = LET("a") { mapOf("s1" to "s2") }
            RETURN(a)
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |LET `l_a` = @l_a_1
                    |RETURN `l_a`
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("l_a_1" to mapOf("s1" to "s2"))
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(mapOf("s1" to "s2"))
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe
                        "java.util.Map<java.lang.String, ? extends java.lang.String>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe
                        "java.util.List<java.util.Map<java.lang.String, ? extends java.lang.String>>"
            }
        }
    }

    "Returning a List<Map<String, Int>> defined by LET" {

        val input = listOf(
            mapOf("s1" to 1, "s2" to 2),
            mapOf("s3" to 3)
        )

        val result: Cursor<List<Map<String, Int>>> = db.query {
            val a = LET("a", input)
            RETURN(a)
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |LET `l_a` = @l_a_1
                    |RETURN `l_a`
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("l_a_1" to input)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(input)
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe
                        "java.util.List<? extends java.util.Map<java.lang.String, ? extends java.lang.Integer>>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe
                        "java.util.List<java.util.List<? extends java.util.Map<java.lang.String, ? extends java.lang.Integer>>>"
            }
        }
    }
})
