package de.peekandpoke.com.thebase.apps.www

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources

val WebResources.www get() = get(WwwWebResources::class)

class WwwWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {

    resourceCss("/assets/www/semantic/semantic.css")
    resourceJs("/assets/www/semantic/semantic.js")

    resourceCss("/assets/www/www.css")
    resourceCss("/assets/www/thebase/thebase.css")
    resourceJs("/assets/www/www.js")

    webjarCss("/vendor/slick-carousel/slick/slick.css")
    webjarCss("/vendor/slick-carousel/slick/slick-theme.css")
    webjarJs("/vendor/slick-carousel/slick/slick.min.js")
})
