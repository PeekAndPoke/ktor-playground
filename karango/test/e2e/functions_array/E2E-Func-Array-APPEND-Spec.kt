package de.peekandpoke.karango.e2e.functions_array

import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.e2e.*
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-Array-APPEND-Spec` : StringSpec({

    "APPEND must return the correct typeref for two int-arrays" {

        val result = driver.query {
            RETURN(
                APPEND(ARRAY(1.aql, 2.aql, 3.aql), ARRAY(4.aql, 5.aql, 6.aql))
            )
        }

        assertSoftly {

            result.first() shouldBe listOf(1, 2, 3, 4, 5, 6)

            result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Integer>"
        }
    }

    "APPEND must return the correct typeref for int-arrays and double-array" {

        val result = driver.query {
            RETURN(
                APPEND<Number>(ARRAY(1.aql, 2.aql, 3.aql), listOf(4.5, 5.5, 6.5).aql)
            )
        }

        assertSoftly {

            result.first() shouldBe listOf(1L, 2L, 3L, 4.5, 5.5, 6.5)

            result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Number>"
        }
    }

    "APPEND must return the correct typeref for incompatible arrays" {

        val result: Cursor<List<Any>> = driver.query {
            RETURN(
                APPEND<Any>(ARRAY(1.aql), ARRAY("a".aql))
            )
        }

        assertSoftly {

            result.first() shouldBe listOf(1L, "a")

            result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Object>"
        }
    }

    "APPEND must return the correct typeref for incompatible arrays, with unique parameter given" {

        val result = driver.query {
            RETURN(
                APPEND<Any>(ARRAY(1.aql, 1.aql), ARRAY("a".aql, "a".aql), true.aql)
            )
        }

        assertSoftly {

            result.first() shouldBe listOf(1L, "a")

            result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Object>"
        }
    }

    "APPEND two compatible arrays extracted from objects" {

        val result = driver.query {

            val a = LET("a") {
                listOf(
                    E2ePerson("a", 10),
                    E2ePerson("b", 15)
                )
            }

            val b = LET("b") {
                listOf(
                    E2ePerson("c", 15),
                    E2ePerson("d", 20)
                )
            }

            RETURN(
                APPEND(
                    FOR(a) { RETURN(it.age) }, FOR(b) { RETURN(it.age) }, true.aql
                )
            )
        }

        assertSoftly {

            result.first() shouldBe listOf(10, 15, 20)

            result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Integer>"
        }
    }

    "APPEND two in-compatible arrays extracted from objects" {

        val result = driver.query {

            val a = LET("a") {
                listOf(
                    E2ePerson("a", 10),
                    E2ePerson("b", 15)
                )
            }

            val b = LET("b") {
                listOf(
                    E2ePerson("c", 15),
                    E2ePerson("d", 20)
                )
            }

            RETURN(
                APPEND(
                    FOR(a) { RETURN(it.age) },
                    FOR(b) { RETURN(it.name) }
                )
            )
        }

        assertSoftly {

            result.first() shouldBe listOf(10L, 15L, "c", "d")

            result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Object>"
        }
    }

    "APPEND two in-compatible arrays extracted from objects ... array contraction" {

        val result = driver.query {

            val a = LET("a") {
                listOf(
                    E2ePerson("a", 10),
                    E2ePerson("b", 15)
                )
            }

            val b = LET("b") {
                listOf(
                    E2ePerson("c", 15),
                    E2ePerson("d", 20)
                )
            }

            RETURN(
                APPEND(a[`*`].age, b[`*`].name, true.aql)
            )
        }

        assertSoftly {

            result.first() shouldBe listOf(10L, 15L, "c", "d")

            result.query.ret.innerType().toString() shouldBe "java.util.List<java.lang.Object>"
        }
    }


    val cases = listOf(
        row(
            "APPEND ([], [])",
            APPEND(ARRAY(), ARRAY()),
            listOf()
        ),
        row(
            "APPEND ([1], [])",
            APPEND(ARRAY(1.aql), ARRAY()),
            listOf(1)
        ),
        row(
            "APPEND ([], [1])",
            APPEND(ARRAY(), ARRAY(1.aql)),
            listOf(1)
        ),
        row(
            "APPEND ([1,2,3], [4,5,6])",
            APPEND(ARRAY(1.aql, 2.aql, 3.aql), listOf(4, 5, 6).aql),
            listOf(1, 2, 3, 4, 5, 6)
        ),
        row(
            "APPEND (['1'], ['2'])",
            APPEND(ARRAY("1".aql), ARRAY("2".aql)),
            listOf("1", "2")
        ),
        row(
            "APPEND ([1, 1, 2, 3], [3, 4, 5, 5], true)",
            APPEND(ARRAY(1.aql, 1.aql, 2.aql, 3.aql), ARRAY(3.aql, 4.aql, 5.aql, 5.aql), true.aql),
            listOf(1, 2, 3, 4, 5)
        ),
        row(
            "APPEND (['1'], ['2'], true)",
            APPEND(ARRAY("1".aql), ARRAY("2".aql), true.aql),
            listOf("1", "2")
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = driver.query {
                @Suppress("UNCHECKED_CAST")
                RETURN(expression) as TerminalExpr<Any>
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = driver.query {
                val l = LET("l", expression)

                @Suppress("UNCHECKED_CAST")
                RETURN(l) as TerminalExpr<Any>
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
