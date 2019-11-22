package de.peekandpoke.module.cms.views

import de.peekandpoke.ktorfx.formidable.semanticui.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.cms.forms.CmsPageForm
import kotlinx.html.h1
import kotlinx.html.title

internal fun SimpleTemplate.editPage(create: Boolean, form: CmsPageForm) {

    breadCrumbs = listOf(CmsMenu.PAGES)

    pageTitle {
        title { +"CMS Edit Page" }
    }

    content {

        h1 {
            when (create) {
                true -> +"Create Page"
                false -> +"Edit ${form.id.value}"
            }
        }

        formidable(t, form) {

            textInput(form.id, label = "Name")

            textInput(form.slug, label = "Slug")

            ui.button Submit { +"Submit" }
        }
    }
}
