package de.peekandpoke.ktorfx.insights.gui

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources

class InsightsGuiWebResourceGroup(buster: CacheBuster) : WebResourceGroup(buster, {

    resourceCss("/assets/ktorfx/insights/insights.css")

    resourceJs("/assets/ktorfx/insights/bar.js")
})

val WebResources.insightsGui get() = get(InsightsGuiWebResourceGroup::class)
