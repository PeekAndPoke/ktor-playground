package de.peekandpoke.ktorfx.broker

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.lang.reflect.Type

class OutgoingConverterSpec : StringSpec({

    "Shared lookup must be used correctly" {

        class DummyConverter(val init: String) : OutgoingParamConverter {
            override fun canHandle(type: Type) = true

            override fun convert(value: Any, type: Type) = "$value $init"
        }

        val subjectOne = OutgoingConverter(listOf(DummyConverter("one")))

        val subjectTwo = OutgoingConverter(listOf(DummyConverter("two")))

        assertSoftly {

            subjectOne.convert("x", String::class.java) shouldBe "x one"

            subjectTwo.convert("x", String::class.java) shouldBe "x two"
        }
    }
})
