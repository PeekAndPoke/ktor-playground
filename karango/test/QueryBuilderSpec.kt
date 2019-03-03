package de.peekandpoke.karango

import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.testdomain.*
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec


class QueryBuilderSpec : StringSpec({

    "Filter operators must be applied correctly" {

        val query = query {
            FOR(TestNames) { iter ->
                FILTER(iter.name EQ "V_EQ")
                FILTER(iter.name NE "V_NE")
                FILTER(iter.name GT "V_GT")
                FILTER(iter.name GTE "V_GTE")
                FILTER(iter.name LT "V_LT")
                FILTER(iter.name LTE "V_LTE")
                FILTER(iter.name IN listOf("V_IN"))
                FILTER(iter.name NOT_IN listOf("V_NOT_IN"))
                FILTER(iter.name LIKE "V_LIKE")
                FILTER(iter.name REGEX "V_REGEX")
                RETURN(iter)
            }
        }

        assertSoftly {

            query.aql.shouldContain("FOR `iter` IN `test-names`")
            query.aql.shouldContain("FILTER `iter`.`name` == @iter__name_1")
            query.aql.shouldContain("FILTER `iter`.`name` != @iter__name_2")
            query.aql.shouldContain("FILTER `iter`.`name` > @iter__name_3")
            query.aql.shouldContain("FILTER `iter`.`name` >= @iter__name_4")
            query.aql.shouldContain("FILTER `iter`.`name` < @iter__name_5")
            query.aql.shouldContain("FILTER `iter`.`name` <= @iter__name_6")
            query.aql.shouldContain("FILTER `iter`.`name` IN @iter__name_7")
            query.aql.shouldContain("FILTER `iter`.`name` NOT IN @iter__name_8")
            query.aql.shouldContain("FILTER `iter`.`name` LIKE @iter__name_9")
            query.aql.shouldContain("FILTER `iter`.`name` =~ @iter__name_10")
            query.aql.shouldContain("RETURN `iter`")

            query.aql.shouldBe(
                """ |FOR `iter` IN `test-names`
                    |    FILTER `iter`.`name` == @iter__name_1
                    |    FILTER `iter`.`name` != @iter__name_2
                    |    FILTER `iter`.`name` > @iter__name_3
                    |    FILTER `iter`.`name` >= @iter__name_4
                    |    FILTER `iter`.`name` < @iter__name_5
                    |    FILTER `iter`.`name` <= @iter__name_6
                    |    FILTER `iter`.`name` IN @iter__name_7
                    |    FILTER `iter`.`name` NOT IN @iter__name_8
                    |    FILTER `iter`.`name` LIKE @iter__name_9
                    |    FILTER `iter`.`name` =~ @iter__name_10
                    |    RETURN `iter`
                    |
                """.trimMargin()
            )

            query.vars.shouldBe(
                mapOf(
                    "iter__name_1" to "V_EQ",
                    "iter__name_2" to "V_NE",
                    "iter__name_3" to "V_GT",
                    "iter__name_4" to "V_GTE",
                    "iter__name_5" to "V_LT",
                    "iter__name_6" to "V_LTE",
                    "iter__name_7" to listOf("V_IN"),
                    "iter__name_8" to listOf("V_NOT_IN"),
                    "iter__name_9" to "V_LIKE",
                    "iter__name_10" to "V_REGEX"
                )
            )
        }
    }

    "Iterator name must be taken from parameter name of FOR loop" {

        val query = query {
            FOR(TestPersons) { iterator ->
                FILTER(iterator.addresses[`*`].street ALL EQ("street"))
                RETURN(iterator)
            }
        }

        query.aql.shouldBe(
            """ |FOR `iterator` IN `test-persons`
                |    FILTER `iterator`.`addresses`[*].`street` ALL == @iterator__addresses_STAR__street_ALL_1
                |    RETURN `iterator`
                |
            """.trimMargin()
        )
    }

    "Property path must be rendered correctly" {

        val query = query {
            FOR(TestPersons) { p ->
                FILTER(p.addresses[`*`].street ALL EQ("street"))
                RETURN(p)
            }
        }

        query.aql.shouldBe(
            """ |FOR `p` IN `test-persons`
                |    FILTER `p`.`addresses`[*].`street` ALL == @p__addresses_STAR__street_ALL_1
                |    RETURN `p`
                |
            """.trimMargin()
        )
    }

    "Property path with Array expansion [*] and contraction [**] must be rendered correctly" {

        val query = query {
            FOR(TestPersons) { person ->
                FILTER(person.books[`*`].authors[`*`].firstName[`**`] ALL EQ("street"))
                RETURN(person)
            }
        }

        query.aql.shouldBe(
            """ |FOR `person` IN `test-persons`
                |    FILTER `person`.`books`[*].`authors`[*].`firstName`[**] ALL == @person__books_STAR__authors_STAR__firstName_STAR2_ALL_1
                |    RETURN `person`
                |
            """.trimMargin()
        )
    }

    "Array operators ANY, ALL, NONE must be rendered correctly" {

        val query = query {
            FOR(TestPersons) { iter ->
                FILTER(iter.addresses[`*`].street ANY EQ("street"))
                FILTER(iter.addresses[`*`].street ALL NE("street"))
                FILTER(iter.addresses[`*`].street NONE IN(listOf("street")))
                RETURN(iter)
            }
        }

        query.aql.shouldBe(
            """ |FOR `iter` IN `test-persons`
                |    FILTER `iter`.`addresses`[*].`street` ANY == @iter__addresses_STAR__street_ANY_1
                |    FILTER `iter`.`addresses`[*].`street` ALL != @iter__addresses_STAR__street_ALL_2
                |    FILTER `iter`.`addresses`[*].`street` NONE IN @iter__addresses_STAR__street_NONE_3
                |    RETURN `iter`
                |
            """.trimMargin()
        )
    }
})
