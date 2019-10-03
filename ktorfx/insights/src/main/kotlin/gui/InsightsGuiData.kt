package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.insights.InsightsCollector

data class InsightsGuiData(
    val ts: Long,
    val date: String,
    val collectors: List<InsightsCollector>
)
