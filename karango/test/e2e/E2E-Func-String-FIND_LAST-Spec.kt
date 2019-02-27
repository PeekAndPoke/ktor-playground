package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.aql.FIND_LAST
import de.peekandpoke.karango.aql.aql
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
open class `E2E-Func-String-FIND_LAST-Spec` : StringSpec({

    val cases = listOf(
        row(
            "FIND_LAST empty needle in empty haystack",
            FIND_LAST("".aql, "".aql),
            0L
        ),
        row(
            "FIND_LAST empty needle in haystack",
            FIND_LAST("abc".aql, "".aql),
            3L
        ),
        row(
            "FIND_LAST not finding (no start no end)",
            FIND_LAST("abc".aql, "x".aql),
            -1L
        ),
        row(
            "FIND_LAST finding (no start no end)",
            FIND_LAST("abc abc abc".aql, "abc".aql),
            8L
        ),
        row(
            "FIND_LAST finding with start (no end)",
            FIND_LAST("abc abc abc".aql, "abc".aql, 1.aql),
            8L
        ),
        row(
            "FIND_LAST finding with start and end",
            FIND_LAST("abc abc abc".aql, "abc".aql, 1.aql, 6.aql),
            4L
        ),
        row(
            "FIND_LAST not finding with start and end",
            FIND_LAST("abc abc abc".aql, "abc".aql, 1.aql, 2.aql),
            -1L
        ),
        row(
            "FIND_LAST not finding with start greater end",
            FIND_LAST("abc abc abc".aql, "abc".aql, 2.aql, 1.aql),
            -1L
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = db.query {
                RETURN(expression)
            }

            withClue("|| $expression || $expected ||") {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue("|| $expression || $expected ||") {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
