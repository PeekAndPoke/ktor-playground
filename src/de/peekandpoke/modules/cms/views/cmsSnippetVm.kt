package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.flashsession.success
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.viewModel
import de.peekandpoke.modules.cms.cms
import de.peekandpoke.modules.cms.cmsAdminRoutes
import de.peekandpoke.modules.cms.db.cmsSnippets
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.modules.cms.domain.CmsSnippet
import de.peekandpoke.modules.cms.domain.CmsSnippetChangeElementForm
import de.peekandpoke.modules.cms.domain.CmsSnippetForm
import de.peekandpoke.ultra.vault.Stored
import io.ktor.application.ApplicationCall
import io.ultra.ktor_tools.database

suspend fun ApplicationCall.vm(storedSnippet: Stored<CmsSnippet>) = viewModel { vmb ->

    val form = CmsSnippetForm.of(storedSnippet)

    if (form.submit(this)) {
        if (form.isModified) {
            val saved = database.cmsSnippets.save(form.result)
            flashSession.success("Snippet '${saved.value.name}' was saved")
        }

        vmb.redirect(vmb.call.cmsAdminRoutes.snippets)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val changeElementForm = CmsSnippetChangeElementForm(vmb.call.cms, storedSnippet)

    if (changeElementForm.submit(this)) {
        val saved = database.cmsSnippets.save(storedSnippet) { snippet ->
            val newElement = cms.getElement(changeElementForm.data.element)
            snippet.copy(element = newElement)
        }

        flashSession.success("Element of '${saved.value.name}' was changed to '${changeElementForm.data.element}'")

        vmb.reload()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val actions = object : CmsElement.EditActions {
        override fun modify(it: CmsElement): Nothing {
            val saved = database.cmsSnippets.save(storedSnippet) { snippet ->
                snippet.copy(element = it)
            }

            flashSession.success("Snippet '${saved.value.name}' was saved")

            vmb.reload()
        }
    }

    val elementVm = vmb.child("element") {
        storedSnippet.value.element.editVm(it, actions)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    vmb.view {

        ui.header.divided H1 {
            +"Edit Snippet ${form.name.value} (${form.result._key})"
        }

        ui.two.column.grid {

            ui.column {
                formidable(i18n, form) {

                    ui.header H2 { +"General Settings" }

                    ui.top.attached.segment {
                        textInput(form.name, label = "Name")
                    }

                    ui.bottom.attached.segment {
                        submitButton("Save Snippet")
                    }
                }
            }

            ui.column {
                formidable(i18n, changeElementForm) {

                    ui.header H2 { +"Select Element" }

                    ui.top.attached.red.segment {
                        selectInput(it.element, label = "Element")
                    }

                    ui.bottom.attached.segment {
                        submitButton("Change Element")
                    }
                }
            }
        }

        ui.header H2 {
            +"Element"
        }

        elementVm.render(this)
    }
}
