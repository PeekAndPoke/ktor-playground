package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import kotlin.reflect.KMutableProperty0


class ScalarMutationsSpec : StringSpec({

    fun <T> setProp(prop: KMutableProperty0<T>, v: T) = prop.set(v)

    "Mutating nothing will not clone the source" {

        val source = WithScalars()

        val result = source.mutate { }

        assertSoftly {

            withClue("When nothing was changed the source object must be returned") {
                (source === result) shouldBe true
            }
        }
    }

    "Mutating but not changing any value is treated like no mutation" {

        val source = WithScalars(
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
            draft.aString = "string"
            draft.aChar = 'c'
            draft.aByte = 1
            draft.aShort = 1
            draft.aInt = 1
            draft.aLong = 1L
            draft.aFloat = 1.0f
            draft.aDouble = 1.0
            draft.aBool = true
        }

        assertSoftly {

            withClue("When all mutations are not changing any value, the source object must be returned") {
                (source === result) shouldBe true
            }
        }
    }

    "Mutating one scalar property by assignment" {

        val source = WithScalars()

        val result = source.mutate { draft ->
            draft.aString = "changed"
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
                result.aString shouldBe "changed"
                result.aChar shouldBe 'c'
                result.aByte shouldBe 1.toByte()
                result.aShort shouldBe 1.toShort()
                result.aInt shouldBe 1
                result.aLong shouldBe 1L
                result.aFloat shouldBe 1.0f
                result.aDouble shouldBe 1.0
                result.aBool shouldBe true
            }
        }
    }

    "Mutating one scalar property via callback" {

        val source = WithScalars()

        val result = source.mutate { draft ->
            draft.aString { toUpperCase() }
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.aString shouldBe "string"
            }

            withClue("Result must be modified properly") {
                result.aString shouldBe "STRING"
            }
        }
    }

    "Mutating one scalar property via reflection" {

        val source = WithScalars()

        val result = source.mutate { draft ->
            @Suppress("RemoveExplicitTypeArguments")
            setProp<String>(draft::aString, "changed")
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.aString shouldBe "string"
            }

            withClue("Result must be modified properly") {
                result.aString shouldBe "changed"
            }
        }
    }

    "Mutating multiple scalar in multiple ways" {

        val source = WithScalars()

        val result = source.mutate { draft ->
            draft.aString { plus(" plus") }
            draft.aChar = 'd'
            draft.aByte = 2
            draft.aShort = 3
            draft.aInt *= 4
            draft.aLong -= 2
            draft.aFloat /= 2
            draft.aDouble *= 3.5
            @Suppress("RemoveExplicitTypeArguments")
            setProp<Boolean>(draft::aBool, false)
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
