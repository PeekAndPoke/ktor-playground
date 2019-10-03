package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ultra.kontainer.Kontainer

class KontainerCollector(override var data: List<String> = listOf()) : InsightsCollector {

    override val name = "Kontainer"

    fun record(kontainer: Kontainer) {
        data = kontainer.dump().split("\n")
    }
}
