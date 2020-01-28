package de.peekandpoke.modules.cms

data class CmsValidity(val errors: List<String>) {
    val isValid get() = errors.isEmpty()
}
