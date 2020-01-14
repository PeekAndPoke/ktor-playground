package de.peekandpoke.jointhebase.cms.layouts

import de.peekandpoke._sortme_.karsten.LoremIpsum
import de.peekandpoke._sortme_.karsten.replaceAt
import de.peekandpoke.jointhebase.cms.elements.*
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.vm.View
import de.peekandpoke.ktorfx.templating.vm.ViewModelBuilder
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.module.cms.CmsLayout
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
            LoremIpsum.imageUrls(3, 500, 600)
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
            LoremIpsum.imageUrls(1, 600, 400)
        ),
        TextImageElement(
            SemanticColor.none,
            TextImageElement.Layout.ImageLeft,
            "Sit Amet Consect",
            LoremIpsum.words(25, 5),
            LoremIpsum.imageUrls(3, 600, 400)
        ),
        GalleryElement(
            SemanticColor.none,
            GalleryElement.Layout.SideBySideSlider,
            "",
            listOf(
                GalleryElement.Item(
                    "Dine together 1",
                    LoremIpsum.words(25, 5),
                    LoremIpsum.imageUrl(300, 200)
                ),
                GalleryElement.Item(
                    "Dine together 2",
                    LoremIpsum.words(25, 5),
                    LoremIpsum.imageUrl(300, 200)
                ),
                GalleryElement.Item(
                    "Dine together 3",
                    LoremIpsum.words(25, 5),
                    LoremIpsum.imageUrl(300, 200)
                ),
                GalleryElement.Item(
                    "Dine together 4",
                    LoremIpsum.words(25, 5),
                    LoremIpsum.imageUrl(300, 200)
                ),
                GalleryElement.Item(
                    "Dine together 5",
                    LoremIpsum.words(25, 5),
                    LoremIpsum.imageUrl(300, 200)
                ),
                GalleryElement.Item(
                    "Dine together 6",
                    LoremIpsum.words(25, 5),
                    LoremIpsum.imageUrl(300, 200)
                ),
                GalleryElement.Item(
                    "Dine together 7",
                    LoremIpsum.words(25, 5),
                    LoremIpsum.imageUrl(300, 200)
                )
            )
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
            LoremIpsum.imageUrls(10, 800, 500)
        ),
        GalleryElement(
            SemanticColor.none,
            GalleryElement.Layout.ThreeColumns,
            "Extra, Not ordinary",
            (1..9).map {
                GalleryElement.Item(
                    LoremIpsum(3),
                    LoremIpsum(15),
                    LoremIpsum.imageUrl(400, 200)
                )
            }
        ),
        GalleryElement(
            SemanticColor.olive,
            GalleryElement.Layout.ThreeColumns,
            "Now Bases are coming",
            listOf(
                GalleryElement.Item(
                    "Frankfurt, Germany",
                    "",
                    LoremIpsum.imageUrl(400, 300)
                ),
                GalleryElement.Item(
                    "Lisbon, Portugal",
                    "",
                    LoremIpsum.imageUrl(400, 300)
                ),
                GalleryElement.Item(
                    "Amsterdam, Netherlands",
                    "",
                    LoremIpsum.imageUrl(400, 300)
                )
            )
        ),
        ListElement(
            SemanticColor.none,
            ListElement.Layout.TwoColumns,
            "Become a partner",
            "",
            listOf(
                ListElement.Item(
                    "check circle outline icon",
                    LoremIpsum.words(10, 5)
                ),
                ListElement.Item(
                    "check circle outline icon",
                    LoremIpsum.words(10, 5)
                ),
                ListElement.Item(
                    "check circle outline icon",
                    LoremIpsum.words(10, 5)
                ),
                ListElement.Item(
                    "check circle outline icon",
                    LoremIpsum.words(10, 5)
                ),
                ListElement.Item(
                    "check circle outline icon",
                    LoremIpsum.words(10, 5)
                ),
                ListElement.Item(
                    "check circle outline icon",
                    LoremIpsum.words(10, 5)
                )
            )
        ),
        GalleryElement(
            SemanticColor.blue,
            GalleryElement.Layout.FiveColumns,
            "Working together",
            listOf(
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                ),
                GalleryElement.Item(
                    image = LoremIpsum.imageUrl(200, 100)
                )
            )
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
