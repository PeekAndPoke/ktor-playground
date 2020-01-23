package com.thebase.apps.admin

import com.thebase.apps.admin.views.TheBaseAdminMenu
import de.peekandpoke.demos.forms.FormDemos
import de.peekandpoke.demos.forms.FormDemosMenu
import de.peekandpoke.demos.semanticui.SemanticUi
import de.peekandpoke.demos.semanticui.views.SemanticUiMenu
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.noui
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.TemplateTools
import de.peekandpoke.ktorfx.templating.semanticui.SemanticUiAdminTemplate
import de.peekandpoke.ktorfx.webresources.css
import de.peekandpoke.ktorfx.webresources.js
import de.peekandpoke.modules.cms.CmsAdmin
import de.peekandpoke.modules.cms.views.CmsMenu
import de.peekandpoke.modules.depot.DepotAdmin
import de.peekandpoke.modules.depot.views.DepotMenu
import de.peekandpoke.modules.got.GameOfThrones
import de.peekandpoke.modules.got.views.GameOfThronesMenu
import kotlinx.html.div
import kotlinx.html.script
import kotlinx.html.unsafe

class AdminTemplate(

    tools: TemplateTools,

    val theBase: TheBaseAdmin,
    val cms: CmsAdmin,
    private val depot: DepotAdmin,
    private val gameOfThrones: GameOfThrones,
    private val semanticUi: SemanticUi,
    private val formDemos: FormDemos

) : SemanticUiAdminTemplate(tools) {

    init {

        loadDefaultJQuery()
        loadDefaultSemanticUi()
        initInsights()

        styles {
            css(webResources.admin)
        }

        scripts {
            js(webResources.admin)
        }

        scripts {
            // TODO: move this somewhere else
            script {
                unsafe {
                    +"""
                        ${'$'}('.ui.accordion')
                          .accordion()
                        ;
                    """.trimIndent()
                }
            }
        }

        mainMenu {

            ui.accordion {

                // JoinTheBase ///////////////////////////////////////////////////////////////////////////////////////////////////////////

                ui.given(TheBaseAdminMenu has breadCrumbs) { active }.title.header.item H4 {
                    icon.dropdown()

                    div {
                        icon.building_outline()
                        +"The Base"
                    }
                }
                ui.given(TheBaseAdminMenu has breadCrumbs) { active }.content {
                    ui.accordion.transition.active {
                        // TODO: create a method in the Cms Admin Module that renders all menu entries
                        ui.item.given(TheBaseAdminMenu.INDEX in breadCrumbs) { active } A { href = theBase.routes.index; +"Overview" }
                    }
                }

                // CMS ///////////////////////////////////////////////////////////////////////////////////////////////////////////

                ui.given(CmsMenu has breadCrumbs) { active }.title.header.item H4 {
                    icon.dropdown()

                    div {
                        icon.tv()
                        +"Cms"
                    }
                }
                ui.given(CmsMenu has breadCrumbs) { active }.content {
                    ui.accordion.transition.active {
                        // TODO: create a method in the Cms Admin Module that renders all menu entries
                        ui.item.given(CmsMenu.INDEX in breadCrumbs) { active } A { href = cms.routes.index; +"Overview" }

                        ui.item.given(CmsMenu.PAGES in breadCrumbs) { active } A { href = cms.routes.pages; +"Pages" }

                        ui.item.given(CmsMenu.SNIPPETS in breadCrumbs) { active } A { href = cms.routes.snippets; +"Snippets" }
                    }
                }

                // DEPOT ///////////////////////////////////////////////////////////////////////////////////////////////////////////

                ui.given(DepotMenu has breadCrumbs) { active }.title.header.item H4 {
                    icon.dropdown()

                    div {
                        icon.folder()
                        +"File Depot"
                    }
                }
                ui.given(DepotMenu has breadCrumbs) { active }.content {
                    ui.accordion.transition.active {
                        // TODO: create a method in the Cms Admin Module that renders all menu entries
                        ui.item.given(DepotMenu.INDEX in breadCrumbs) { active } A { href = depot.routes.index; +"Overview" }

                        ui.item.given(DepotMenu.REPOSITORIES in breadCrumbs) { active } A { href = depot.routes.repositories; +"Repositories" }
                    }
                }

                // TOOLS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                ui.given(breadCrumbs.isTools()) { active }.title.header.item H4 {
                    icon.dropdown()

                    div {
                        icon.wrench()
                        +"Tools & Demos"
                    }
                }

                ui.given(breadCrumbs.isTools()) { active }.content {

                    noui.accordion.transition {
                        // Semantic UI demos

                        ui.given(SemanticUiMenu has breadCrumbs) { active }.title.header.item H5 {
                            icon.dropdown()

                            div {
                                icon.code()
                                +"SemanticUI"
                            }
                        }

                        ui.given(SemanticUiMenu has breadCrumbs) { active }.content {
                            ui.accordion.transition.active {
                                // TODO: create a method in the Semantic Ui Module that renders all menu entries
                                ui.item.given(SemanticUiMenu.Index in breadCrumbs) { active } A { href = semanticUi.routes.index; +"Semantic UI" }

                                ui.item.given(SemanticUiMenu.Playground in breadCrumbs) { active } A { href = semanticUi.routes.playground; +"Playground" }

                                ui.item.given(SemanticUiMenu.Buttons in breadCrumbs) { active } A { href = semanticUi.routes.buttons; +"Buttons" }

                                ui.item.given(SemanticUiMenu.Icons in breadCrumbs) { active } A { href = semanticUi.routes.icons; +"Icons" }
                            }
                        }

                        // Form demos

                        ui.given(FormDemosMenu has breadCrumbs) { active }.title.header.item H5 {
                            icon.dropdown()

                            div {
                                icon.code()
                                +"Forms"
                            }
                        }

                        ui.given(FormDemosMenu has breadCrumbs) { active }.content {
                            ui.accordion.transition.active {

                                ui.item.given(FormDemosMenu.Index in breadCrumbs) { active } A {
                                    href = formDemos.routes.index
                                    +"Index"
                                }

                                ui.item.given(FormDemosMenu.SimpleFields in breadCrumbs) { active } A {
                                    href = formDemos.routes.simpleFields
                                    +"Simple form fields"
                                }

                                ui.item.given(FormDemosMenu.CommaSeparated in breadCrumbs) { active } A {
                                    href = formDemos.routes.commaSeparated
                                    +"Comma separated lists"
                                }

                                ui.item.given(FormDemosMenu.ListOfFields in breadCrumbs) { active } A {
                                    href = formDemos.routes.listOfFields
                                    +"List of fields"
                                }
                            }
                        }
                    }
                }

                // PLAYGROUND //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                ui.given(GameOfThronesMenu has breadCrumbs) { active }.title.header.item H4 {
                    icon.dropdown()

                    div {
                        icon.gamepad()
                        +"Playground"
                    }
                }
                ui.given(GameOfThronesMenu has breadCrumbs) { active }.content {
                    ui.accordion.transition.active {
                        ui.item.given(GameOfThronesMenu.CHARACTERS in breadCrumbs) { active } A {
                            href = gameOfThrones.routes.getCharacters(); +"Game of Thrones"
                        }
                    }
                }
            }
        }
    }

    private fun List<Any>.isTools() = SemanticUiMenu has this || FormDemosMenu has this
}
