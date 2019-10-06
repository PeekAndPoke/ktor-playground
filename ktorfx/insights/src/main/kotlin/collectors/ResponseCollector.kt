package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.InsightsCollectorData
import de.peekandpoke.ktorfx.insights.gui.InsightsGuiTemplate
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.util.toMap

class ResponseCollector : InsightsCollector {

    data class Data(
        val status: HttpStatusCode?,
        val headers: Map<String, List<String>>
    ) : InsightsCollectorData {

        override fun renderDetails(template: InsightsGuiTemplate) = with(template) {

            menu {
                icon.cloud_download()
                +"Response"
            }

            content {
                ui.header H3 {
                    +"Response"
                }

                json(this@Data)
            }
        }
    }

    override fun finish(call: ApplicationCall) = Data(
        call.response.status(),
        call.response.headers.allValues().toMap()
    )
}
