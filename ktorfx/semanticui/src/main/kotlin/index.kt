package de.peekandpoke.ktorfx.semanticui

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module

fun KontainerBuilder.ktorFxSemanticUi() = module(KtorFX_SemanticUi)

val KtorFX_SemanticUi = module {
    singleton(SemanticUiWebResources::class)
    singleton(JQueryWebResource::class)
}

class SemanticUiWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {

    webjarCss("/vendor/Semantic-UI/semantic.css")

    webjarJs("/vendor/Semantic-UI/semantic.js")
})

val WebResources.semanticUi get() = get(SemanticUiWebResources::class)

class JQueryWebResource(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {
    webjarJs("/vendor/jquery/dist/jquery.min.js")
})

val WebResources.jQuery get() = get(JQueryWebResource::class)
