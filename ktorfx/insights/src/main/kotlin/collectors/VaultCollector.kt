package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiTemplate
import de.peekandpoke.ktorfx.prismjs.prism
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.vault.profiling.QueryProfiler
import io.ktor.application.ApplicationCall
import kotlinx.html.FlowContent
import kotlinx.html.title

class VaultCollector(private val profiler: QueryProfiler) : InsightsCollector {

    data class Data(val entries: List<QueryProfiler.Entry>) : InsightsCollectorData {

        private val totalTimeNs: Long by lazy {
            entries.map { it.totalNs }.sum()
        }

        private val totalSerializerNs by lazy {
            entries.map { it.measureSerializer.totalNs }.sum()
        }

        private val totalQueryNs by lazy {
            entries.map { it.measureQuery.totalNs }.sum()
        }

        private val totalIteratorNs by lazy {
            entries.map { it.measureIterator.totalNs }.sum()
        }

        private val totalDeserializerNs by lazy {
            entries.map { it.measureDeserializer.totalNs }.sum()
        }

        override fun renderBar(template: InsightsBarTemplate) = with(template) {

            left {

                ui.item {
                    title = "Database | Total: ${totalTimeNs.formatMs()} | " +
                            "Serializer: ${totalSerializerNs.formatMs()} | " +
                            "Query: ${totalQueryNs.formatMs()} | " +
                            "Iterator: ${totalIteratorNs.formatMs()} | " +
                            "Deserializer: ${totalDeserializerNs.formatMs()}"

                    icon.database()

                    +"${entries.size} in ${totalTimeNs.formatMs()}"
                }
            }
        }

        override fun renderDetails(template: InsightsGuiTemplate) = with(template) {

            menu {
                icon.database()
                +"Database"
            }

            content {

                stats()

                entries.forEachIndexed { idx, it ->
                    ui.segment {
                        ui.header H5 {
                            +"Query #${idx + 1} took ${it.totalNs.formatMs()} - ${it.connection}"
                        }

                        ui.horizontal.segments {
                            ui.segment {
                                +"Serializer: ${it.measureSerializer.totalNs.formatMs()} (${it.measureSerializer.count}x)"
                            }
                            ui.segment {
                                +"Query: ${it.measureQuery.totalNs.formatMs()} (${it.measureQuery.count}x)"
                            }
                            ui.segment {
                                +"Iterator: ${it.measureIterator.totalNs.formatMs()} (${it.measureIterator.count}x)"
                            }
                            ui.segment {
                                +"Deserializer: ${it.measureDeserializer.totalNs.formatMs()} (${it.measureDeserializer.count}x)"
                            }
                        }

                        prism(it.queryLanguage) { it.query }

                        json(it.vars)
                    }
                }

                ui.segment {
                    json(this@Data)
                }
            }
        }

        fun FlowContent.stats() {
            ui.horizontal.segments {
                ui.center.aligned.segment {
                    ui.header { +"Queries" }
                    +entries.size.toString()
                }

                ui.center.aligned.segment {
                    ui.header { +"Total" }
                    +totalTimeNs.formatMs()
                }

                ui.center.aligned.segment {
                    ui.header { +"Serializer" }
                    +totalSerializerNs.formatMs()
                }

                ui.center.aligned.segment {
                    ui.header { +"Query" }
                    +totalQueryNs.formatMs()
                }

                ui.center.aligned.segment {
                    ui.header { +"Iterator" }
                    +totalIteratorNs.formatMs()
                }

                ui.center.aligned.segment {
                    ui.header { +"Deserializer" }
                    +totalDeserializerNs.formatMs()
                }
            }
        }
    }

    override fun finish(call: ApplicationCall) = Data(profiler.entries)
}
