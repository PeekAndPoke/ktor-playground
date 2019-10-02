package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector

class PhaseCollector : InsightsCollector {

    class Data {
        val phases = mutableListOf<Pair<String, Long>>()
    }

    override val name = "PhaseCollector"

    override val data = Data()

    fun record(phase: String, time: Long) {
        data.phases.add(phase to time)
    }
}
