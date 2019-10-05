package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import io.ktor.html.Template
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.head

class InsightsDetailsTemplate(private val webResources: WebResources, private val guiData: InsightsGuiData) : Template<HTML> {
    override fun HTML.apply() {
        head {

            css(webResources.semanticUi)
        }

        body {

            ui.sidebar.vertical.left.inverted.black.menu.visible.fixed {
                ui.green.inverted.attached.segment {
                    ui.header H3 {
                        icon.eye()
                        +"KtorFX Insights"
                    }
                }
            }

            ui.pusher.padded.right {
                ui.green.inverted.attached.segment {
                    ui.big.horizontal.divided.list {
                        ui.item { +guiData.statusCode.toString() }
                        ui.item { +guiData.requestMethod }
                        ui.item { +guiData.requestUrl }
                        ui.item { +guiData.responseTimeMs }
                    }
                }
            }

            js(webResources.semanticUi)
        }
    }
}
