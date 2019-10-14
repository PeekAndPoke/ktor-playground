package de.peekandpoke.ultra.vault.profiling

class NullQueryProfiler : QueryProfiler {

    override val entries: List<QueryProfiler.Entry> get() = listOf()

    override fun <R> add(connection: String, queryLanguage: String, query: String, vars: Map<String, Any>?, block: () -> R): R {
        return block()
    }

}
