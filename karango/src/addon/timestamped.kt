package de.peekandpoke.karango.addon

import java.time.LocalDateTime

data class Timestamps(
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

@Suppress("PropertyName")
interface Timestamped {
    val _ts: Timestamps?
}

internal fun Timestamped.updateTimestamps() {

    val now = LocalDateTime.now()

    val ts = when (val current = _ts) {

        null -> Timestamps(now, now)

        else -> current.copy(
            createdAt = current.createdAt ?: now,
            updatedAt = now
        )
    }

    val field = this.javaClass.declaredFields.first { it.name == "_ts" }

    field.isAccessible = true
    field.set(this, ts)
}

