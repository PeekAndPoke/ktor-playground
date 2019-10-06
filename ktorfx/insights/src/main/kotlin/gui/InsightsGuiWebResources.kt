package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources

class InsightsGuiWebResources(buster: CacheBuster) : WebResourceGroup(buster, {

    resourceCss("/assets/ktorfx/insights/gui.css")

    resourceJs("/assets/ktorfx/insights/gui.js")
})

val WebResources.insightsGui get() = get(InsightsGuiWebResources::class)
