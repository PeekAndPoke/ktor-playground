package de.peekandpoke.ktorfx.prismjs

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.kontainer.module

val KtorFX_PrismJs = module {
    singleton(PrismJsWebResources::class)
}

class PrismJsWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {
    webjarCss("/vendor/prismjs/prism.css")
    webjarCss("/vendor/prismjs/prism.css")
    webjarCss("/vendor/prismjs/plugins/toolbar/prism-toolbar.css")

    webjarJs("/vendor/prismjs/prism.js")
    webjarJs("/vendor/prismjs/plugins/toolbar/prism-toolbar.js")
    webjarJs("/vendor/prismjs/show-language/prism-show-language.js")
    webjarJs("/vendor/prismjs/components/prism-kotlin.js")
})

val WebResources.prismJs get() = get(PrismJsWebResources::class)
