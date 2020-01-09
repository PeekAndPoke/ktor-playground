package de.peekandpoke

import de.peekandpoke.ktorfx.templating.TemplateTools
import de.peekandpoke.ktorfx.templating.semanticui.SemanticUiPlainTemplate
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import de.peekandpoke.module.cms.CmsPublic
import de.peekandpoke.resources.www

class WwwTemplate(

    tools: TemplateTools,

    private val cms: CmsPublic

) : SemanticUiPlainTemplate(tools) {

    init {

        styles {
            css(webResources.www)
        }

        scripts {
            js(webResources.www)
        }

        mainMenu {
        }
    }
}
