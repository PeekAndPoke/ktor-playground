package de.peekandpoke.module.cms.layouts

import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsLayout
import kotlinx.html.FlowContent

class LandingPageLayout : CmsLayout {

    companion object {
        val Empty = LandingPageLayout()
    }

    override fun FlowContent.render() {

        ui.container {
            ui.header H1 {
                +"Landing page"
            }
        }
    }
}
