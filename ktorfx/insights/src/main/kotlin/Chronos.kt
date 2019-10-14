package de.peekandpoke.ktorfx.insights

class Chronos {

    var startNs: Long? = null
    var endNs: Long? = null

    fun start() {
        startNs = System.nanoTime()
    }

    fun end() {
        endNs = System.nanoTime()
    }

    fun totalDurationNs(): Long? {
        if (startNs == null || endNs == null) {
            return null
        }

        return endNs!! - startNs!!
    }
}
