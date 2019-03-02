package de.peekandpoke.karango.e2e

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
                result.query.ret.innerType().toString() shouldBe "java.util.List<? extends java.lang.String>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<? extends java.lang.String>>"
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
                result.query.ret.innerType().toString() shouldBe "java.util.List<? extends java.lang.Double>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<? extends java.lang.Double>>"
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
                result.query.ret.innerType().toString() shouldBe "java.util.List<?>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<?>>"
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
                result.query.ret.innerType().toString() shouldBe "java.util.List<? extends java.lang.Number>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<? extends java.lang.Number>>"
            }
        }
    }

    "Directly returning a domain object" {

        val data = Person("Eddard", 42) 
        
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
                result.query.ret.innerType().toString() shouldBe "class ${Person::class.qualifiedName}"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<${Person::class.qualifiedName}>"
            }
        }
    }

    "Directly returning a list of domain objects" {

        val data = listOf(
            Person("Eddard", 42),
            Person("John", 22)
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
                result.query.ret.innerType().toString() shouldBe "java.util.List<? extends ${Person::class.qualifiedName}>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe "java.util.List<java.util.List<? extends ${Person::class.qualifiedName}>>"
            }
        }
    }

    "Directly returning a map of strings to domain objects" {

        val data = mapOf(
            "a" to Person("Eddard", 42),
            "b" to Person("John", 22)
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
                        "java.util.Map<java.lang.String, ? extends ${Person::class.qualifiedName}>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe 
                        "java.util.List<java.util.Map<java.lang.String, ? extends ${Person::class.qualifiedName}>>"
            }
        }
    }

    "Directly returning a map of strings to lists of domain objects" {

        val data = mapOf(
            "a" to listOf(Person("Eddard", 42), Person("John", 22)),
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
                        "java.util.Map<java.lang.String, ? extends java.util.List<? extends ${Person::class.qualifiedName}>>"
            }

            withClue("TypeRef of TerminalExpr") {
                @Suppress("RemoveExplicitTypeArguments")
                result.query.ret.getType().toString() shouldBe
                        "java.util.List<java.util.Map<java.lang.String, ? extends java.util.List<? extends ${Person::class.qualifiedName}>>>"
            }
        }
    }

})
