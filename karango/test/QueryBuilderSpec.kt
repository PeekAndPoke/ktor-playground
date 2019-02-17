package de.peekandpoke.karango

import de.peekandpoke.karango.query.*
import de.peekandpoke.karango.testdomain.*
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec


class QueryBuilderSpec : StringSpec({

    "Filter operators must be applied correctly" {

        val query = query {
            FOR(TestDataCollection) { t ->
                FILTER { t.name EQ "V_EQ" }
                FILTER { t.name NE "V_NE" }
                FILTER { t.name GT "V_GT" }
                FILTER { t.name GTE "V_GTE" }
                FILTER { t.name LT "V_LT" }
                FILTER { t.name LTE "V_LTE" }
                FILTER { t.name IN listOf("V_IN") }
                FILTER { t.name NOT_IN listOf("V_NOT_IN") }
                FILTER { t.name LIKE "V_LIKE" }
                FILTER { t.name REGEX "V_REGEX" }
                RETURN(t)
            }
        }

        assertSoftly {

            query.aql.shouldContain("FOR i_test IN `test`")
            query.aql.shouldContain("FILTER i_test.name == @test__name_1")
            query.aql.shouldContain("FILTER i_test.name != @test__name_2")
            query.aql.shouldContain("FILTER i_test.name > @test__name_3")
            query.aql.shouldContain("FILTER i_test.name >= @test__name_4")
            query.aql.shouldContain("FILTER i_test.name < @test__name_5")
            query.aql.shouldContain("FILTER i_test.name <= @test__name_6")
            query.aql.shouldContain("FILTER i_test.name IN @test__name_7")
            query.aql.shouldContain("FILTER i_test.name NOT IN @test__name_8")
            query.aql.shouldContain("FILTER i_test.name LIKE @test__name_9")
            query.aql.shouldContain("FILTER i_test.name =~ @test__name_10")

            query.aql.shouldBe(
                """ |FOR i_test IN `test`
                    |    FILTER i_test.name == @test__name_1
                    |    FILTER i_test.name != @test__name_2
                    |    FILTER i_test.name > @test__name_3
                    |    FILTER i_test.name >= @test__name_4
                    |    FILTER i_test.name < @test__name_5
                    |    FILTER i_test.name <= @test__name_6
                    |    FILTER i_test.name IN @test__name_7
                    |    FILTER i_test.name NOT IN @test__name_8
                    |    FILTER i_test.name LIKE @test__name_9
                    |    FILTER i_test.name =~ @test__name_10
                    |    RETURN i_test
                    |
                """.trimMargin()
            )

            query.vars.shouldBe(
                mapOf(
                    "i_test__name_1" to "V_EQ",
                    "i_test__name_2" to "V_NE",
                    "i_test__name_3" to "V_GT",
                    "i_test__name_4" to "V_GTE",
                    "i_test__name_5" to "V_LT",
                    "i_test__name_6" to "V_LTE",
                    "i_test__name_7" to listOf("V_IN"),
                    "i_test__name_8" to listOf("V_NOT_IN"),
                    "i_test__name_9" to "V_LIKE",
                    "i_test__name_10" to "V_REGEX"
                )
            )
        }
    }

    "Nested query must be rendered correctly" {

        val query = query {
            FOR(TestPersonCollection) { p ->
                FILTER { p.addresses.`*`.street EQ "street" }
                RETURN(p)
            }
        }

        query.aql.shouldBe(
            """ |FOR i_test_persons IN `test-persons`
                |    FILTER i_test_persons.addresses[*].street == @test_persons__addresses_STAR__street_1
                |    RETURN i_test_persons
                |
            """.trimMargin()
        )
    }

    "Array expansion [*] and contraction [**] must be rendered correctly" {

        val query = query {
            FOR(TestPersonCollection) { p ->
                FILTER { p.addresses.`*`.street.`**` EQ "street" }
                RETURN(p)
            }
        }

        query.aql.shouldBe(
            """ |FOR i_test_persons IN `test-persons`
                |    FILTER i_test_persons.addresses[*].street[**] == @test_persons__addresses_STAR__street_STAR2_1
                |    RETURN i_test_persons
                |
            """.trimMargin()
        )
    }

    "Array operators ANY, ALL, NONE must be rendered correctly" {

        val query = query {
            FOR(TestPersonCollection) { p ->
                FILTER { p.addresses.`*`.street.`**` ANY { it EQ "street" } }
                FILTER { p.addresses.`*`.street.`**` ALL { it NE "street" } }
                FILTER { p.addresses.`*`.street.`**` NONE { it IN listOf("street") } }
                RETURN(p)
            }
        }

        query.aql.shouldBe(
            """ |FOR i_test_persons IN `test-persons`
                |    FILTER i_test_persons.addresses[*].street[**] ANY == @test_persons__addresses_STAR__street_STAR2_ANY_1
                |    FILTER i_test_persons.addresses[*].street[**] ALL != @test_persons__addresses_STAR__street_STAR2_ALL_2
                |    FILTER i_test_persons.addresses[*].street[**] NONE IN @test_persons__addresses_STAR__street_STAR2_NONE_3
                |    RETURN i_test_persons
                |
            """.trimMargin()
        )
    }
})
