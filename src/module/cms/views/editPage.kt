package de.peekandpoke.module.cms.views

import de.peekandpoke.ktorfx.formidable.semanticui.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.cms.forms.CmsPageChangeLayoutForm
import de.peekandpoke.module.cms.forms.CmsPageForm
import kotlinx.html.title

internal fun SimpleTemplate.editPage(create: Boolean, form: CmsPageForm, changeLayoutForm: CmsPageChangeLayoutForm? = null) {

    breadCrumbs = listOf(CmsMenu.PAGES)

    pageTitle {
        title { +"CMS Edit Page" }
    }

    content {

        ui.header.divided H1 {
            when (create) {
                true -> +"Create Page"
                false -> +"Edit Page ${form.id.value} (${form.result._key})"
            }
        }

        ui.header H2 {
            +"General settings"
        }

        formidable(t, form) {

            ui.two.fields {
                textInput(form.id, label = "Name")
                textInput(form.slug, label = "Slug")
            }

            ui.button Submit { +"Submit" }
        }

        if (changeLayoutForm != null) {

            ui.header H2 {
                +"Select Layout"
            }

            formidable(t, changeLayoutForm) {

                ui.two.fields {
                    selectInput(it.layout, label = "Layout")
                }

                ui.button Submit { +"Change" }
            }
        }
    }
}
