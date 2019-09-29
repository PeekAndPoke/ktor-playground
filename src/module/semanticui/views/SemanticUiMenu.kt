package de.peekandpoke.module.semanticui.views

enum class SemanticUiMenu {
    Index,
    Playground,
    Buttons,
    Icons;

    companion object {
        infix fun has(crumbs: List<Any>): Boolean = values().intersect(crumbs).isNotEmpty()
    }

}
