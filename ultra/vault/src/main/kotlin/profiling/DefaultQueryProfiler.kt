package de.peekandpoke.ultra.vault.profiling

import kotlin.system.measureNanoTime

class DefaultQueryProfiler : QueryProfiler {

    override val entries: MutableList<QueryProfiler.Entry> = mutableListOf()

    override fun <R> add(connection: String, query: String, block: () -> R): R {

        var result: R? = null

        val time = measureNanoTime {
            result = block()
        }

        entries.add(QueryProfiler.Entry(connection, query, time))

        return result!!
    }
}
