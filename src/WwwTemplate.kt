package de.peekandpoke

import de.peekandpoke.ktorfx.templating.SimpleTemplateImpl
import de.peekandpoke.ktorfx.templating.TemplateTools
import de.peekandpoke.module.cms.CmsPublic

class WwwTemplate(

    tools: TemplateTools,

    private val cms: CmsPublic

) : SimpleTemplateImpl(tools) {

    init {

        scripts {
        }

        mainMenu {
        }
    }
}
