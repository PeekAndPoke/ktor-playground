package de.peekandpoke

import de.peekandpoke.ktorfx.templating.TemplateTools
import de.peekandpoke.ktorfx.templating.semanticui.SemanticUiPlainTemplate
import de.peekandpoke.module.cms.CmsPublic

class WwwTemplate(

    tools: TemplateTools,

    private val cms: CmsPublic

) : SemanticUiPlainTemplate(tools) {

    init {

        scripts {
        }

        mainMenu {
        }
    }
}
