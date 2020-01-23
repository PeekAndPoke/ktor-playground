package com.thebase.apps.cms.layouts

import com.thebase._sortme_.karsten.addAt
import com.thebase._sortme_.karsten.camelCaseDivide
import com.thebase._sortme_.karsten.removeAt
import com.thebase._sortme_.karsten.replaceAt
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.Form
import de.peekandpoke.ktorfx.formidable.button
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.formidable.withOptions
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.cms
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.modules.cms.domain.CmsLayout
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.id

data class LandingPageLayout(
    override val elements: List<CmsElement> = listOf()
) : CmsLayout {

    companion object : Polymorphic.Child {
        val Empty = LandingPageLayout()

        override val identifier = "landing-page"
    }

    inner class DeleteForm(vmb: ViewModelBuilder) : Form(vmb.path + ".delete") {
        val btn = button("delete")
    }

    inner class AddElementData(var element: String = "")

    inner class AddElementForm(vmb: ViewModelBuilder) : Form(vmb.path + ".add") {
        val data = AddElementData()

        val select = field(data::element).withOptions(
            vmb.call.cms.elements.map { (k, _) ->
                (k.qualifiedName ?: "n/a") to (k.simpleName ?: "").camelCaseDivide().untranslated()
            }
        )

        val before = button("before")

        val after = button("after")
    }

    inner class AddFirstElementForm(vmb: ViewModelBuilder) : Form(vmb.path + ".add") {
        val data = AddElementData()

        val select = field(data::element).withOptions(
            vmb.call.cms.elements.map { (k, _) ->
                (k.qualifiedName ?: "n/a") to (k.simpleName ?: "").camelCaseDivide().untranslated()
            }
        )
    }

    override fun withElements(elements: List<CmsElement>): LandingPageLayout = copy(elements = elements)

    override fun FlowContent.render(ctx: RenderCtx) {

        ui.basic.segment {
            id = "header"

            ui.container {
                ui.three.column.stackable.grid {

                    ui.left.aligned.column {
                        id = "main-menu"

                        ui.horizontal.list.blue.text {
                            ui.item {
                                a(href = "about") { +"About" }
                            }
                            ui.item {
                                a(href = "about") { +"Location" }
                            }
                            ui.item {
                                a(href = "about") { +"Partner" }
                            }
                            ui.item {
                                a(href = "about") { +"Jobs" }
                            }
                        }
                    }
                    ui.center.aligned.column {
                        ui.red.header.with("the-base-logo") {
                            +"THE BASE"
                        }
                    }
                    ui.right.aligned.column {
                        ui.blue.button {
                            +"Book"
                        }
                    }
                }
            }
        }

        div {
            id = "pull-down"
        }

        div(classes = "segment-stack") {

            elements.forEach {
                it.apply { render(ctx) }
            }
        }
    }

    override suspend fun editVm(vm: ViewModelBuilder, onChange: (CmsLayout) -> Nothing): View = when (elements.size) {
        0 -> editWithoutElements(vm, onChange)
        else -> editWithElements(vm, onChange)
    }

    private suspend fun editWithoutElements(vm: ViewModelBuilder, onChange: (CmsLayout) -> Nothing): View {

        val actions = createEditActions(onChange, 0)

        val addElement = AddFirstElementForm(vm)

        if (addElement.submit(vm.call)) {
            actions.addBefore(
                vm.call.cms.getElement(addElement.data.element)
            )
        }

        return vm.view {

            ui.header H3 {
                +"Add the first element to the page"
            }

            formidable(vm.call.i18n, addElement) {

                selectInput(addElement.select)

                submitButton("Add Element")
            }
        }
    }

    private suspend fun editWithElements(vm: ViewModelBuilder, onChange: (CmsLayout) -> Nothing): View {

        val children = elements.mapIndexed { idx, element ->

            val actions = createEditActions(onChange, idx)

            vm.child("$idx") { vmbOuter ->
                // actions
                val actionsVm = vmbOuter.actions(actions)

                // individual edit form for each element
                val editVm = vmbOuter.child("edit") { vmb -> element.editVm(vmb, actions) }

                vm.view {
                    ui.grid {
                        ui.twelve.wide.column {
                            editVm.render(this)
                        }
                        ui.four.wide.column {
                            actionsVm.render(this)
                        }
                    }
                }
            }
        }

        return vm.view {
            children.forEach { it.render(this) }
        }
    }

    private suspend fun ViewModelBuilder.actions(actions: CmsElement.EditActions): View = child("actions") { vmb ->

        val deleteElement = DeleteForm(vmb)

        if (deleteElement.submit(vmb.call)) {
            actions.delete()
        }

        val addElement = AddElementForm(vmb)

        if (addElement.submit(vmb.call)) {
            // Add an element before?
            if (addElement.before.isClicked) {
                actions.addBefore(
                    vmb.call.cms.getElement(addElement.data.element)
                )
            }

            // Add an element after?
            if (addElement.after.isClicked) {
                actions.addAfter(
                    vmb.call.cms.getElement(addElement.data.element)
                )
            }
        }

        view {
            ui.top.attached.blue.segment {

                formidable(vmb.call.i18n, deleteElement) {
                    confirmButton(deleteElement.btn, "Delete Element", "Really delete this Element?")
                }

                ui.divider {}

                formidable(vmb.call.i18n, addElement) {
                    selectInput(addElement.select, "Add element")

                    ui.two.buttons {
                        submitButton(addElement.before, "Add before")
                        submitButton(addElement.after, "Add after")
                    }
                }
            }
        }
    }

    private fun createEditActions(onChange: (CmsLayout) -> Nothing, idx: Int): CmsElement.EditActions {

        return object : CmsElement.EditActions {
            override fun modify(it: CmsElement): Nothing {
                onChange(
                    copy(elements = elements.replaceAt(idx, it))
                )
            }

            override fun delete(): Nothing {
                onChange(
                    copy(elements = elements.removeAt(idx))
                )
            }

            override fun addBefore(it: CmsElement): Nothing {
                onChange(
                    copy(elements = elements.addAt(idx, it))
                )
            }

            override fun addAfter(it: CmsElement): Nothing {
                onChange(
                    copy(elements = elements.addAt(idx + 1, it))
                )
            }
        }
    }
}
