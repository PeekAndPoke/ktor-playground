package de.peekandpoke.ktorfx.templating.semanticui

import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplateBase
import de.peekandpoke.ktorfx.templating.TemplateTools
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import io.ktor.html.each
import io.ktor.html.insert
import kotlinx.html.*
import kotlin.system.measureNanoTime

open class SemanticUiAdminTemplate(

    tools: TemplateTools

) : SimpleTemplateBase(tools) {

    init {
        pageTitle {
            title { +"Admin" }
        }

        styles {
            css(webResources.semanticUi)
        }

        scripts {
            js(webResources.semanticUi)
        }

        initInsights()
    }

    override fun HTML.apply() {

        val nanos = measureNanoTime {

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

                ui.sidebar.vertical.left.inverted.mainMenuBgColor().menu.visible.fixed {
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

        tools.insightsCollector?.record(nanos)
    }
}
