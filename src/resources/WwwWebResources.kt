package de.peekandpoke.resources

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources

val WebResources.www get() = get(WwwWebResources::class)

class WwwWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {

    resourceCss("/assets/css/www.css")

})
