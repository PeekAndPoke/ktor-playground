package de.peekandpoke.ktorfx.insights.collectors

import de.peekandpoke.ktorfx.insights.InsightsCollector
import de.peekandpoke.ktorfx.insights.gui.InsightsBarTemplate
import de.peekandpoke.ktorfx.semanticui.ui
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.util.toMap

class ResponseCollector(override var data: Data? = null) : InsightsCollector {

    data class Data(
        val status: HttpStatusCode?,
        val headers: Map<String, List<String>>
    )

    override val name = "Response"

    fun record(call: ApplicationCall) {
        data = Data(
            call.response.status(),
            call.response.headers.allValues().toMap()
        )
    }

    override fun renderBar(template: InsightsBarTemplate) {

        data?.status?.let { status ->

            template.status {

                when {
                    status.isSuccess() -> ui.green.label {
                        +status.toString()
                    }

                    else -> ui.red.label {
                        +status.toString()
                    }
                }
            }
        }
    }
}
