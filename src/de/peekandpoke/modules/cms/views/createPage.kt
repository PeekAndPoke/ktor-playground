package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.modules.cms.domain.CmsPageForm
import kotlinx.html.title

internal fun SimpleTemplate.createPage(form: CmsPageForm) {

    breadCrumbs = listOf(CmsMenu.PAGES)

    pageHead {
        title { +"CMS Create Page" }
    }

    content {

        ui.header.divided H1 { +"Create Page" }

        ui.header H2 {
            +"General settings"
        }

        formidable(t, form) {

            ui.two.fields {
                textInput(form.name, label = "Name")
                textInput(form.slug, label = "Slug")
            }

            ui.button Submit { +"Submit" }
        }
    }
}
