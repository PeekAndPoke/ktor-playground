package de.peekandpoke.ktorfx.templating.semanticui

import de.peekandpoke.ktorfx.templating.SimpleTemplateBase
import de.peekandpoke.ktorfx.templating.TemplateTools
import io.ktor.html.each
import io.ktor.html.insert
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.meta
import kotlin.system.measureNanoTime

open class SemanticUiPlainTemplate(

    tools: TemplateTools

) : SimpleTemplateBase(tools) {

    override fun HTML.apply() {

        val nanos = measureNanoTime {

            head {

                meta { charset = "utf-8" }

                each(pageHead) { insert(it) }

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
