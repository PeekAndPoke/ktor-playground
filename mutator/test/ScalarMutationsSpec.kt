package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import kotlin.reflect.KMutableProperty0


class ScalarMutationsSpec : StringSpec({

    fun <T> setProp(prop: KMutableProperty0<T>, v: T) = prop.set(v)

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
            draft.aInt *= 2
            draft.aDouble *= 3.5
            @Suppress("RemoveExplicitTypeArguments")
            setProp<Boolean>(draft::aBool, false)
        }

        assertSoftly {
            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.aString shouldBe "string"
                source.aChar shouldBe 'c'
                source.aInt shouldBe 1
                source.aDouble shouldBe 1.0
                source.aBool shouldBe true
            }

            withClue("Result must be modified properly") {
                result.aString shouldBe "string plus"
                result.aChar shouldBe 'd'
                result.aInt shouldBe 2
                result.aDouble shouldBe 3.5
                result.aBool shouldBe false
            }
        }
    }
})
