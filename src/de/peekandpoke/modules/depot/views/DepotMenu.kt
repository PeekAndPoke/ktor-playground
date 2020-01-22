package de.peekandpoke.modules.depot.views

enum class DepotMenu {
    INDEX,
    REPOSITORIES,
    BUCKETS,
    FILES;

    companion object {
        infix fun has(crumbs: List<Any>): Boolean = values().intersect(crumbs).isNotEmpty()
    }
}
