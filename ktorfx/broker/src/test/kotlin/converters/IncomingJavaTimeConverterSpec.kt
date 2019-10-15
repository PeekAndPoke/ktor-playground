package de.peekandpoke.ktorfx.broker.converters

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row
import java.time.*

class IncomingJavaTimeConverterSpec : StringSpec({

    listOf(
        row(Instant::class, true),

        row(LocalDate::class, true),
        row(LocalTime::class, true),
        row(LocalDateTime::class, true),

        // negative cases
        row(Object::class, false)

    ).forEach { (type, supported) ->

        "Must ${if (supported) "" else " NOT "} support the type '$type'" {

            val subject = IncomingJavaTimeConverter()

            subject.canHandle(type.java) shouldBe supported
        }
    }

    listOf(
        row("2019-01-01T12:00:00.000Z", Instant::class, Instant.ofEpochMilli(1546344000000)),

        row("2019-01-01", LocalDate::class, LocalDate.of(2019, 1, 1)),

        row("12:00:00", LocalTime::class, LocalTime.of(12, 0, 0)),

        row(
            "2019-01-01T12:00:00",
            LocalDateTime::class,
            LocalDateTime.of(2019, 1, 1, 12, 0, 0)
        ),

        row(
            "2019-01-01T12:00:00.000Z",
            ZonedDateTime::class,
            ZonedDateTime.parse("2019-01-01T12:00:00.000Z")
        )

    ).forEach { (input, type, expected) ->

        "Input '$input' must be correctly converted to type '$type'" {

            val subject = IncomingJavaTimeConverter()

            subject.convert(input, type.java) shouldBe expected
        }
    }
})
