package de.peekandpoke.demos.forms

enum class FormDemosMenu {
    Index,
    SimpleFields,
    CommaSeparated,
    ListOfFields;

    companion object {
        infix fun has(crumbs: List<Any>): Boolean = values().intersect(crumbs).isNotEmpty()
    }
}
