package de.peekandpoke.resources

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources

val WebResources.admin get() = get(AdminWebResources::class)

class AdminWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {

    resourceCss("/assets/css/admin.css")

    resourceJs("/assets/formidable/formidable.js")
})
