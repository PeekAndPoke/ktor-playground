package de.peekandpoke

import de.peekandpoke.ktorfx.flashsession.FlashSession
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplateImpl
import de.peekandpoke.ktorfx.webresources.WebResources
import de.peekandpoke.module.cms.CmsAdmin
import de.peekandpoke.module.cms.views.MenuEntries
import de.peekandpoke.module.semanticui.SemanticUi
import io.ultra.polyglot.I18n

class AdminTemplate(

    t: I18n,
    flashSession: FlashSession,
    webResources: WebResources,

    private val cms: CmsAdmin,
    private val semanticUi: SemanticUi


) : SimpleTemplateImpl(t, flashSession, webResources) {


    init {
        mainMenu {

            ui.item { +"CMS" }

            // TODO: create a method in the Cms Admin Module that renders all menu entries
            ui.item.given(MenuEntries.HOME in breadCrumbs) { active } A { href = cms.routes.index; +"Overview" }

            ui.item.given(MenuEntries.PAGES in breadCrumbs) { active } A { href = cms.routes.pages; +"Pages" }


            ui.item { +"SEMANTIC UI" }

            // TODO: create a method in the Semantic Ui Module that renders all menu entries
            ui.item A { href = semanticUi.routes.index; +"Semantic UI" }

            ui.item A { href = semanticUi.routes.playground; +"Playground" }

            ui.item A { href = semanticUi.routes.buttons; +"Buttons" }

            ui.item A { href = semanticUi.routes.icons; +"Icons" }


        }
    }

}
