package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import kotlin.reflect.KMutableProperty0


class AnyMutationsSpec : StringSpec({

    fun <T> setProp(prop: KMutableProperty0<T>, v: T) = prop.set(v)

    "Mutating but not changing any value is treated like no mutation" {

        val source = WithAnyObject(
            anObject = "string"
        )

        val result = source.mutate { draft ->
            draft.anObject = "string"
        }

        assertSoftly {

            withClue("When all mutations are not changing any value, the source object must be returned") {
                (source === result) shouldBe true
            }
        }
    }

    "Mutating one 'kotlin.Any' property by assignment" {

        val source = WithAnyObject(anObject = 0)

        val result = source.mutate { draft ->
            draft.anObject = "changed"
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.anObject shouldBe 0
            }

            withClue("Result must be modified properly") {
                result.anObject shouldBe "changed"
            }
        }
    }

    "Mutating one 'kotlin.Any' property via callback" {

        val source = WithAnyObject(anObject = "string")

        val result = source.mutate { draft ->
            draft.anObject {
                when (this) {
                    is String -> toUpperCase()
                    else -> this
                }
            }
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.anObject shouldBe "string"
            }

            withClue("Result must be modified properly") {
                result.anObject shouldBe "STRING"
            }
        }
    }

    "Mutating one 'kotlin.Any' property via reflection" {

        val source = WithAnyObject(anObject = 0)

        val result = source.mutate { draft ->
            @Suppress("RemoveExplicitTypeArguments")
            setProp<Any>(draft::anObject, "changed")
        }

        assertSoftly {

            source shouldNotBe result

            withClue("Source object must NOT be modified") {
                source.anObject shouldBe 0
            }

            withClue("Result must be modified properly") {
                result.anObject shouldBe "changed"
            }
        }
    }
})
