package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.modules.cms.domain.CmsSnippetForm
import kotlinx.html.title

internal fun SimpleTemplate.createSnippet(form: CmsSnippetForm) {

    breadCrumbs = listOf(CmsMenu.SNIPPETS)

    pageHead {
        title { +"CMS Create Snippet" }
    }

    content {

        ui.header.divided H1 { +"Create Snippet" }

        ui.header H2 {
            +"General settings"
        }

        formidable(t, form) {

            ui.two.fields {
                textInput(form.name, label = "Name")
            }

            ui.button Submit { +"Submit" }
        }
    }
}
