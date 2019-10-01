package de.peekandpoke

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplateImpl
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.module.cms.CmsAdmin
import de.peekandpoke.module.cms.views.CmsMenu
import de.peekandpoke.module.semanticui.SemanticUi
import de.peekandpoke.module.semanticui.views.SemanticUiMenu
import de.peekandpoke.ultra.polyglot.I18n
import kotlinx.html.script
import kotlinx.html.unsafe

class AdminTemplate(

    t: I18n,
    flashSession: FlashSession,
    webResources: WebResources,

    private val cms: CmsAdmin,
    private val semanticUi: SemanticUi


) : SimpleTemplateImpl(t, flashSession, webResources) {


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
                ui.given(CmsMenu has breadCrumbs) { active }.title {
                    ui.header.item {
                        icon.dropdown()
                        +"CMS"
                    }
                }
                ui.given(CmsMenu has breadCrumbs) { active }.content {
                    ui.accordion.transition.active {
                        // TODO: create a method in the Cms Admin Module that renders all menu entries
                        ui.item.given(CmsMenu.INDEX in breadCrumbs) { active } A { href = cms.routes.index; +"Overview" }

                        ui.item.given(CmsMenu.PAGES in breadCrumbs) { active } A { href = cms.routes.pages; +"Pages" }
                    }
                }

                ui.given(SemanticUiMenu has breadCrumbs) { active }.title {
                    ui.header.item {
                        icon.dropdown()
                        +"Semantic UI"
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
            }
        }
    }

}
