package de.peekandpoke.de.peekandpoke.modules.cms.domain

import kotlinx.html.FlowContent

interface CmsItem {
    fun FlowContent.render()
}
