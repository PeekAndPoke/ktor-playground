package de.peekandpoke.modules.cms

import kotlinx.html.HTMLTag
import kotlinx.html.unsafe
import org.commonmark.node.*
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.AttributeProvider
import org.commonmark.renderer.html.HtmlRenderer

/**
 * Markdown to html converter
 *
 * Library See https://github.com/atlassian/commonmark-java
 *
 * Tutorial See https://commonmark.org/help/tutorial/
 */
class SemanticMarkdown {

    private val parser = Parser.builder().build()

    private val renderer = HtmlRenderer.builder()
        .attributeProviderFactory {
            SemanticAttributeProvider()
        }
        .build()

    private val markdownCache = mutableMapOf<String, String>()

    fun HTMLTag.render(markdown: String) {

        val rendered = markdownCache.getOrPut(markdown) {
            // TODO: strip any html tags from the incoming markdown
            renderer.render(
                parser.parse(markdown)
            )
        }

        unsafe {
            +rendered
        }
    }
}

class SemanticAttributeProvider : AttributeProvider {
    override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {

        val classes = when (node) {
            is Heading -> "ui header"

            is Paragraph -> "ui text"

            is BulletList -> "ui list"

            is ListItem -> "ui item"

            else -> null
        }

        if (classes != null) {
            attributes["classes"] = classes
        }
    }
}
