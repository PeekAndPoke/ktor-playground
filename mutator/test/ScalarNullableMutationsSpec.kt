package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import kotlin.reflect.KMutableProperty0


class ScalarNullableMutationsSpec : StringSpec({

    fun <T> setProp(prop: KMutableProperty0<T>, v: T) = prop.set(v)

    "Mutating one nullable scalar property by assignment" {

        val source = WithNullableScalars()

        val result = source.mutate { draft ->
            draft.aString = "changed"
            draft.aByte = 1
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.aString shouldBe null
                source.aChar shouldBe null
                source.aByte shouldBe null
                source.aShort shouldBe null
                source.aInt shouldBe null
                source.aLong shouldBe null
                source.aFloat shouldBe null
                source.aDouble shouldBe null
                source.aBool shouldBe null
            }

            withClue("Result must be modified properly") {
                result.aString shouldBe "changed"
                result.aChar shouldBe null
                result.aByte shouldBe 1.toByte()
                result.aShort shouldBe null
                result.aInt shouldBe null
                result.aLong shouldBe null
                result.aFloat shouldBe null
                result.aDouble shouldBe null
                result.aBool shouldBe null
            }
        }
    }

    "Mutating nullable scalar properties via callback" {

        val source = WithNullableScalars(
            aString = "string",
            aByte = 1
        )

        val result = source.mutate { draft ->
            draft.aString { it?.toUpperCase() }
            draft.aByte { null }
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.aString shouldBe "string"
            }

            withClue("Result must be modified properly") {
                result.aString shouldBe "STRING"
                result.aByte shouldBe null
            }
        }
    }

    "Mutating nullable scalar properties via reflection" {

        val source = WithNullableScalars(
            aString = "string",
            aByte = 1
        )

        val result = source.mutate { draft ->
            @Suppress("RemoveExplicitTypeArguments")
            setProp<String?>(draft::aString, "changed")

            @Suppress("RemoveExplicitTypeArguments")
            setProp<Byte?>(draft::aByte, null)
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.aString shouldBe "string"
            }

            withClue("Result must be modified properly") {
                result.aString shouldBe "changed"
                result.aByte shouldBe null
            }
        }
    }

    "Mutating multiple nullable scalars in multiple ways" {

        val source = WithNullableScalars(
            aString = "string",
            aChar = 'c',
            aByte = 1,
            aShort = 1,
            aInt = 1,
            aLong = 1L,
            aFloat = 1.0f,
            aDouble = 1.0,
            aBool = true
        )

        val result = source.mutate { draft ->
            draft.aString { plus(" plus") }
            draft.aChar = 'd'
            draft.aByte = 2
            draft.aShort = 3
            draft.aInt { it?.times(4) }
            draft.aLong { it?.minus(2) }
            draft.aFloat { it?.div(2) }
            draft.aDouble { it?.times(3.5) }
            @Suppress("RemoveExplicitTypeArguments")
            setProp<Boolean?>(draft::aBool, false)
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.aString shouldBe "string"
                source.aChar shouldBe 'c'
                source.aByte shouldBe 1.toByte()
                source.aShort shouldBe 1.toShort()
                source.aInt shouldBe 1
                source.aLong shouldBe 1L
                source.aFloat shouldBe 1.0f
                source.aDouble shouldBe 1.0
                source.aBool shouldBe true
            }

            withClue("Result must be modified properly") {
                result.aString shouldBe "string plus"
                result.aChar shouldBe 'd'
                result.aByte shouldBe 2.toByte()
                result.aShort shouldBe 3.toShort()
                result.aInt shouldBe 4
                result.aLong shouldBe -1L
                result.aFloat shouldBe 0.5f
                result.aDouble shouldBe 3.5
                result.aBool shouldBe false
            }
        }
    }
})
