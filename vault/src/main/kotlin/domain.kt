package de.peekandpoke.ultra.vault

data class Stored<T>(
    val _id: String,
    val _key: String,
    val _rev: String,
    val value: T
) {
    val collection by lazy {
        _id.split("/").first()
    }

    val asRef: Ref<T> by lazy {
        Ref(_id, _key, _rev, value)
    }
}

data class Ref<T>(
    val _id: String,
    val _key: String,
    val _rev: String,
    val value: T
) {
    val collection by lazy {
        _id.split("/").first()
    }

    val asStored: Stored<T> by lazy {
        Stored(_id, _key, _rev, value)
    }
}
