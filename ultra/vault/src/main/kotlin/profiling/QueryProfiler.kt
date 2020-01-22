package de.peekandpoke.ultra.vault.profiling

interface QueryProfiler {

    class StopWatch {

        @Suppress("MemberVisibilityCanBePrivate")
        val entriesNs = mutableListOf<Long>()

        val totalNs get() = entriesNs.sum()

        val count get() = entriesNs.size

        operator fun <T> invoke(block: () -> T): T = measure(block)

        private fun <T> measure(block: () -> T): T {

            val start = System.nanoTime()

            val ret = block()

            entriesNs.add(System.nanoTime() - start)

            return ret
        }
    }

    class Entry(
        val connection: String,
        val queryLanguage: String,
        val query: String
    ) {
        var vars: Map<String, Any?>? = null

        val totalNs
            get() = measureQuery.totalNs +
                    measureIterator.totalNs +
                    measureSerializer.totalNs +
                    measureDeserializer.totalNs

        val measureQuery = StopWatch()
        val measureIterator = StopWatch()
        val measureSerializer = StopWatch()
        val measureDeserializer = StopWatch()
    }

    val entries: List<Entry>

    fun <R> profile(
        connection: String,
        queryLanguage: String,
        query: String,
        block: (Entry) -> R
    ): R
}

