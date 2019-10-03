package de.peekandpoke.ktorfx.insights

import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate

interface InsightsCollector {
    val name: String
    val data: Any?

    fun renderBar(template: InsightsBarTemplate) {}
}
