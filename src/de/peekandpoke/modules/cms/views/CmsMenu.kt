package de.peekandpoke.modules.cms.views

enum class CmsMenu {
    INDEX,
    PAGES,
    SNIPPETS;

    companion object {
        infix fun has(crumbs: List<Any>): Boolean = values().intersect(crumbs).isNotEmpty()
    }
}
