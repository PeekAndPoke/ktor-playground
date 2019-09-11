package de.peekandpoke.module.cms.views

import de.peekandpoke.module.cms.CmsPage
import de.peekandpoke.module.cms.forms.PageForm
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.bootstrap.textArea
import io.ultra.ktor_tools.bootstrap.textInput
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.FormMethod
import kotlinx.html.form
import kotlinx.html.h1

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.editPage(page: CmsPage, form: PageForm) {

    activeMenu = MenuEntries.PAGES

    content {

        h1 {
            when (page._id) {
                null -> +"Create Page"

                else -> +"Edit ${form.name.value}"
            }
        }

        form(classes = "ui form", method = FormMethod.post) {

            textInput(t, form.name, label = "Name")

            textInput(t, form.slug, label = "Slug")

            textArea(t, form.markup, label = "Markup")

            ui.button Submit {
                +"Submit"
            }
        }
    }
}
