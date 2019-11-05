package de.peekandpoke.ultra.vault.profiling

class DefaultQueryProfiler : QueryProfiler {

    override val entries: MutableList<QueryProfiler.Entry> = mutableListOf()

    override fun <R> profile(
        connection: String,
        queryLanguage: String,
        query: String,
        block: (QueryProfiler.Entry) -> R
    ): R {

        val entry = QueryProfiler.Entry(
            connection = connection,
            queryLanguage = queryLanguage,
            query = query
        )

        entries.add(entry)

        return block(entry)
    }
}
