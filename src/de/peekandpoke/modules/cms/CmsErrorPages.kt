package de.peekandpoke.modules.cms

import de.peekandpoke.ktorfx.templating.SimpleTemplate
import io.ktor.application.ApplicationCall
import io.ktor.request.uri

interface CmsErrorPages {

    fun SimpleTemplate.error404(call: ApplicationCall)

    fun SimpleTemplate.error500(call: ApplicationCall, cause: Throwable)
}

class DefaultErrorPages : CmsErrorPages {

    override fun SimpleTemplate.error404(call: ApplicationCall) {
        content {
            +"The page ${call.request.uri} was not found."
        }
    }

    override fun SimpleTemplate.error500(call: ApplicationCall, cause: Throwable) {
        content {
            +"The page ${call.request.uri} caused an internal server error."
        }
    }
}
