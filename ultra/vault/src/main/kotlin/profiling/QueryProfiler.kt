package de.peekandpoke.ultra.vault.profiling

interface QueryProfiler {

    data class Entry(
        val connection: String,
        val queryLanguage: String,
        val query: String,
        val vars: Any?,
        val timeNs: Long
    )

    val entries: List<Entry>

    fun <R> add(
        connection: String,
        queryLanguage: String,
        query: String,
        vars: Any?,
        block: () -> R
    ): R
}

