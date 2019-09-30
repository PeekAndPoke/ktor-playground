package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import io.ktor.html.Placeholder
import io.ktor.html.insert
import io.ultra.polyglot.I18n
import kotlinx.html.*

open class SimpleTemplateImpl(

    final override val t: I18n,
    final override val flashSession: FlashSession,
    final override val webResources: WebResources

) : SimpleTemplate {

    override var breadCrumbs: List<Any> = listOf()

    final override val pageTitle = Placeholder<HEAD>()
    final override val mainMenu = Placeholder<FlowContent>()
    final override val content = Placeholder<FlowContent>()

    final override val styles = Placeholder<HEAD>()
    final override val scripts = Placeholder<FlowContent>()

    private val flashSessionEntries = flashSession.pull()

    init {
        pageTitle {
            title { +"Default Template" }
        }
    }

    override fun HTML.apply() {

        head {

            meta { charset = "utf-8" }

            insert(pageTitle)

            css(webResources.semanticUi)
            insert(styles)

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
                insert(mainMenu)
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
            insert(scripts)
        }
    }
}
