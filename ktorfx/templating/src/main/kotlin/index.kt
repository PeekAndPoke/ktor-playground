package de.peekandpoke.ktorfx.templating

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.templating.semanticui.SemanticUiPlainTemplate
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.HttpStatusCode
import io.ktor.util.pipeline.PipelineContext
import kotlin.reflect.KClass

fun KontainerBuilder.ktorFxTemplating() = module(KtorFX_Templating)

/**
 * Template kontainer module
 */
val KtorFX_Templating = module {

    /**
     * Overwrite this one with an application specific implementation of [SimpleTemplate]
     */
    prototype(SimpleTemplate::class, SemanticUiPlainTemplate::class)

    /**
     * Override this one with an application specific implementation of [TemplateTools]
     */
    prototype(TemplateTools::class, TemplateToolsImpl::class)

    /**
     * Insights collector for rendering
     */
    dynamic(TemplateInsightsCollector::class)
}

/**
 * Shorthand for getting a new instance of the default template
 */
inline val ApplicationCall.defaultTemplate: SimpleTemplate get() = kontainer.get(SimpleTemplate::class)

/**
 * Shorthand for getting a new instance of the default template
 */
inline val PipelineContext<Unit, ApplicationCall>.defaultTemplate: SimpleTemplate get() = call.defaultTemplate

/**
 * Responds with the default template
 */
suspend fun ApplicationCall.respond(
    status: HttpStatusCode = HttpStatusCode.OK,
    body: SimpleTemplate.() -> Unit
) {
    respondHtmlTemplate(defaultTemplate, status, body)
}

/**
 * Responds with the default template
 */
suspend fun PipelineContext<Unit, ApplicationCall>.respond(
    status: HttpStatusCode = HttpStatusCode.OK,
    body: SimpleTemplate.() -> Unit
) {
    call.respond(status, body)
}

@JvmName("respond_withHint")
suspend fun <T : SimpleTemplate> PipelineContext<Unit, ApplicationCall>.respond(
    status: HttpStatusCode = HttpStatusCode.OK,
    body: T.() -> Unit
) {
    @Suppress("UNCHECKED_CAST")
    call.respondHtmlTemplate(defaultTemplate as T, status, body)
}

/**
 * Responds with the given template [T]
 */
suspend fun <T : SimpleTemplate> PipelineContext<Unit, ApplicationCall>.respond(
    template: KClass<T>,
    status: HttpStatusCode = HttpStatusCode.OK,
    body: T.() -> Unit
) {
    call.respondHtmlTemplate(kontainer.get(template), status, body)
}
