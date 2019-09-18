package de.peekandpoke.ultra.vault

data class Stored<T>(
    val _id: String,
    val _key: String,
    val _rev: String,
    val value: T
)
