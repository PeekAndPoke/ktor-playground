package de.peekandpoke.ktorfx.webresources

/**
 * A single web resource
 */
data class WebResource(val uri: String, val cacheKey: String? = null, val integrity: String? = null) {

    val fullUri by lazy {

        when (cacheKey) {
            null -> uri
            else -> "$uri?$cacheKey"
        }
    }
}
