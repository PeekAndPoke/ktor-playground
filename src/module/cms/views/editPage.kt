package de.peekandpoke.module.cms.views

import de.peekandpoke.ktorfx.formidable.semanticui.textArea
import de.peekandpoke.ktorfx.formidable.semanticui.textInput
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.cms.forms.CmsPageForm
import kotlinx.html.FormMethod
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
                false -> +"Edit ${form.name.value}"
            }
        }

        ui.form Form {
            method = FormMethod.post

            textInput(t, form.name, label = "Name")

            textInput(t, form.slug, label = "Slug")

            textArea(t, form.markup, label = "Markup")

            ui.button Submit { +"Submit" }
        }
    }
}
