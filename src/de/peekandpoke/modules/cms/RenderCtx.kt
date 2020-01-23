package de.peekandpoke.modules.cms

import io.ktor.application.ApplicationCall
import kotlinx.html.HTMLTag

data class RenderCtx(
    val cms: Cms,
    val call: ApplicationCall
) {

    fun HTMLTag.markdown(markdown: String) = cms.markdown.apply { render(markdown) }
}
