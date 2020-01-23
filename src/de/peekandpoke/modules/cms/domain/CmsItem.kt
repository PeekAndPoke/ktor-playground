package de.peekandpoke.modules.cms.domain

import de.peekandpoke.modules.cms.RenderCtx
import kotlinx.html.FlowContent

interface CmsItem {
    fun FlowContent.render(ctx: RenderCtx)
}
