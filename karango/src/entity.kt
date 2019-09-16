package de.peekandpoke.karango

import com.fasterxml.jackson.annotation.JsonInclude

data class Stored<T>(
    val _id: String,
    val _key: String,
    val _rev: String,
    val value: T
)

@Suppress("PropertyName")
interface Entity {
    @get:JsonInclude(JsonInclude.Include.NON_EMPTY)
    val _id: String?
    @get:JsonInclude(JsonInclude.Include.NON_EMPTY)
    val _key: String?
}

val Entity?.id: String
    get() = when {
        this === null -> ""
        this._id === null -> ""
        else -> this._id!!
    }

@Suppress("PropertyName")
interface WithRev {
    val _rev: String?
}

@Suppress("PropertyName")
interface Edge : Entity {
    val _from: String
    val _to: String
}
