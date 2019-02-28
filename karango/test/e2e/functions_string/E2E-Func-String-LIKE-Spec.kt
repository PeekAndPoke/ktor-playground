package de.peekandpoke.karango.e2e.functions_string

import de.peekandpoke.karango.aql.LIKE
import de.peekandpoke.karango.aql.aql
import de.peekandpoke.karango.e2e.db
import de.peekandpoke.karango.e2e.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

@Suppress("ClassName")
class `E2E-Func-String-LIKE-Spec` : StringSpec({

    val cases = listOf(
        row(
            "LIKE(\"cart\", \"ca_t\")",
            LIKE("cart".aql, "ca_t".aql),
            true
        ),
        row(
            "LIKE(\"carrot\", \"ca_t\")",
            LIKE("carrot".aql, "ca_t".aql),
            false
        ),
        row(
            "LIKE(\"carrot\", \"ca%t\")",
            LIKE("carrot".aql, "ca%t".aql),
            true
        ),

        row(
            "LIKE(\"foo bar baz\", \"bar\")",
            LIKE("foo bar baz".aql, "bar".aql),
            false
        ),
        row(
            "LIKE(\"foo bar baz\", \"%bar%\")",
            LIKE("foo bar baz".aql, "%bar%".aql),
            true
        ),
        row(
            "LIKE(\"bar\", \"%bar%\")",
            LIKE("bar".aql, "%bar%".aql),
            true
        ),

        row(
            "LIKE(\"FoO bAr BaZ\", \"fOo%bAz\")  in caseInsensitive mode",
            LIKE("FoO bAr BaZ".aql, "fOo%bAz".aql),
            false
        ),
        row(
            "LIKE(\"FoO bAr BaZ\", \"fOo%bAz\", false)  in caseInsensitive mode",
            LIKE("FoO bAr BaZ".aql, "fOo%bAz".aql, false.aql),
            false
        ),
        row(
            "LIKE(\"FoO bAr BaZ\", \"fOo%bAz\", true) in caseInsensitive mode",
            LIKE("FoO bAr BaZ".aql, "fOo%bAz".aql, true.aql),
            true
        )
    )

    for ((description, expression, expected) in cases) {

        "$description - direct return" {

            val result = db.query {
                RETURN(expression)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }

        "$description - return from LET" {

            val result = db.query {
                val l = LET("l", expression)

                RETURN(l)
            }

            withClue(expression, expected) {
                result.toList() shouldBe listOf(expected)
            }
        }
    }
})
