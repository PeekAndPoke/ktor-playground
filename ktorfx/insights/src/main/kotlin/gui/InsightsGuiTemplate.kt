package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.insights.InsightsMapper
import de.peekandpoke.ktorfx.prismjs.Language
import de.peekandpoke.ktorfx.prismjs.prism
import de.peekandpoke.ktorfx.prismjs.prismJs
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import io.ktor.html.PlaceholderList
import io.ktor.html.Template
import io.ktor.html.each
import io.ktor.html.insert
import kotlinx.html.*

class InsightsGuiTemplate(
    private val webResources: WebResources,
    private val mapper: InsightsMapper,
    private val guiData: InsightsGuiData
) : Template<HTML> {

    val styles = PlaceholderList<HEAD, HEAD>()
    val scripts = PlaceholderList<FlowContent, FlowContent>()

    val menuPlaceholders = PlaceholderList<FlowContent, FlowContent>()

    val contentPlaceholders = PlaceholderList<FlowContent, FlowContent>()

    init {
        styles {
            css(webResources.semanticUi)
            css(webResources.prismJs)
            css(webResources.insightsGui)
        }

        scripts {
            js(webResources.semanticUi)
            js(webResources.prismJs)
            js(webResources.insightsGui)
        }

        menuPlaceholders {
            ui.item.active {
                attributes["data-key"] = "overview"

                icon.chart_bar_outline()
                +"Overview"
            }
        }

        contentPlaceholders {
            div {
                attributes["data-key"] = "overview"

                ui.basic.segment {
                    ui.header H3 {
                        +"Overview"
                    }
                }
            }
        }

        guiData.collectors.forEach {
            it.renderDetails(this)
        }
    }

    override fun HTML.apply() {

        head {
            each(styles) { insert(it) }
        }

        body {

            ui.green.inverted.attached.segment {

                ui.big.horizontal.divided.list {
                    ui.inverted.header.item { +"KtorFX" }

                    ui.item { +guiData.statusCode.toString() }
                    ui.item { +guiData.requestMethod }
                    ui.item { +guiData.requestUrl }
                    ui.item { +guiData.responseTimeMs }
                }
            }

            ui.attached.inverted.labeled.tiny.icon.menu {
                id = "menu"

                each(menuPlaceholders) {
                    insert(it)
                }
            }

            div {
                id = "content"

                each(contentPlaceholders) {
                    insert(it)
                }
            }

            each(scripts) { insert(it) }
        }
    }

    fun FlowContent.json(data: Any) {

        prism(Language.Json) {
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data)
        }
    }
}
