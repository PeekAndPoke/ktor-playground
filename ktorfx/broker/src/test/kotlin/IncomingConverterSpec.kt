package de.peekandpoke.ktorfx.broker

import de.peekandpoke.ktorfx.broker.converters.IncomingPrimitiveConverter
import de.peekandpoke.ultra.common.SimpleLookup
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.ktor.http.parametersOf
import java.lang.reflect.Type

class IncomingConverterSpec : StringSpec({

    "Shared lookup must be used correctly" {

        class DummyConverter(val init: String) : IncomingParamConverter {
            override fun canHandle(type: Type) = true

            override fun convert(value: String, type: Type) = "$value $init"
        }

        val lookup = IncomingConverterLookup()

        val subjectOne = IncomingConverter(lookup, SimpleLookup { listOf(DummyConverter("one")) })

        val subjectTwo = IncomingConverter(lookup, SimpleLookup { listOf(DummyConverter("two")) })

        assertSoftly {

            subjectOne.convert("x", String::class.java) shouldBe "x one"

            subjectTwo.convert("x", String::class.java) shouldBe "x two"
        }
    }

    "Converting an object from route parameters" {

        data class MyClass(val name: String)

        val subject = IncomingConverter(
            IncomingConverterLookup(),
            SimpleLookup { listOf(IncomingPrimitiveConverter()) }
        )

        subject.convert(
            parametersOf("name", "ktorfx"),
            parametersOf("name", "ktorfx-query-param"),
            MyClass::class
        ) shouldBe MyClass("ktorfx")
    }

    "Converting an object from query parameters" {

        data class MyClass(val name: String)

        val subject = IncomingConverter(
            IncomingConverterLookup(),
            SimpleLookup { listOf(IncomingPrimitiveConverter()) }
        )

        subject.convert(
            parametersOf("other", "ktorfx"),
            parametersOf("name", "ktorfx-query-param"),
            MyClass::class
        ) shouldBe MyClass("ktorfx-query-param")
    }
})
