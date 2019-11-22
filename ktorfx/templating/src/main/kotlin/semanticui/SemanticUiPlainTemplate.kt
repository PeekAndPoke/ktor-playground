package de.peekandpoke.ktorfx.templating.semanticui

import de.peekandpoke.ktorfx.semanticui.semanticUi
import de.peekandpoke.ktorfx.templating.SimpleTemplateBase
import de.peekandpoke.ktorfx.templating.TemplateTools
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import io.ktor.html.each
import io.ktor.html.insert
import kotlinx.html.*
import kotlin.system.measureNanoTime

open class SemanticUiPlainTemplate(

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
            }

            body {

                insert(content)

                insert(insightsBar)

                each(scripts) { insert(it) }
            }
        }

        tools.insightsCollector?.record(nanos)
    }
}
