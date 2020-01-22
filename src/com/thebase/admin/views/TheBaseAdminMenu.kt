package com.thebase.admin.views

enum class TheBaseAdminMenu {

    INDEX,
    RESIDENTIAL_PROPERTIES,
    RENTABLE_ROOMS;

    companion object {
        infix fun has(crumbs: List<Any>): Boolean = values().intersect(crumbs).isNotEmpty()
    }
}
