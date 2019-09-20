@file:Suppress("PropertyName")

package de.peekandpoke.ultra.vault

import com.fasterxml.jackson.annotation.JsonIgnore

sealed class Storable<T> {
    abstract val _id: String
    abstract val _key: String
    abstract val _rev: String
    abstract val value: T

    abstract fun with(newValue: T): Storable<T>

    @get:JsonIgnore
    val collection by lazy {
        _id.split("/").first()
    }
}

data class Stored<T>(
    override val _id: String,
    override val _key: String,
    override val _rev: String,
    override val value: T
) : Storable<T>() {

    @get:JsonIgnore
    val asRef: Ref<T> by lazy {
        Ref(_id, _key, _rev, value)
    }

    override fun with(newValue: T): Stored<T> = copy(value = newValue)
}

data class Ref<T>(
    override val _id: String,
    override val _key: String,
    override val _rev: String,
    override val value: T
) : Storable<T>() {

    @get:JsonIgnore
    val asStored: Stored<T> by lazy {
        Stored(_id, _key, _rev, value)
    }

    override fun with(newValue: T): Ref<T> = copy(value = newValue)
}

data class New<T>(
    override val value: T
) : Storable<T>() {

    override val _id: String = ""
    override val _key: String = ""
    override val _rev: String = ""

    override fun with(newValue: T): New<T> = copy(value = newValue)
}
