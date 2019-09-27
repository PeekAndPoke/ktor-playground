package de.peekandpoke.ktorfx.webresources

import de.peekandpoke.ultra.common.md5
import de.peekandpoke.ultra.kontainer.module
import java.time.Instant

val KtorFX_WebResources = module {

    // A default cache buster.
    instance(CacheBuster(Instant.now().toString().md5()))

    // Web resources service
    singleton(WebResources::class)
}
