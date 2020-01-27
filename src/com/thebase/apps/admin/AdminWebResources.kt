package com.thebase.apps.admin

import de.peekandpoke.ktorfx.webresources.CacheBuster
import de.peekandpoke.ktorfx.webresources.WebResourceGroup
import de.peekandpoke.ktorfx.webresources.WebResources

val WebResources.admin get() = get(AdminWebResources::class)

class AdminWebResources(cacheBuster: CacheBuster) : WebResourceGroup(cacheBuster, {

    // SimpleMDE
    webjarCss("/vendor/simplemde-markdown-editor/dist/simplemde.min.css")
    webjarJs("/vendor/simplemde-markdown-editor/dist/simplemde.min.js")

    // Formidable
    resourceJs("/assets/formidable/formidable.js")

    // Admin
    resourceCss("/assets/admin/admin.css")
    resourceJs("/assets/admin/admin.js")
})
