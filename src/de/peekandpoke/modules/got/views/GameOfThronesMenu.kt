package de.peekandpoke.modules.got.views

enum class GameOfThronesMenu {

    CHARACTERS,
    EDIT_CHARACTER;

    companion object {
        infix fun has(crumbs: List<Any>): Boolean = values().intersect(crumbs).isNotEmpty()
    }
}
