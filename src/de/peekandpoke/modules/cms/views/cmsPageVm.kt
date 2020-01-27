package de.peekandpoke.modules.cms.views

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.flashsession.success
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.viewModel
import de.peekandpoke.modules.cms.cms
import de.peekandpoke.modules.cms.cmsAdminRoutes
import de.peekandpoke.modules.cms.db.cmsPages
import de.peekandpoke.modules.cms.domain.CmsPage
import de.peekandpoke.modules.cms.domain.CmsPageChangeLayoutForm
import de.peekandpoke.modules.cms.domain.CmsPageForm
import de.peekandpoke.modules.cms.domain.DeleteForm
import de.peekandpoke.ultra.vault.Stored
import io.ktor.application.ApplicationCall
import io.ultra.ktor_tools.database


suspend fun ApplicationCall.vm(storedPage: Stored<CmsPage>) = viewModel { vmb ->

    val form = CmsPageForm.of(storedPage)

    if (form.submit(this)) {
        if (form.isModified) {
            val saved = database.cmsPages.save(form.result)
            flashSession.success("Page '${saved.value.name}' was saved")
        }

        vmb.redirect(vmb.call.cmsAdminRoutes.pages)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val changeLayoutForm = CmsPageChangeLayoutForm(cms, storedPage)

    if (changeLayoutForm.submit(this)) {

        val saved = database.cmsPages.save(storedPage) { page ->

            val newLayout = cms
                .getLayout(changeLayoutForm.data.layout)
                .withElements(storedPage.value.layout.elements)

            page.copy(layout = newLayout)
        }

        flashSession.success("Layout of '${saved.value.name}' was changed to '${changeLayoutForm.data.layout}'")

        vmb.reload()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val deleteForm = DeleteForm(vmb.path + ".delete")

    if (deleteForm.submit(vmb.call)) {

        database.cmsPages.remove(storedPage)

        flashSession.success("The page '${storedPage.value.name}' was deleted")

        vmb.redirect(vmb.call.cmsAdminRoutes.pages)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val layoutView = vmb.child("layout") {

        storedPage.value.layout.editVm(it) { changed ->

            val saved = database.cmsPages.save(storedPage) { page -> page.copy(layout = changed) }

            flashSession.success("Changes of page '${saved.value.name}' where saved")

            it.reload()
        }
    }

    return@viewModel vmb.view {

        ui.header.divided H1 {
            +"Edit Page ${form.name.value} (${form.result._key})"
        }

        ui.grid {
            ui.eight.wide.column {
                formidable(i18n, form) {

                    ui.header H2 { +"General settings" }

                    ui.top.attached.segment {
                        ui.two.fields {
                            textInput(form.name, label = "Name")
                            textInput(form.slug, label = "Slug")
                        }
                    }

                    ui.bottom.attached.segment {
                        submitButton("Save Page")
                    }
                }
            }

            ui.four.wide.column {
                formidable(i18n, changeLayoutForm) {

                    ui.header H2 { +"Select Layout" }

                    ui.top.attached.red.segment {
                        ui.fields {
                            selectInput(it.layout, label = "Layout")
                        }
                    }

                    ui.bottom.attached.segment {
                        submitButton("Change Layout")
                    }
                }
            }

            ui.four.wide.column {

                formidable(i18n, changeLayoutForm) {

                    ui.header H2 { +"Delete Page" }

                    ui.top.attached.red.segment {
                        confirmButton("Delete Page", "Really delete the page '${storedPage.value.name}'") { red.inverted }
                    }
                }
            }
        }

        layoutView.render(this)
    }
}
