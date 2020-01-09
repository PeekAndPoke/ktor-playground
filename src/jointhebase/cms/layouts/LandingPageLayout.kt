package de.peekandpoke.jointhebase.cms.layouts

import de.peekandpoke.jointhebase.cms.elements.*
import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import de.peekandpoke.module.cms.CmsLayout
import de.peekandpoke.ultra.slumber.builtin.polymorphism.Polymorphic
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p

data class LandingPageLayout(
    val hero: HeroElement = HeroElement(
        SemanticColor.blue,
        "The cities that never sleep",
        "Lorem ipsum dolor sit amet, consetetur sadipscing elitr"
    ),
    val elements: List<CmsElement> = listOf(
        TextElement(
            SemanticColor.red,
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
        ),
        HeadlineElement(
            SemanticColor.none,
            "Unser Wertesystem und was du bei uns erleben kannst"
        ),
        TextImageElement(
            SemanticColor.none,
            true,
            "Sit Amet Consect",
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
        ),
        TextImageElement(
            SemanticColor.none,
            false,
            "Sit Amet Consect",
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
        ),
        SliderElement(
            SemanticColor.green,
            listOf(
                SliderElement.Item(
                    "Dine together",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
                ),
                SliderElement.Item(
                    "Dine together",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
                ),
                SliderElement.Item(
                    "Dine together",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
                ),
                SliderElement.Item(
                    "Dine together",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
                ),
                SliderElement.Item(
                    "Dine together",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
                )
            )
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

            ui.padded.three.column.grid {

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
                    +"Action"
                }
            }
        }

        div {
            id = "pull-down"
        }

        div(classes = "segment-stack") {

            hero.apply { render() }

            elements.forEach {
                it.apply { render() }
            }
        }
    }
}
