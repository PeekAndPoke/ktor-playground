@file:Suppress("PropertyName")

package de.peekandpoke.ultra.vault

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonUnwrapped
import de.peekandpoke.ultra.vault.hooks.Timestamps
import de.peekandpoke.ultra.vault.hooks.UserRecord

data class StorableMeta(
    val ts: Timestamps? = null,
    val user: UserRecord? = null
)

sealed class Storable<T> {
    @get: JsonUnwrapped
    abstract val value: T

    @get: JsonInclude(JsonInclude.Include.NON_EMPTY)
    abstract val _id: String

    @get: JsonInclude(JsonInclude.Include.NON_EMPTY)
    abstract val _key: String

    @get: JsonInclude(JsonInclude.Include.NON_EMPTY)
    abstract val _rev: String

    abstract val _meta: StorableMeta?

    abstract fun withValue(newValue: T): Storable<T>

    abstract fun withMeta(newMeta: StorableMeta): Storable<T>

    @get:JsonIgnore
    val collection by lazy {
        _id.split("/").first()
    }
}

data class Stored<T>(
    override val value: T,
    override val _id: String,
    override val _key: String,
    override val _rev: String,
    override val _meta: StorableMeta?
) : Storable<T>() {

    @get:JsonIgnore
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

    @get:JsonIgnore
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
