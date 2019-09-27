package io.ultra.ktor_tools

import de.peekandpoke.ktorfx.semanticui.KtorFX_SemanticUi
import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.KtorFX_WebResources
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.kontainer.Kontainer
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
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext
import io.ultra.ktor_tools.typedroutes.IncomingConverter
import io.ultra.ktor_tools.typedroutes.IncomingVaultConverter
import io.ultra.ktor_tools.typedroutes.OutgoingConverter
import io.ultra.ktor_tools.typedroutes.OutgoingVaultConverter
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

    // data conversion for routing ////////////////////////////////////////////////////////////////////////////

    singleton(IncomingConverter::class)
    singleton(IncomingVaultConverter::class)

    singleton(OutgoingConverter::class)
    singleton(OutgoingVaultConverter::class)

    // resources //////////////////////////////////////////////////////////////////////////////////////////////

    // I18n (can be overwritten by re-defining the instance)
    instance(I18n.empty())

    module(KtorFX_SemanticUi)
    module(KtorFX_WebResources)
}

// Registering the kontainer on call attributes

val KontainerKey = AttributeKey<Kontainer>("kontainer")

inline val ApplicationCall.kontainer: Kontainer get() = attributes[KontainerKey]

fun Attributes.provide(value: Kontainer) = put(KontainerKey, value)

// Shortcuts to common services

inline val ApplicationCall.incomingConverter: IncomingConverter get() = kontainer.get(IncomingConverter::class)
inline val PipelineContext<Unit, ApplicationCall>.incomingConverter: IncomingConverter get() = call.incomingConverter

inline val ApplicationCall.database: Database get() = kontainer.get(Database::class)
inline val PipelineContext<Unit, ApplicationCall>.database: Database get() = call.database

inline val ApplicationCall.i18n: I18n get() = kontainer.get(I18n::class)
inline val PipelineContext<Unit, ApplicationCall>.i18n: I18n get() = call.i18n

inline val ApplicationCall.cacheBuster: CacheBuster get() = kontainer.get(CacheBuster::class)
inline val PipelineContext<Unit, ApplicationCall>.cacheBuster: CacheBuster get() = call.cacheBuster

inline val ApplicationCall.webResources: WebResources get() = kontainer.get(WebResources::class)
inline val PipelineContext<Unit, ApplicationCall>.webResources: WebResources get() = call.webResources
