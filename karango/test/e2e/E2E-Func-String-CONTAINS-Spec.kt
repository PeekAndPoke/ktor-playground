package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.CONCAT
import de.peekandpoke.karango.aql.CONTAINS
import de.peekandpoke.karango.aql.aql
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

@Suppress("ClassName")
class `E2E-Func-String-CONTAINS-Spec` : StringSpec({

    "infix CONTAINS matching an input value" {
        val result = db.query {
            RETURN(
                "abc".aql() CONTAINS "b".aql()
            )
        }

        result.toList() shouldBe listOf(true)
    }

    "prefix CONTAINS matching an input value" {
        val result = db.query {
            RETURN(
                CONTAINS("abc".aql(), "b".aql())
            )
        }

        result.toList() shouldBe listOf(true)
    }

    "infix CONTAINS not matching an input value" {
        val result = db.query {
            RETURN(
                "abc".aql() CONTAINS "X".aql()
            )
        }

        result.toList() shouldBe listOf(false)
    }

    "prefix CONTAINS not matching on input value expression" {
        val result = db.query {
            RETURN(
                CONTAINS("abc".aql(), "X".aql())
            )
        }

        result.toList() shouldBe listOf(false)
    }

    "infix CONTAINS matching two expressions" {
        val result = db.query {
            RETURN(
                CONCAT("abc".aql(), "def".aql()) CONTAINS CONCAT("c".aql(), "d".aql())
            )
        }

        result.toList() shouldBe listOf(true)
    }

    "infix CONTAINS not matching two expressions" {
        val result = db.query {
            RETURN(
                CONCAT("abc".aql(), "def".aql()) CONTAINS CONCAT("X".aql(), "Y".aql())
            )
        }

        result.toList() shouldBe listOf(false)
    }

    "prefix CONTAINS matching two expressions" {
        val result = db.query {
            RETURN(
                CONTAINS(CONCAT("abc".aql(), "def".aql()) ,CONCAT("c".aql(), "d".aql()))
            )
        }

        result.toList() shouldBe listOf(true)
    }

    "prefix CONTAINS not matching two expression" {
        val result = db.query {
            RETURN(
                CONTAINS(CONCAT("abc".aql(), "def".aql()) ,CONCAT("X".aql(), "Y".aql()))
            )
        }

        result.toList() shouldBe listOf(false)
    }
})
