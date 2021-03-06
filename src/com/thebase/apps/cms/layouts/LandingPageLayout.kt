package com.thebase.apps.cms.layouts

import com.thebase._sortme_.karsten.*
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
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.polyglot.untranslated
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.*

@Mutable
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

    inner class MoveElementForm(vmb: ViewModelBuilder) : Form(vmb.path + ".move") {
        val moveUp = button("move-up")
        val moveDown = button("move-down")
    }

    inner class AddElementData(var element: String = "")

    inner class AddElementForm(vmb: ViewModelBuilder) : Form(vmb.path + ".add") {
        val data = AddElementData()

        val select = field(data::element).withOptions(
            "---".untranslated(),
            vmb.call.cms.elements.map { (k, _) ->
                (k.qualifiedName ?: "n/a") to (k.simpleName ?: "n/a").camelCaseDivide().untranslated()
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

            element to vm.child("$idx") { vmbOuter ->
                // actions
                val actionsVm = vmbOuter.actions(actions, element)

                // individual edit form for each element
                val editVm = vmbOuter.child("edit") { vmb ->
                    element.editVm(vmb, actions)
                }

                vmbOuter.view {
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

            ui.header H2 { +"Page Elements" }

            ui.segment {
                ol {
                    children.forEachIndexed { idx, pair ->
                        li {
                            a(href = "#element.$idx") { +pair.first.elementName }
                        }
                    }
                }
            }

            children.forEachIndexed { idx, pair ->

                a { attributes["name"] = "element.$idx" }

                pair.second.render(this)
            }
        }
    }

    private suspend fun ViewModelBuilder.actions(actions: CmsElement.EditActions, element: CmsElement): View = child("actions") { vmb ->

        val moveElement = MoveElementForm(vmb)

        if (moveElement.submit(vmb.call)) {
            // Move the element up
            if (moveElement.moveUp.isClicked) {
                actions.moveUp(element)
            }
            // Move the element down
            if (moveElement.moveDown.isClicked) {
                actions.moveDown(element)
            }
        }

        val addElement = AddElementForm(vmb)

        if (addElement.submit(vmb.call)) {
            // Add an element before?
            if (addElement.before.isClicked) {
                actions.addBefore(vmb.call.cms.getElement(addElement.data.element))
            }
            // Add an element after?
            if (addElement.after.isClicked) {
                actions.addAfter(vmb.call.cms.getElement(addElement.data.element))
            }
        }

        val deleteElement = DeleteForm(vmb)

        if (deleteElement.submit(vmb.call)) {
            actions.delete()
        }

        view {
            ui.top.attached.blue.segment {

                formidable(vmb.call.i18n, moveElement) {

                    ui.two.buttons {
                        submitButton(moveElement.moveUp, "Move up")
                        submitButton(moveElement.moveDown, "Move down")
                    }
                }

                ui.divider {}

                formidable(vmb.call.i18n, addElement) {

                    selectInput(addElement.select, "Add element")

                    ui.two.buttons {
                        submitButton(addElement.before, "Add before")
                        submitButton(addElement.after, "Add after")
                    }
                }

                ui.divider {}

                formidable(vmb.call.i18n, deleteElement) {
                    confirmButton(deleteElement.btn, "Delete Element", "Really delete this Element?") { red.inverted }
                }
            }
        }
    }

    private fun createEditActions(onChange: (CmsLayout) -> Nothing, idx: Int): CmsElement.EditActions {

        return object : CmsElement.EditActions {

            override val index: Int get() = idx

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

            override fun moveUp(it: CmsElement): Nothing {
                val pos = elements.indexOf(it)

                onChange(
                    copy(elements = elements.swapAt(pos, pos - 1))
                )
            }

            override fun moveDown(it: CmsElement): Nothing {
                val pos = elements.indexOf(it)

                onChange(
                    copy(elements = elements.swapAt(pos, pos + 1))
                )
            }
        }
    }
}
