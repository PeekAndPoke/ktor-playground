package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.ARRAY
import de.peekandpoke.karango.aql.OBJECT
import de.peekandpoke.karango.aql.aql
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec


@Suppress("ClassName")
class `E2E-ReturnDirectly-Spec` : StringSpec({

    "Directly returning a String" {

        val result = db.query {
            RETURN(
                "string".aql("ret")
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @ret_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("ret_1" to "string")
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

    "Directly returning a List<String>" {

        val result = db.query {
            RETURN(
                listOf("s1", "s2").aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to listOf("s1", "s2"))
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(listOf("s1", "s2"))
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.String>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<java.lang.String>>"
            }
        }
    }

    "Directly returning a List<Integer> created with ARRAY()" {

        val result = db.query {
            RETURN(
                ARRAY(1.aql, 2.aql)
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN [@v_1, @v_2]
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to 1, "v_2" to 2)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(listOf(1L, 2L))
            }

            withClue("TypeRef for deserialization") {
                result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Number>"
            }

            withClue("TypeRef of TerminalExpr") {
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<java.lang.Number>>"
            }
        }
    }

    "Directly returning a Map<String, Integer> created with OBJECT()" {

        val result = db.query {
            RETURN(
                OBJECT("a".aql to 1.aql, "b".aql to 2.aql)
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN {@v_1: @v_2, @v_3: @v_4}
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to "a", "v_2" to 1, "v_3" to "b", "v_4" to 2)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(mapOf("a" to 1L, "b" to 2L))
            }

            withClue("TypeRef for deserialization") {
                result.query.ret.innerType().toString() shouldBe "java.util.Map<java.lang.String, java.lang.Number>"
            }

            withClue("TypeRef of TerminalExpr") {
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.Map<java.lang.String, java.lang.Number>>"
            }
        }
    }

    "Directly returning a Double" {

        val result = db.query {
            RETURN(
                12.34.aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to 12.34)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(12.34)
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "class java.lang.Number"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.lang.Number>"
            }
        }
    }

    "Directly returning an Integer" {

        val result = db.query {
            RETURN(
                1234.aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to 1234)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(1234L)
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "class java.lang.Number"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.lang.Number>"
            }
        }
    }

    "Directly returning a Long" {

        val result = db.query {
            RETURN(
                1234L.aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to 1234L)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(1234L)
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "class java.lang.Number"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.lang.Number>"
            }
        }
    }

    "Directly returning a List<Double>" {

        val result = db.query {
            RETURN(
                listOf(1.0, 2.5).aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to listOf(1.0, 2.5))
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(listOf(1.0, 2.5))
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Double>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<java.lang.Double>>"
            }
        }
    }

    "Directly returning a List of mixed numeric values" {

        val result = db.query {
            RETURN(
                listOf(0L, 1f, 2.5).aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to listOf(0L, 1f, 2.5))
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(listOf(0L, 1.0, 2.5))
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Object>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<java.lang.Object>>"
            }
        }
    }

    "Directly returning a List of mixed numeric values declared as List<Number>" {

        val result = db.query {
            RETURN(
                listOf<Number>(0L, 1f, 2.5).aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to listOf(0L, 1f, 2.5))
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(listOf(0L, 1.0, 2.5))
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Number>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<java.lang.Number>>"
            }
        }
    }

    "Directly returning a domain object" {

        val data = E2ePerson("Eddard", 42)

        val result = db.query {
            RETURN(
                data.aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to data)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(data)
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "class ${E2ePerson::class.qualifiedName}"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<${E2ePerson::class.qualifiedName}>"
            }
        }
    }

    "Directly returning a list of domain objects" {

        val data = listOf(
            E2ePerson("Eddard", 42),
            E2ePerson("John", 22)
        )

        val result = db.query {
            RETURN(
                data.aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to data)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(data)
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe "java.util.List<${E2ePerson::class.qualifiedName}>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<${E2ePerson::class.qualifiedName}>>"
            }
        }
    }

    "Directly returning a map of strings to domain objects" {

        val data = mapOf(
            "a" to E2ePerson("Eddard", 42),
            "b" to E2ePerson("John", 22)
        )

        val result = db.query {
            RETURN(
                data.aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to data)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(data)
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe
                        "java.util.Map<java.lang.String, ${E2ePerson::class.qualifiedName}>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe
                        "java.util.List<java.util.Map<java.lang.String, ${E2ePerson::class.qualifiedName}>>"
            }
        }
    }

    "Directly returning a map of strings to lists of domain objects" {

        val data = mapOf(
            "a" to listOf(E2ePerson("Eddard", 42), E2ePerson("John", 22)),
            "b" to listOf()
        )

        val result = db.query {
            RETURN(
                data.aql()
            )
        }

        assertSoftly {

            withClue("AQL-Query") {
                result.query.aql shouldBe """
                    |RETURN @v_1
                    |
                """.trimMargin()
            }

            withClue("AQL-vars") {
                result.query.vars shouldBe mapOf("v_1" to data)
            }

            withClue("Query-result") {
                result.toList() shouldBe listOf(data)
            }

            withClue("TypeRef for deserialization") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.innerType().toString() shouldBe
                        "java.util.Map<java.lang.String, java.util.List<${E2ePerson::class.qualifiedName}>>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe
                        "java.util.List<java.util.Map<java.lang.String, java.util.List<${E2ePerson::class.qualifiedName}>>>"
            }
        }
    }

})
