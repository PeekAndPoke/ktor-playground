import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun main() {

    println("Please play with me")


    println(
        Instant.now()
    )

    println(
        LocalDateTime.of(2019, 1, 1, 12, 0, 30).atZone(ZoneId.of("UTC"))
    )

    println(
        LocalDateTime.of(2019, 1, 1, 12, 0, 30).atZone(ZoneId.of("Europe/Berlin"))
    )

    println(
        ZonedDateTime.parse("2019-01-01T12:00:30+01:00")
    )
}
