@file:Suppress("PropertyName")

package de.peekandpoke.ultra.vault

import de.peekandpoke.ultra.security.user.UserRecord
import de.peekandpoke.ultra.vault.hooks.Timestamps

data class StorableMeta(
    val ts: Timestamps? = null,
    val user: UserRecord? = null
)

sealed class Storable<T> {
    abstract val value: T
    abstract val _id: String
    abstract val _key: String
    abstract val _rev: String
    abstract val _meta: StorableMeta?

    val collection by lazy {
        _id.split("/").first()
    }

    abstract fun withValue(newValue: T): Storable<T>

    abstract fun withMeta(newMeta: StorableMeta): Storable<T>
}

data class Stored<T>(
    override val value: T,
    override val _id: String,
    override val _key: String,
    override val _rev: String,
    override val _meta: StorableMeta?
) : Storable<T>() {

    val asRef: Ref<T> by lazy {
        Ref(value, _id, _key, _rev, _meta)
    }

    override fun withValue(newValue: T): Stored<T> = copy(value = newValue)

    override fun withMeta(newMeta: StorableMeta): Stored<T> = copy(_meta = newMeta)
}

data class Ref<T>(
    override val value: T,
    override val _id: String,
    override val _key: String,
    override val _rev: String,
    override val _meta: StorableMeta?

) : Storable<T>() {

    val asStored: Stored<T> by lazy {
        Stored(value, _id, _key, _rev, _meta)
    }

    override fun withValue(newValue: T): Ref<T> = copy(value = newValue)

    override fun withMeta(newMeta: StorableMeta): Ref<T> = copy(_meta = newMeta)
}

data class New<T>(
    override val value: T,
    override val _id: String = "",
    override val _key: String = "",
    override val _rev: String = "",
    override val _meta: StorableMeta? = null
) : Storable<T>() {

    override fun withValue(newValue: T): New<T> = copy(value = newValue)

    override fun withMeta(newMeta: StorableMeta): New<T> = copy(_meta = newMeta)
}
