package de.peekandpoke.ultra.vault.profiling

interface QueryProfiler {

    data class Entry(val connection: String, val query: String, val timeNs: Long)

    val entries: List<Entry>

    fun <R> add(connection: String, query: String, block: () -> R): R
}

