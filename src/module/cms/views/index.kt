package de.peekandpoke.module.cms.views

import kotlinx.html.h1

internal fun Template.index() {

    activeMenu = MenuEntries.HOME

    content {
        h1 { +"Welcome to the CMS!" }
    }
}
