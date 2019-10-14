package de.peekandpoke.karango

/**
 * Remove result
 */
data class RemoveResult(val count: Int, val query: TypedQuery<*>) {

    companion object {
        fun from(e: KarangoQueryException) = RemoveResult(0, e.query)

        fun from(cursor: Cursor<*>) = RemoveResult(cursor.count, cursor.query)
    }
}
