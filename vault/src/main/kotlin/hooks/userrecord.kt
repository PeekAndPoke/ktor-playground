package de.peekandpoke.ultra.vault.hooks

import de.peekandpoke.ultra.vault.OnSaveHook

data class UserRecord(
    val user: String,
    val ip: String
)

@Suppress("PropertyName")
interface WithUserRecord {
    val _userRecord: UserRecord?
}

class UserRecordOnSaveHook(private val provider: UserRecordProvider) : OnSaveHook {

    val user by lazy { provider() }

    override fun <T> invoke(obj: T): T {

        if (obj is WithUserRecord) {
            obj.update(user)
        }

        return obj
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

private fun WithUserRecord.update(user: UserRecord) {

    val field = this.javaClass.declaredFields.first { it.name == ::_userRecord.name }

    field.isAccessible = true
    field.set(this, user)
}
