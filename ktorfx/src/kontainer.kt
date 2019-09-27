package io.ultra.ktor_tools

import de.peekandpoke.ktorfx.broker.KtorFX_Broker
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.prismjs.KtorFX_PrismJs
import de.peekandpoke.ktorfx.semanticui.KtorFX_SemanticUi
import de.peekandpoke.ktorfx.webresources.KtorFX_WebResources
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.DefaultEntityCache
import de.peekandpoke.ultra.vault.EntityCache
import de.peekandpoke.ultra.vault.SharedRepoClassLookup
import de.peekandpoke.ultra.vault.hooks.AnonymousUserRecordProvider
import de.peekandpoke.ultra.vault.hooks.TimestampedOnSaveHook
import de.peekandpoke.ultra.vault.hooks.UserRecordOnSaveHook
import de.peekandpoke.ultra.vault.hooks.UserRecordProvider
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext
import io.ultra.polyglot.I18n

// Kontainer module

val KtorFX = module {

    // database ///////////////////////////////////////////////////////////////////////////////////////////////

    singleton(SharedRepoClassLookup::class)
    singleton(Database::class)
    dynamic(EntityCache::class) { DefaultEntityCache() }

    singleton(TimestampedOnSaveHook::class)
    singleton(UserRecordOnSaveHook::class)
    dynamic(UserRecordProvider::class) { AnonymousUserRecordProvider() }


    // I18n (can be overwritten by re-defining the instance)
    instance(I18n.empty())


    module(KtorFX_Broker)
    module(KtorFX_PrismJs)
    module(KtorFX_SemanticUi)
    module(KtorFX_WebResources)
}

// Shortcuts to common services

// TODO: remove this from there ... create a ktorfx:vault module
inline val ApplicationCall.database: Database get() = kontainer.get(Database::class)
inline val PipelineContext<Unit, ApplicationCall>.database: Database get() = call.database

// TODO: remove this from here ... where to put it ?
inline val ApplicationCall.i18n: I18n get() = kontainer.get(I18n::class)
inline val PipelineContext<Unit, ApplicationCall>.i18n: I18n get() = call.i18n

