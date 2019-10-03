package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.insights.gui.InsightsBarRenderer
import de.peekandpoke.ktorfx.insights.gui.insightsGui
import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import de.peekandpoke.ultra.polyglot.I18n
import io.ktor.html.Placeholder
import io.ktor.html.PlaceholderList
import io.ktor.html.each
import io.ktor.html.insert
import kotlinx.html.*

open class SimpleTemplateImpl(

    final override val tools: TemplateTools

) : SimpleTemplate {

    final override val t: I18n = tools.i18n
    final override val flashSession: FlashSession = tools.flashSession
    final override val webResources: WebResources = tools.webResources
    final override val insights: InsightsBarRenderer? = tools.insights

    private val flashSessionEntries = flashSession.pull()

    override var breadCrumbs: List<Any> = listOf()

    final override val pageTitle = Placeholder<HEAD>()
    final override val mainMenu = Placeholder<FlowContent>()
    final override val content = Placeholder<FlowContent>()

    final override val styles = PlaceholderList<HEAD, HEAD>()
    final override val scripts = PlaceholderList<FlowContent, FlowContent>()

    val insightsBar = Placeholder<FlowContent>()

    init {
        pageTitle {
            title { +"Default Template" }
        }

        styles {
            css(webResources.semanticUi)
        }

        scripts {
            js(webResources.semanticUi)
        }

        if (insights != null) {
            styles {
                css(webResources.insightsGui)
            }
            scripts {
                js(webResources.insightsGui)
            }

            insightsBar {
                insights.render(this)
            }
        }
    }

    override fun HTML.apply() {

        head {

            meta { charset = "utf-8" }

            insert(pageTitle)

            each(styles) { insert(it) }

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

            insert(insightsBar)

            each(scripts) { insert(it) }
        }
    }
}
