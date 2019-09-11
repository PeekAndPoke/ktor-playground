package de.peekandpoke.karango.addon

import de.peekandpoke.karango.OnSaveHook

data class UserRecord(
    val user: String,
    val ip: String
)

@Suppress("PropertyName")
interface WithUserRecord {
    val _userRecord: UserRecord?
}

class UserRecordOnSaveHook(private val provider: () -> UserRecord): OnSaveHook {
    override fun <T> invoke(obj: T): T {

        if (obj is WithUserRecord) {
            obj.update(provider)
        }

        return obj
    }
}

private fun WithUserRecord.update(provider: () -> UserRecord) {

    val field = this.javaClass.declaredFields.first { it.name == ::_userRecord.name }

    field.isAccessible = true
    field.set(this, provider())
}
