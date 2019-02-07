package de.peekandpoke.karango

import de.peekandpoke.karango.query.*
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

object TestDef : EntityCollectionDef<TestData>("test", TestData::class.java) {
    val name = string("name")
}

data class TestData(
    override val _id: String? = null
) : Entity

class QueryBuilderSpec : StringSpec({

    "Filter operators must be applied correctly" {

        val query = query {
            FOR(TestDef) { t ->
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

            query.query.shouldContain("FOR c_test IN test")
            query.query.shouldContain("FILTER c_test.name == @c_test__name_1")
            query.query.shouldContain("FILTER c_test.name != @c_test__name_2")
            query.query.shouldContain("FILTER c_test.name > @c_test__name_3")
            query.query.shouldContain("FILTER c_test.name >= @c_test__name_4")
            query.query.shouldContain("FILTER c_test.name < @c_test__name_5")
            query.query.shouldContain("FILTER c_test.name <= @c_test__name_6")
            query.query.shouldContain("FILTER c_test.name IN @c_test__name_7")
            query.query.shouldContain("FILTER c_test.name NOT IN @c_test__name_8")
            query.query.shouldContain("FILTER c_test.name LIKE @c_test__name_9")
            query.query.shouldContain("FILTER c_test.name =~ @c_test__name_10")

            query.query.shouldBe(
                """|FOR c_test IN test
                   |    FILTER c_test.name == @c_test__name_1
                   |    FILTER c_test.name != @c_test__name_2
                   |    FILTER c_test.name > @c_test__name_3
                   |    FILTER c_test.name >= @c_test__name_4
                   |    FILTER c_test.name < @c_test__name_5
                   |    FILTER c_test.name <= @c_test__name_6
                   |    FILTER c_test.name IN @c_test__name_7
                   |    FILTER c_test.name NOT IN @c_test__name_8
                   |    FILTER c_test.name LIKE @c_test__name_9
                   |    FILTER c_test.name =~ @c_test__name_10
                   |    RETURN c_test
                   |
                """.trimMargin()
            )

            query.vars.shouldBe(mapOf(
                "c_test__name_1" to "V_EQ",
                "c_test__name_2" to "V_NE",
                "c_test__name_3" to "V_GT",
                "c_test__name_4" to "V_GTE",
                "c_test__name_5" to "V_LT",
                "c_test__name_6" to "V_LTE",
                "c_test__name_7" to listOf("V_IN"),
                "c_test__name_8" to listOf("V_NOT_IN"),
                "c_test__name_9" to "V_LIKE",
                "c_test__name_10" to "V_REGEX"
            ))
        }
    }
})
