package com.thebase.apps.cms.elements

import com.thebase.apps.cms.elements.common.ElementStyle
import com.thebase.apps.cms.elements.common.partial
import com.thebase.apps.cms.elements.common.styling
import de.peekandpoke.ktorfx.common.i18n
import de.peekandpoke.ktorfx.formidable.MutatorForm
import de.peekandpoke.ktorfx.formidable.field
import de.peekandpoke.ktorfx.formidable.list
import de.peekandpoke.ktorfx.formidable.rendering.formidable
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.modules.cms.Cms
import de.peekandpoke.modules.cms.RenderCtx
import de.peekandpoke.modules.cms.cms
import de.peekandpoke.modules.cms.domain.CmsElement
import de.peekandpoke.modules.cms.domain.Link
import de.peekandpoke.modules.cms.domain.LinkForm
import de.peekandpoke.modules.cms.domain.mutator
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.id

@Mutable
data class HeaderElement(
    val styling: ElementStyle = ElementStyle.default,
    val name: String = "",
    val items: List<MenuEntry> = listOf()
) : CmsElement {

    companion object : Polymorphic.Child {
        override val identifier = "header-element"
    }

    override val elementName: String get() = "Header '$name'"

    data class MenuEntry(
        val link: Link = Link("", ""),
        val subs: List<Link> = listOf()
    )

    class MenuEntryForm(cms: Cms, it: HeaderElement_MenuEntryMutator) : MutatorForm<MenuEntry, HeaderElement_MenuEntryMutator>(it) {

        val link = subForm(LinkForm(cms, target.link))

        val subs = list(target::subs, { Link("", "").mutator() }) { item ->
            subForm(
                LinkForm(cms, item.value)
            )
        }
    }

    inner class VmForm(vm: ViewModelBuilder) : MutatorForm<HeaderElement, HeaderElementMutator>(mutator { _: HeaderElement -> Unit }, vm.path) {

        val styling = styling(target.styling)

        val name = field(target::name)

        val items = list(target::items, { MenuEntry().mutator() }) {
            subForm(
                MenuEntryForm(vm.call.cms, it.value)
            )
        }
    }

    override fun FlowContent.render(ctx: RenderCtx) {

        div(classes = "header-element") {

            ui.basic.segment.given(styling.backgroundColor.isSet) { inverted.color(styling.backgroundColor) }.then {

                ui.container {
                    ui.three.column.stackable.grid {

                        ui.left.aligned.column {
                            id = "main-menu"

                            ui.horizontal.list.color(styling.textColor).text {

                                items.forEach { menuItem ->
                                    ui.item {
                                        a(href = menuItem.link.url) { +menuItem.link.title }

                                        if (menuItem.subs.isNotEmpty()) {
                                            ui.list {
                                                menuItem.subs.forEach { subItem ->
                                                    ui.item {
                                                        a(href = subItem.url) { +subItem.title }
                                                    }
                                                }
                                            }
                                        }
                                    }
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
        }

        div {
            id = "pull-down"
        }
    }

    override suspend fun editVm(vm: ViewModelBuilder, actions: CmsElement.EditActions): View {

        val form = VmForm(vm)

        if (form.submit(vm.call)) {
            if (form.isModified) {
                actions.modify(form.result)
            }
        }

        return vm.view {

            formidable(vm.call.i18n, form, { action = "#element.${actions.index}" }) {

                ui.attached.segment {

                    ui.header.given(form.isSubmitted() && form.isNotValid()) { red } H3 {
                        icon.heading()
                        +"Header '$name'"
                    }

                    ui.three.fields {
                        partial(this, form.styling)

                        textInput(form.name, "Header name")
                    }

                    ui.divider {}

                    listFieldAsGrid(form.items) { item ->
                        textInput(item.link.title, "Title")
                        textInput(item.link.url, "Uri")

                        listFieldAsGrid(item.subs) { sub ->
                            textInput(sub.title, "Title")
                            textInput(sub.url, "Uri")
                        }
                    }
                }

                ui.bottom.attached.segment {
                    submitButton("Submit")
                }
            }
        }
    }
}
