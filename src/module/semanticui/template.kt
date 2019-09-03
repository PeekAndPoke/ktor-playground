package de.peekandpoke.module.semanticui

import io.ktor.application.ApplicationCall
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.resources.css
import io.ultra.ktor_tools.resources.iocTranslations
import io.ultra.ktor_tools.resources.iocWebResources
import io.ultra.ktor_tools.resources.js
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.*

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal class Template constructor(private val linkTo: SemanticUiModule.LinkTo, call: ApplicationCall) : Template<HTML> {

    val t = call.iocTranslations
    private val webResources = call.iocWebResources

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

            css(webResources["semantic"])
            css(webResources["prism"])

            style(
                "text/css", """
                .pusher.padded.right {
                    padding-right: 260px;
                }
            """.trimIndent()
            )
        }

        body {

            ui.sidebar.vertical.left.inverted.menu.visible.fixed {

                a(classes = "item", href = linkTo.index()) { +"Semantic UI" }

                a(classes = "item", href = linkTo.playground()) { +"Playground" }

                a(classes = "item", href = linkTo.buttons()) { +"Buttons" }
            }

            ui.pusher.padded.right {

                ui.padded.basic.segment.shrink {
                    insert(content)
                }
            }

            js(webResources["semantic"])
            js(webResources["prism"])
        }
    }
}
