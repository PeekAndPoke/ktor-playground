package de.peekandpoke.karango

import com.arangodb.ArangoCursor
import com.arangodb.ArangoIterator
import com.arangodb.entity.CursorEntity
import com.fasterxml.jackson.databind.ObjectMapper
import de.peekandpoke.karango.aql.TypeRef

interface Cursor<T> : Iterable<T> {
    val query: TypedQuery<T>
    val timeMs: Long
    val stats: CursorEntity.Stats
    val count: Int
}

class CursorImpl<T>(
    private val arangoCursor: ArangoCursor<*>,
    override val query: TypedQuery<T>,
    override val timeMs: Long,
    mapper: ObjectMapper
) : Cursor<T> {

    private val iterator = It(arangoCursor, query.ret.innerType(), mapper)

    class It<X>(
        private val inner: ArangoIterator<*>,
        private val type: TypeRef<X>,
        private val mapper: ObjectMapper
    ) : Iterator<X> {

        override fun hasNext(): Boolean = inner.hasNext()

        override fun next(): X = mapper.convertValue(inner.next(), type)
    }

    override val count: Int = arangoCursor.count

    override val stats: CursorEntity.Stats get() = arangoCursor.stats

    override fun iterator() = iterator
}
