package de.peekandpoke

import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplateImpl
import de.peekandpoke.ktorfx.templating.TemplateTools
import de.peekandpoke.module.cms.CmsAdmin
import de.peekandpoke.module.cms.views.CmsMenu
import de.peekandpoke.module.depot.DepotAdmin
import de.peekandpoke.module.depot.views.DepotMenu
import de.peekandpoke.module.semanticui.SemanticUi
import de.peekandpoke.module.semanticui.views.SemanticUiMenu
import kotlinx.html.script
import kotlinx.html.unsafe

class AdminTemplate(

    tools: TemplateTools,

    private val cms: CmsAdmin,
    private val semanticUi: SemanticUi,
    private val depot: DepotAdmin


) : SimpleTemplateImpl(tools) {

    init {

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
                // CMS ///////////////////////////////////////////////////////////////////////////////////////////////////////////

                ui.given(CmsMenu has breadCrumbs) { active }.title.header.item H4 {
                    icon.dropdown()
                    +"Cms"
                }
                ui.given(CmsMenu has breadCrumbs) { active }.content {
                    ui.accordion.transition.active {
                        // TODO: create a method in the Cms Admin Module that renders all menu entries
                        ui.item.given(CmsMenu.INDEX in breadCrumbs) { active } A { href = cms.routes.index; +"Overview" }

                        ui.item.given(CmsMenu.PAGES in breadCrumbs) { active } A { href = cms.routes.pages; +"Pages" }
                    }
                }

                // DEPOT ///////////////////////////////////////////////////////////////////////////////////////////////////////////

                ui.given(DepotMenu has breadCrumbs) { active }.title.header.item H4 {
                    icon.dropdown()
                    +"File Depot"
                }
                ui.given(DepotMenu has breadCrumbs) { active }.content {
                    ui.accordion.transition.active {
                        // TODO: create a method in the Cms Admin Module that renders all menu entries
                        ui.item.given(DepotMenu.INDEX in breadCrumbs) { active } A { href = depot.routes.index; +"Overview" }

                        ui.item.given(DepotMenu.REPOSITORIES in breadCrumbs) { active } A { href = depot.routes.repositories; +"Repositories" }
                    }
                }

                // SEMANTIC UI ///////////////////////////////////////////////////////////////////////////////////////////////////////////

                ui.given(SemanticUiMenu has breadCrumbs) { active }.title.header.item H4 {
                    icon.dropdown()
                    +"Semantic UI"
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
            }
        }
    }

}
