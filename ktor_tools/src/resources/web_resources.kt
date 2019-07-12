package io.ultra.ktor_tools.resources

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext
import io.ultra.common.base64
import io.ultra.common.md5
import io.ultra.common.sha384
import java.io.InputStream

data class CacheBuster(val key: String) {
    override fun toString() = key
}

data class WebResource(val uri: String, val cacheKey: String? = null, val integrity: String? = null) {

    val fullUri by lazy {

        when (cacheKey) {
            null -> uri
            else -> "$uri?$cacheKey"
        }
    }
}

abstract class WebResources(private val cacheBuster: CacheBuster) {

    private val _css = mutableListOf<WebResource>()
    private val _js = mutableListOf<WebResource>()

    val x = 1

    val css: List<WebResource> get() = _css
    val js: List<WebResource> get() = _js

    fun webjarCss(uri: String) = apply {
        _css.add(WebResource(uri, cacheBuster.key))
    }

    fun resourceCss(uri: String) = apply {

        val bytes = uri.toInputStream().readBytes()

        _css.add(
            WebResource(uri, bytes.md5(), "sha384-${bytes.sha384().base64()}")
        )
    }

    fun webjarJs(uri: String) = apply {
        _js.add(WebResource(uri, cacheBuster.key))
    }

    fun resourceJs(uri: String) = apply {

        val bytes = uri.toInputStream().readBytes()

        _js.add(
            WebResource(uri, bytes.md5(), "sha384-${bytes.sha384().base64()}")
        )
    }

    private fun String.toInputStream() : InputStream = this::class.java.getResourceAsStream(this)
}

val iocWebResourcesKey = AttributeKey<WebResources>("web-resources")

inline val ApplicationCall.iocWebResources get() = attributes[iocWebResourcesKey]

inline val PipelineContext<Unit, ApplicationCall>.iocWebResources get() = call.iocWebResources

fun Attributes.put(value: WebResources) = put(iocWebResourcesKey, value)

