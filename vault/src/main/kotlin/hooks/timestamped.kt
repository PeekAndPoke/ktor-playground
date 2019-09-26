package de.peekandpoke.ultra.vault.hooks

import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.StorableMeta
import java.time.Instant
import kotlin.reflect.full.hasAnnotation

/**
 * Repository marker for recording [Timestamps]
 *
 * Use this annotation on your repo, to signal that [Timestamps] should be set
 * in the meta data of a [Storable]
 */
@Target(AnnotationTarget.CLASS)
annotation class WithTimestamps

data class Timestamps(
    val createdAt: Instant?,
    val updatedAt: Instant?
)

class TimestampedOnSaveHook : OnSaveHook {
    @ExperimentalStdlibApi
    override fun <R> apply(repo: Repository<R>, storable: Storable<R>): Storable<R> {

        if (!repo::class.hasAnnotation<WithTimestamps>()) {
            return storable
        }

        return storable.withMeta(
            update(
                storable._meta ?: StorableMeta()
            )
        )
    }

    private fun update(meta: StorableMeta): StorableMeta {

        val now = Instant.now()

        return when (val current = meta.ts) {

            null -> meta.copy(
                ts = Timestamps(
                    createdAt = now,
                    updatedAt = now
                )
            )

            else -> meta.copy(
                ts = Timestamps(
                    createdAt = current.createdAt ?: now,
                    updatedAt = now
                )
            )
        }
    }
}
