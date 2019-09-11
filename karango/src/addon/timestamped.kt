package de.peekandpoke.karango.addon

import de.peekandpoke.karango.OnSaveHook
import java.time.LocalDateTime

data class Timestamps(
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

@Suppress("PropertyName")
interface Timestamped {
    val _ts: Timestamps?
}

class TimestampedOnSaveHook : OnSaveHook {
    override operator fun <T> invoke(obj: T): T {

        if (obj is Timestamped) {
            obj.update()
        }

        return obj
    }
}

private fun Timestamped.update() {

    val now = LocalDateTime.now()

    val ts = when (val current = _ts) {

        null -> Timestamps(now, now)

        else -> current.copy(
            createdAt = current.createdAt ?: now,
            updatedAt = now
        )
    }

    val field = this.javaClass.declaredFields.first { it.name == ::_ts.name }

    field.isAccessible = true
    field.set(this, ts)
}

