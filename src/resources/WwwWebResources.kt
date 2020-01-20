package de.peekandpoke.resources

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources

val WebResources.www get() = get(WwwWebResources::class)

class WwwWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {

    resourceCss("/assets/www/semantic.css")
    resourceCss("/assets/css/www.css")

    webjarCss("/vendor/slick-carousel/slick/slick.css")
    webjarCss("/vendor/slick-carousel/slick/slick-theme.css")
    webjarJs("/vendor/slick-carousel/slick/slick.min.js")

    resourceJs("/assets/www/semantic.js")
    resourceJs("/assets/js/www.js")
})
