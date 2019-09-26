package de.peekandpoke.ultra.vault.hooks

import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.StorableMeta
import kotlin.reflect.full.hasAnnotation

/**
 * Repository marker for recording [UserRecord]
 *
 * Use this annotation on your repo, to signal that [UserRecord] should be set
 * in the meta data of a [Storable]
 */
@Target(AnnotationTarget.CLASS)
annotation class WithUserRecord

data class UserRecord(
    val user: String,
    val ip: String
)

class UserRecordOnSaveHook(private val provider: UserRecordProvider) : OnSaveHook {

    val user by lazy { provider() }

    @ExperimentalStdlibApi
    override fun <R> apply(repo: Repository<R>, storable: Storable<R>): Storable<R> {

        if (!repo::class.hasAnnotation<WithUserRecord>()) {
            return storable
        }

        return storable.withMeta(
            (storable._meta ?: StorableMeta()).copy(user = user)
        )
    }
}

interface UserRecordProvider {
    operator fun invoke(): UserRecord
}

class AnonymousUserRecordProvider : UserRecordProvider {
    override fun invoke() = UserRecord("anonymous", "unknown")
}

class StaticUserRecordProvider(private val user: UserRecord) : UserRecordProvider {
    override fun invoke() = user
}
