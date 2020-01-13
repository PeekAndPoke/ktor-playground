package de.peekandpoke.jointhebase.cms.elements

import de.peekandpoke.ktorfx.semanticui.SemanticColor
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.module.cms.CmsElement
import kotlinx.html.FlowContent
import kotlinx.html.div

data class FooterElement(
    val background: SemanticColor = SemanticColor.none

) : CmsElement {

    override fun FlowContent.render() {

        ui.basic.inverted.segment.with(background.toString()) {

            ui.three.column.grid {

                ui.row {

                    ui.column {

                        div {
                            +"See you soon"
                        }
                    }

                    ui.column {
                        +"Contact"
                    }

                    ui.column {
                        +"Let's meet up no matter where"
                    }
                }

                ui.row {
                    ui.column {
                        ui.horizontal.list {
                            ui.item {
                                +"Legal Notice"
                            }
                            ui.item {
                                +"Data Privacy"
                            }
                            ui.item {
                                +"Disclaimer"
                            }
                        }
                    }

                    ui.column {
                        +"The Base"
                    }

                    ui.column {
                        icon.copyright_outline()
                        +"2020 The Base - Future Of Living"
                    }
                }
            }
        }
    }
}
