package de.peekandpoke.jointhebase.admin.views

enum class JtbAdminMenu {

    INDEX,
    RESIDENTIAL_PROPERTIES,
    RENTABLE_ROOMS;

    companion object {
        infix fun has(crumbs: List<Any>): Boolean = values().intersect(crumbs).isNotEmpty()
    }
}
