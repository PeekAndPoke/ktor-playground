package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources

class InsightsBarWebResources(buster: CacheBuster) : WebResourceGroup(buster, {

    resourceCss("/assets/ktorfx/insights/bar.css")

    resourceJs("/assets/ktorfx/insights/bar.js")
})

val WebResources.insightsBar get() = get(InsightsBarWebResources::class)
