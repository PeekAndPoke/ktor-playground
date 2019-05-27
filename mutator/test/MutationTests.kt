package de.peekandpoke.mutator

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

/**
 * Created by gerk on 26.05.19 23:48
 */
class MutationTests : StringSpec({

    "Mutating scalar properties of a simple data class" {

        val source = Person("Susi", 45)

//        val result = source.mutate {
//
//        }

        1.shouldBe(2)
    }

})
