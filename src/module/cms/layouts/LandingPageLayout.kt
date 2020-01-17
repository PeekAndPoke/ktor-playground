package de.peekandpoke.module.cms.layouts

import de.peekandpoke._sortme_.karsten.LoremIpsum
import de.peekandpoke._sortme_.karsten.replaceAt
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.module.cms.CmsLayout
import de.peekandpoke.module.cms.domain.Image
import de.peekandpoke.module.cms.elements.*
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p

data class LandingPageLayout(
    override val elements: List<CmsElement> = listOf(
        HeroElement(
            SemanticColor.blue,
            "The cities that never sleep",
            LoremIpsum.words(15),
            LoremIpsum.imageUrls(3, 500, 600).map {
                Image(it, LoremIpsum.words(10, 5))
            }
        ),
        TextElement(
            SemanticColor.red,
            "",
            LoremIpsum.words(50)
        ),
        TextElement(
            SemanticColor.none,
            "Unser Wertesystem und was du bei uns erwarten kannst",
            ""
        ),
        TextImageElement(
            SemanticColor.none,
            TextImageElement.Layout.ImageRight,
            "Sit Amet Consect",
            LoremIpsum.words(25, 5),
            listOf()
        ),
        TextImageElement(
            SemanticColor.none,
            TextImageElement.Layout.ImageLeft,
            "Sit Amet Consect",
            LoremIpsum.words(25, 5),
            listOf()
        ),
        GalleryElement(
            SemanticColor.none,
            GalleryElement.Layout.SideBySideSlider,
            "",
            "",
            listOf()
        ),
        DividerElement(
            SemanticColor.violet,
            DividerElement.Height.three
        ),
        TextImageElement(
            SemanticColor.none,
            TextImageElement.Layout.ImageBottom,
            "It's all in, so let us stay!",
            LoremIpsum(30),
            listOf()
        ),
        GalleryElement(
            SemanticColor.none,
            GalleryElement.Layout.ThreeColumns,
            "Extra, Not ordinary",
            "",
            listOf()
        ),
        GalleryElement(
            SemanticColor.olive,
            GalleryElement.Layout.ThreeColumns,
            "Now Bases are coming",
            "",
            listOf()
        ),
        ListElement(
            SemanticColor.none,
            ListElement.Layout.TwoColumns,
            "Become a partner",
            "",
            listOf()
        ),
        GalleryElement(
            SemanticColor.blue,
            GalleryElement.Layout.FiveColumns,
            "Working together",
            "",
            listOf()
        ),
        FooterElement(
            SemanticColor.red
        )
    )
) : CmsLayout {

    companion object : Polymorphic.Child {
        val Empty = LandingPageLayout()

        override val identifier = "landing-page"
    }

    override fun withElements(elements: List<CmsElement>): LandingPageLayout = copy(elements = elements)

    override fun FlowContent.render() {

        ui.basic.segment {
            id = "header"

            ui.padded.three.column.stackable.grid {

                ui.column {
                    p {
                        +"Menu"
                    }
                }
                ui.column.center.aligned {
                    ui.red.header.with("the-base-logo") {
                        +"THE BASE"
                    }
                }
                ui.column.right.aligned {
                    ui.red.button {
                        +"Book Now"
                    }
                }
            }
        }

        div {
            id = "pull-down"
        }

        div(classes = "segment-stack") {

            elements.forEach {
                it.apply { render() }
            }
        }
    }

    override suspend fun editVm(vm: ViewModelBuilder, onChange: (CmsLayout) -> Unit): View {

        val children = elements.mapIndexed { idx, element ->
            vm.child("element-$idx") { vmb ->

                // Create a VM for each element. Propagate changes to the outside world.
                element.editVm(vmb) { changed ->
                    onChange(
                        copy(elements = elements.replaceAt(idx, changed))
                    )
                }
            }
        }

        return vm.view {
            children.forEach { it.render(this) }
        }
    }
}
