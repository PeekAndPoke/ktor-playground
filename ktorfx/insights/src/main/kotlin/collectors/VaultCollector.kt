package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.vault.profiling.QueryProfiler
import io.ktor.application.ApplicationCall

class VaultCollector(private val profiler: QueryProfiler) : InsightsCollector {

    data class Data(val entries: List<QueryProfiler.Entry>) : InsightsCollectorData {

        private val totalTimeNs: Long by lazy {
            entries.map { it.timeNs }.sum()
        }

        override fun renderBar(template: InsightsBarTemplate) {

            with(template) {

                left {
                    ui.item {
                        icon.database()

                        val time = "%.2f".format(totalTimeNs / 1_000_000.0)

                        +"${entries.size} in $time ms"
                    }
                }
            }
        }
    }

    override val name = "Vault"


    override fun finish(call: ApplicationCall) = Data(profiler.entries)
}
