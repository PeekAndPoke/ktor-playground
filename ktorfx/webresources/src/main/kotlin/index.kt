package de.peekandpoke.ktorfx.webresources

import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ultra.common.md5
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext
import java.time.Instant

fun KontainerBuilder.ktorFxWebResources() = module(KtorFX_WebResources)

val KtorFX_WebResources = module {

    // A default cache buster.
    instance(CacheBuster(Instant.now().toString().md5()))

    // Web resources service
    singleton(WebResources::class)
}

inline val ApplicationCall.cacheBuster: CacheBuster get() = kontainer.get(CacheBuster::class)
inline val PipelineContext<Unit, ApplicationCall>.cacheBuster: CacheBuster get() = call.cacheBuster

inline val ApplicationCall.webResources: WebResources get() = kontainer.get(WebResources::class)
inline val PipelineContext<Unit, ApplicationCall>.webResources: WebResources get() = call.webResources
