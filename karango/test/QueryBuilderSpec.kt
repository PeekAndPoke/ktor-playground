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
            FOR("iter") IN TestDataCollection { t ->
                FILTER(t.name EQ "V_EQ")
                FILTER(t.name NE "V_NE")
                FILTER(t.name GT "V_GT")
                FILTER(t.name GTE "V_GTE")
                FILTER(t.name LT "V_LT")
                FILTER(t.name LTE "V_LTE")
                FILTER(t.name IN listOf("V_IN"))
                FILTER(t.name NOT_IN listOf("V_NOT_IN"))
                FILTER(t.name LIKE "V_LIKE")
                FILTER(t.name REGEX "V_REGEX")
                RETURN(t)
            }
        }

        assertSoftly {

            query.aql.shouldContain("FOR `iter` IN `test`")
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
                """ |FOR `iter` IN `test`
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

    "Property path must be rendered correctly" {

        val query = query {
            FOR("iter") IN (TestPersonCollection) { p ->
                FILTER(p.addresses[`*`].street EQ "street")
                RETURN(p)
            }
        }

        query.aql.shouldBe(
            """ |FOR `iter` IN `test-persons`
                |    FILTER `iter`.`addresses`[*].`street` == @iter__addresses_STAR__street_1
                |    RETURN `iter`
                |
            """.trimMargin()
        )
    }

    "Property path with Array expansion [*] and contraction [**] must be rendered correctly" {

        val query = query {
            FOR("iter") IN (TestPersonCollection) { p ->
                FILTER(p.addresses[`*`].street EQ "street")
                FILTER(p.addresses[`*`].street[`**`] ALL EQ("street"))
                RETURN(p)
            }
        }

        query.aql.shouldBe(
            """ |FOR `iter` IN `test-persons`
                |    FILTER `iter`.`addresses`[*].`street` == @iter__addresses_STAR__street_1
                |    FILTER `iter`.`addresses`[*].`street`[**] ALL == @iter__addresses_STAR__street_STAR2_ALL_2
                |    RETURN `iter`
                |
            """.trimMargin()
        )
    }

    "Array operators ANY, ALL, NONE must be rendered correctly" {

        val query = query {
            FOR("iter") IN (TestPersonCollection) { p ->
                FILTER(p.addresses[`*`].street[`**`] ANY EQ("street"))
                FILTER(p.addresses[`*`].street[`**`] ALL NE("street"))
                FILTER(p.addresses[`*`].street[`**`] NONE IN(listOf("street")))
                RETURN(p)
            }
        }

        query.aql.shouldBe(
            """ |FOR `iter` IN `test-persons`
                |    FILTER `iter`.`addresses`[*].`street`[**] ANY == @iter__addresses_STAR__street_STAR2_ANY_1
                |    FILTER `iter`.`addresses`[*].`street`[**] ALL != @iter__addresses_STAR__street_STAR2_ALL_2
                |    FILTER `iter`.`addresses`[*].`street`[**] NONE IN @iter__addresses_STAR__street_STAR2_NONE_3
                |    RETURN `iter`
                |
            """.trimMargin()
        )
    }
})
