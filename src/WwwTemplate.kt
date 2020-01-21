package de.peekandpoke

import de.peekandpoke.ktorfx.templating.TemplateTools
import de.peekandpoke.ktorfx.templating.semanticui.SemanticUiPlainTemplate
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import de.peekandpoke.resources.www

class WwwTemplate(

    tools: TemplateTools

) : SemanticUiPlainTemplate(tools) {

    init {

        loadDefaultJQuery()

        styles {
            css(webResources.www)
        }

        scripts {
            js(webResources.www)
        }

        initInsights()

        mainMenu {
        }
    }
}
