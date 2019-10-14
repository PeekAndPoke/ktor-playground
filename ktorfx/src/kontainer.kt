package io.ultra.ktor_tools

import de.peekandpoke.ktorfx.broker.ktorFxBroker
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.common.ktorFxCommon
import de.peekandpoke.ktorfx.flashsession.ktorFxFlashSession
import de.peekandpoke.ktorfx.insights.ktorFxInsights
import de.peekandpoke.ktorfx.prismjs.ktorFxPrismJs
import de.peekandpoke.ktorfx.security.KtorFXSecurityConfig
import de.peekandpoke.ktorfx.security.ktorFxSecurity
import de.peekandpoke.ktorfx.semanticui.ktorFxSemanticUi
import de.peekandpoke.ktorfx.templating.ktorFxTemplating
import de.peekandpoke.ktorfx.webresources.ktorFxWebResources
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.polyglot.I18n
import de.peekandpoke.ultra.polyglot.NullI18n
import de.peekandpoke.ultra.vault.Database
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

// Kontainer module

data class KtorFXConfig(val security: KtorFXSecurityConfig)

fun KontainerBuilder.ktorFx(config: KtorFXConfig) = module(KtorFX, config)

val KtorFX = module { config: KtorFXConfig ->

    // I18n (can be overwritten by re-defining the instance)
    dynamic(I18n::class, NullI18n::class)

    ktorFxCommon()

    ktorFxBroker()
    ktorFxFlashSession()
    ktorFxInsights()
    ktorFxPrismJs()
    ktorFxSecurity(config.security)
    ktorFxSemanticUi()
    ktorFxTemplating()
    ktorFxWebResources()
}

// Shortcuts to common services

// TODO: remove this from there ... create a ktorfx:vault module
inline val ApplicationCall.database: Database get() = kontainer.get(Database::class)
inline val PipelineContext<Unit, ApplicationCall>.database: Database get() = call.database
