package de.peekandpoke.module.cms.views

import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import de.peekandpoke.ktorfx.webresources.webResources
import de.peekandpoke.module.cms.CmsAdminRoutes
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.i18n
import kotlinx.html.*

enum class MenuEntries {
    HOME,
    PAGES
}

internal class Template constructor(
    val routes: CmsAdminRoutes,
    pipeline: PipelineContext<Unit, ApplicationCall>
) : Template<HTML> {

    private val call = pipeline.call
    val t = call.i18n
    private val webResources = call.webResources
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

            css(webResources.semanticUi)

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

            ui.sidebar.vertical.left.inverted.violet.menu.visible.fixed {

                ui.item.given(activeMenu == MenuEntries.HOME) { active } A { href = routes.index; +"Overview" }

                ui.item.given(activeMenu == MenuEntries.PAGES) { active } A { href = routes.pages; +"Pages" }
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

            js(webResources.semanticUi)
        }
    }
}
