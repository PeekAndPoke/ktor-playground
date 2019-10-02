package io.ultra.ktor_tools

import de.peekandpoke.ktorfx.broker.KtorFX_Broker
import de.peekandpoke.ktorfx.common.KtorFX_Common
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.flashsession.KtorFX_FlashSession
import de.peekandpoke.ktorfx.insights.KtorFX_Insights
import de.peekandpoke.ktorfx.prismjs.KtorFX_PrismJs
import de.peekandpoke.ktorfx.semanticui.KtorFX_SemanticUi
import de.peekandpoke.ktorfx.templating.KtorFX_Templating
import de.peekandpoke.ktorfx.webresources.KtorFX_WebResources
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.polyglot.I18n
import de.peekandpoke.ultra.polyglot.NullI18n
import de.peekandpoke.ultra.vault.Database
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

// Kontainer module

val KtorFX = module {

    // I18n (can be overwritten by re-defining the instance)
    dynamic(I18n::class, NullI18n::class)

    module(KtorFX_Common)

    module(KtorFX_Broker)
    module(KtorFX_FlashSession)
    module(KtorFX_Insights)
    module(KtorFX_PrismJs)
    module(KtorFX_SemanticUi)
    module(KtorFX_Templating)
    module(KtorFX_WebResources)
}

// Shortcuts to common services

// TODO: remove this from there ... create a ktorfx:vault module
inline val ApplicationCall.database: Database get() = kontainer.get(Database::class)
inline val PipelineContext<Unit, ApplicationCall>.database: Database get() = call.database

// TODO: remove this from here ... where to put it ?
inline val ApplicationCall.i18n: I18n get() = kontainer.get(I18n::class)
inline val PipelineContext<Unit, ApplicationCall>.i18n: I18n get() = call.i18n

