package de.peekandpoke.ultra.vault.profiling

class NullQueryProfiler : QueryProfiler {

    override val entries: List<QueryProfiler.Entry> get() = listOf()

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

        return block(entry)
    }

}
