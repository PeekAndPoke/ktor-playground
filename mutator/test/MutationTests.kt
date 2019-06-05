package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import kotlin.reflect.KMutableProperty0

/**
 * Created by gerk on 26.05.19 23:48
 */
class MutationTests : StringSpec({

    fun <T> setProp(prop: KMutableProperty0<T>, v: T) = prop.set(v)

    "Mutating one scalar property by assignment" {

        val source = SimplePerson("Sansa", 21)

        val result = source.mutate { draft ->
            draft.name = "Arya"
        }

        assertSoftly {
            source.shouldNotBe(result)

            source.name.shouldBe("Sansa")
            source.age.shouldBe(21)

            result.name.shouldBe("Arya")
            result.age.shouldBe(21)
        }
    }

    "Mutating one scalar property via callback" {

        val source = SimplePerson("Sansa", 21)

        val result = source.mutate { draft ->
            draft.name { toUpperCase() }
        }

        assertSoftly {
            source.shouldNotBe(result)

            source.name.shouldBe("Sansa")
            source.age.shouldBe(21)

            result.name.shouldBe("SANSA")
            result.age.shouldBe(21)
        }
    }

    "Mutating one scalar property via reflection" {

        val source = SimplePerson("Sansa", 21)

        val result = source.mutate { draft ->
            setProp<String>(draft::name, "Arya")
        }

        assertSoftly {
            source.shouldNotBe(result)

            source.name.shouldBe("Sansa")
            source.age.shouldBe(21)

            result.name.shouldBe("Arya")
            result.age.shouldBe(21)
        }
    }

    "Mutating multiple scalar properties by assignment" {

        val source = SimplePerson("Sansa", 21)

        val result = source.mutate { draft ->
            draft.name = "Arya"
            draft.age = 17
        }

        assertSoftly {
            source.shouldNotBe(result)

            source.name.shouldBe("Sansa")
            source.age.shouldBe(21)

            result.name.shouldBe("Arya")
            result.age.shouldBe(17)
        }
    }

    "Mutating multiple scalar properties via callback" {

        val source = SimplePerson("Sansa", 21)

        val result = source.mutate { draft ->
            draft.name { toLowerCase() }
            draft.age { it * 2 }
        }

        assertSoftly {
            source shouldNotBe result

            source.name shouldBe "Sansa"
            source.age shouldBe 21

            result.name shouldBe "sansa"
            result.age shouldBe 42
        }
    }

    "Mutation with nested objects" {

        val source = PersonWithAddress("Sansa", 21, Address("Winterfell", "W01"))

        val result1 = source.mutate { draft ->
            draft.name = "Arya"
            draft.age = 17
        }

        val result2 = source.mutate { draft ->
            draft.address.city = "Kingslanding"
            draft.address.zip = "K01"
        }

        assertSoftly {
            source shouldNotBe result1
            source.address shouldBe result1.address

            source.name shouldBe "Sansa"
            source.age shouldBe 21

            result1.name shouldBe "Arya"
            result1.age shouldBe 17

            ////

            source shouldNotBe result2
            source.address shouldNotBe result2.address

            result2.address.city shouldBe "Kingslanding"
            result2.address.zip shouldBe "K01"
        }
    }
})
