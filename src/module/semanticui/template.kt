package de.peekandpoke.module.semanticui

import de.peekandpoke.PrismJsWebResources
import de.peekandpoke.SemanticUiWebResources
import io.ktor.application.ApplicationCall
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import io.ultra.ktor_tools.resources.WebResources
import io.ultra.ktor_tools.resources.css
import io.ultra.ktor_tools.resources.js
import io.ultra.ktor_tools.semanticui.ui
import io.ultra.ktor_tools.webResources
import kotlinx.html.*

internal class Template constructor(private val routes: SemanticUiRoutes, call: ApplicationCall) : Template<HTML> {

    private val webResources: WebResources = call.webResources

    val pageTitle = Placeholder<HEAD>()
    val content = Placeholder<FlowContent>()

    init {
        pageTitle {
            title { +"Semantic UI Showcase" }
        }
    }

    override fun HTML.apply() {
        head {

            meta { charset = "utf-8" }

            insert(pageTitle)

            css(webResources[SemanticUiWebResources::class])
            css(webResources[PrismJsWebResources::class])

            style("text/css") {
                unsafe {
                    +"""
                        
                        .pusher.padded.right {
                            padding-right: 260px;
                        }
                        
                    """.trimIndent()
                }
            }
        }

        body {

            ui.sidebar.vertical.left.inverted.menu.visible.fixed {

                ui.item A { href = routes.index; +"Semantic UI" }

                ui.item A { href = routes.playground; +"Playground" }

                ui.item A { href = routes.buttons; +"Buttons" }

                ui.item A { href = routes.icons; +"Icons" }
            }

            ui.pusher.padded.right {

                ui.padded.basic.segment.shrink {
                    insert(content)
                }
            }

            js(webResources[SemanticUiWebResources::class])
            js(webResources[PrismJsWebResources::class])
        }
    }
}
