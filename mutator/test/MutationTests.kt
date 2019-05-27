package de.peekandpoke.mutator

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

/**
 * Created by gerk on 26.05.19 23:48
 */
class MutationTests : StringSpec({

    "Mutating one scalar property of a simple data class" {

        val source = Person("Sansa", 21)

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

    "Mutating multiple scalar property of a simple data class" {

        val source = Person("Sansa", 21)

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
})
