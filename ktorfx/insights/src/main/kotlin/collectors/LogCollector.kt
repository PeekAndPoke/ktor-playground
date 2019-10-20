package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiTemplate
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ultra.logging.ConsoleAppender
import de.peekandpoke.ultra.logging.LogAppender
import de.peekandpoke.ultra.logging.LogLevel
import io.ktor.application.ApplicationCall
import kotlinx.html.pre
import kotlinx.html.style
import kotlinx.html.title
import java.time.ZonedDateTime

class LogCollector : InsightsCollector {

    class Appender : LogAppender {

        internal val entries = mutableListOf<String>()

        override suspend fun append(ts: ZonedDateTime, level: LogLevel, message: String, loggerName: String) {
            entries.add(
                ConsoleAppender.format(ts, level, message, loggerName)
            )
        }
    }

    data class Data(
        val entries: List<String>
    ) : InsightsCollectorData {

        override fun renderBar(template: InsightsBarTemplate) = with(template) {
            left {
                ui.item {
                    title = "Logs"

                    icon.list()

                    +"${entries.size}"
                }
            }
        }

        override fun renderDetails(template: InsightsGuiTemplate) = with(template) {

            menu {
                icon.list()
                +"Logs"
            }

            content {

                entries.forEach {
                    pre {
                        style = "background-color: #F8F8F8; padding: 10px; border-radius: 5px; border: 1px solid #CCC;"
                        +it
                    }
                }
            }
        }
    }

    override fun finish(call: ApplicationCall): InsightsCollectorData {
        return call.kontainer.use(Appender::class) { Data(entries) } ?: Data(listOf())
    }
}
