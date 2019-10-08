package de.peekandpoke.ultra.vault.profiling

interface QueryProfiler {

    data class Entry(
        val connection: String,
        val queryLanguage: String,
        val query: String,
        val vars: Map<String, Any>?,
        val timeNs: Long
    )

    val entries: List<Entry>

    fun <R> add(
        connection: String,
        queryLanguage: String,
        query: String,
        vars: Map<String, Any>?,
        block: () -> R
    ): R
}

