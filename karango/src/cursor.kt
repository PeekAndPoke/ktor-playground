package de.peekandpoke.karango

import com.arangodb.ArangoCursor
import com.arangodb.ArangoIterator
import com.arangodb.entity.CursorEntity
import de.peekandpoke.ultra.slumber.Codec
import de.peekandpoke.ultra.vault.profiling.QueryProfiler
import kotlin.reflect.KType

interface Cursor<T> : Iterable<T> {
    val query: TypedQuery<T>
    val stats: CursorEntity.Stats
    val count: Int
    val timeMs: Double
}

class CursorImpl<T>(
    private val arangoCursor: ArangoCursor<*>,
    override val query: TypedQuery<T>,
    codec: Codec,
    private val profiler: QueryProfiler.Entry
) : Cursor<T> {

    private val iterator = profiler.measureIterator {
        It<T>(arangoCursor, query.ret.innerType().type, codec, profiler)
    }

    class It<X>(
        private val inner: ArangoIterator<*>,
        private val type: KType,
        private val codec: Codec,
        private val profiler: QueryProfiler.Entry
    ) : Iterator<X> {

        override fun hasNext(): Boolean = profiler.measureIterator { inner.hasNext() }

        override fun next(): X {

            val next = profiler.measureIterator { inner.next() }

            return profiler.measureDeserializer {
                @Suppress("UNCHECKED_CAST")
                return@measureDeserializer codec.awake(type, next) as X
            }
        }
    }

    override val count: Int = arangoCursor.count

    override val stats: CursorEntity.Stats get() = arangoCursor.stats

    override fun iterator() = iterator

    override val timeMs get() = profiler.totalNs / 1_000_000.0
}
