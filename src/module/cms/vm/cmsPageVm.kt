package de.peekandpoke.module.cms.vm

import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.flashsession.flashSession
import de.peekandpoke.ktorfx.flashsession.success
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.viewModel
import de.peekandpoke.module.cms.*
import de.peekandpoke.module.cms.forms.CmsPageChangeLayoutForm
import de.peekandpoke.module.cms.forms.CmsPageForm
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
        val saved = database.cmsPages.save(storedPage) {
            it.mutate {
                layout += cms.getLayout(changeLayoutForm.result.layout)
            }
        }

        flashSession.success("Layout of '${saved.value.name}' was changed to '${changeLayoutForm.result.layout}'")

        vmb.reload()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val layoutView = vmb.child("layout") {

        storedPage.value.layout.editVm(it) { changed ->

            val saved = database.cmsPages.save(storedPage) { page ->
                page.mutate {
                    layout += changed
                }
            }

            flashSession.success("Changes of page '${saved.value.name}' where saved")

            it.reload()
        }
    }

    return@viewModel vmb.view {

        ui.header.divided H1 {
            +"Edit Page ${form.id.value} (${form.result._key})"
        }

        ui.header H2 { +"General settings" }

        ui.segment {

            formidable(i18n, form) {

                ui.two.fields {
                    textInput(form.id, label = "Name")
                    textInput(form.slug, label = "Slug")
                }

                ui.button Submit { +"Submit" }
            }
        }

        ui.header H2 { +"Select Layout" }

        ui.segment {
            formidable(i18n, changeLayoutForm) {

                ui.two.fields {
                    selectInput(it.layout, label = "Layout")
                }

                ui.button Submit { +"Change layout" }
            }
        }

        ui.header H2 { +"Page Elements" }

        layoutView.render(this)
    }
}
