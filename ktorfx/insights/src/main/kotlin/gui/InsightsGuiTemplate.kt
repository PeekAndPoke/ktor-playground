package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.broker.TypedRoute
import de.peekandpoke.ktorfx.broker.TypedRouteRenderer
import de.peekandpoke.ktorfx.insights.InsightsMapper
import de.peekandpoke.ktorfx.insights.collectors.RuntimeCollector
import de.peekandpoke.ktorfx.insights.collectors.VaultCollector
import de.peekandpoke.ktorfx.prismjs.Language
import de.peekandpoke.ktorfx.prismjs.prism
import de.peekandpoke.ktorfx.prismjs.prismJs
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.jQuery
import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import io.ktor.html.*
import kotlinx.html.*

class InsightsGuiTemplate(
    private val routes: InsightsGuiRoutes,
    private val routesRenderer: TypedRouteRenderer,
    private val webResources: WebResources,
    private val mapper: InsightsMapper
) : Template<HTML> {

    val styles = PlaceholderList<HEAD, HEAD>()
    val scripts = PlaceholderList<FlowContent, FlowContent>()

    val contentPlaceholder = Placeholder<BODY>()

    val menuPlaceholders = PlaceholderList<FlowContent, FlowContent>()

    val contentPlaceholders = PlaceholderList<FlowContent, FlowContent>()

    /**
     * Renders the url of a bound typed route
     */
    val <T : Any> TypedRoute.Bound<T>.url get() = routesRenderer.render(this)

    init {
        styles {
            css(webResources.jQuery)
            css(webResources.semanticUi)
            css(webResources.prismJs)
            css(webResources.insightsGui)
        }

        scripts {
            js(webResources.jQuery)
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
    }

    override fun HTML.apply() {

        head {
            each(styles) { insert(it) }
        }

        body {
            insert(contentPlaceholder)

            each(scripts) { insert(it) }
        }
    }

    fun render(guiData: InsightsGuiData) {
        contentPlaceholders {
            div {
                attributes["data-key"] = "overview"

                ui.basic.segment {

                    guiData.use(VaultCollector.Data::class) {
                        ui.segment {
                            ui.header H3 { +"Database" }
                            stats()
                        }
                    }

                    guiData.use(RuntimeCollector.Data::class) {
                        ui.segment {
                            ui.header H3 { +"Runtime" }
                            stats()
                        }
                    }
                }
            }
        }

        guiData.collectors.forEach {
            it.renderDetails(this)
        }

        contentPlaceholder {
            ui.green.inverted.attached.segment {

                ui.big.horizontal.divided.list {
                    ui.inverted.header.item { +"KtorFX" }

                    ui.item { +guiData.statusCode.toString() }
                    ui.item { +guiData.requestMethod }
                    ui.item { +guiData.requestUrl }
                    ui.item { +guiData.responseTimeMs }
                    ui.item { +guiData.ts.toString() }
                }
            }

            ui.attached.inverted.labeled.tiny.icon.menu {
                id = "menu"

                each(menuPlaceholders) {
                    insert(it)
                }

                if (guiData.previousFile != null) {
                    a(href = routes.details(guiData.previousFile).url) {
                        ui.item {
                            icon.arrow_left()
                        }
                    }
                }

                if (guiData.nextFile !== null) {
                    a(href = routes.details(guiData.nextFile).url) {
                        ui.item {
                            icon.arrow_right()
                        }
                    }
                }
            }

            div {
                id = "content"

                each(contentPlaceholders) {
                    insert(it)
                }
            }
        }
    }

    fun FlowContent.json(data: Any?) {

        prism(Language.Json) {
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data)
        }
    }
}
