package de.peekandpoke.ktorfx.templating.vm


class VmContext(private val path: String) {

    fun child(stepIn: String, block: VmContext.() -> Any?) {

        VmContext(this.path + "." + stepIn).block()
    }
}
