package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector

class PhaseCollector(override val data: Data = Data()) : InsightsCollector {

    class Data {
        val phases = mutableListOf<Pair<String, Long>>()
    }

    override val name = "PhaseCollector"

    fun record(phase: String, time: Long) {
        data.phases.add(phase to time)
    }
}
