package de.peekandpoke.module.cms.views

import de.peekandpoke.module.cms.CmsAdminModule
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.flashSession
import io.ultra.ktor_tools.resources.css
import io.ultra.ktor_tools.resources.iocTranslations
import io.ultra.ktor_tools.resources.iocWebResources
import io.ultra.ktor_tools.resources.js
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.*

enum class MenuEntries {
    HOME,
    PAGES
}

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal class Template constructor(
    val linkTo: CmsAdminModule.LinkTo,
    pipeline: PipelineContext<Unit, ApplicationCall>
) : Template<HTML> {

    private val call = pipeline.call
    val t = call.iocTranslations
    private val webResources = call.iocWebResources
    private val flashSessionEntries = pipeline.flashSession.pull()

    val pageTitle = Placeholder<HEAD>()
    val content = Placeholder<FlowContent>()

    var activeMenu = MenuEntries.HOME

    init {
        pageTitle {
            title { +"Mini CMS" }
        }
    }

    override fun HTML.apply() {

        head {

            meta { charset = "utf-8" }

            insert(pageTitle)

            css(webResources["semantic"])
            css(webResources["prism"])

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

                ui.item.given(activeMenu == MenuEntries.HOME) { active } A { href = linkTo.index(); +"Overview" }

                ui.item.given(activeMenu == MenuEntries.PAGES) { active } A { href = linkTo.pages(); +"Pages" }
            }

            ui.pusher.padded.right {

                flashSessionEntries.takeIf { it.isNotEmpty() }?.let { entries ->
                    ui.padded.basic.segment {
                        entries.forEach { entry ->
                            ui.message.with(entry.type) {
                                +entry.message
                            }
                        }
                    }
                }

                ui.padded.basic.segment {
                    insert(content)
                }
            }

            js(webResources["semantic"])
            js(webResources["prism"])
        }
    }
}
