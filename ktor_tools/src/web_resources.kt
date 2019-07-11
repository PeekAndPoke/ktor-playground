package io.ultra.ktor_tools

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext
import io.ultra.common.md5
import java.io.InputStream

data class CacheBuster(val key: String) {
    override fun toString() = key
}

data class WebResource(val uri: String, val cacheKey: String? = null) {

    val full by lazy {

        when (cacheKey) {
            null -> uri
            else -> "$uri?$cacheKey"
        }
    }
}

abstract class WebResources(private val cacheBuster: CacheBuster) {

    private val _css = mutableListOf<WebResource>()
    private val _js = mutableListOf<WebResource>()

    val css: List<WebResource> get() = _css
    val js: List<WebResource> get() = _js

    fun webjarCss(uri: String) = apply {
        _css.add(WebResource(uri))
    }

    fun resourceCss(uri: String) = apply {
        _css.add(WebResource(uri, String(uri.toInputStream().readBytes()).md5()))
    }

    fun webjarJs(uri: String) = apply {
        _js.add(WebResource(uri))
    }

    fun resourceJs(uri: String) = apply {
        _js.add(WebResource(uri, String(uri.toInputStream().readBytes()).md5()))
    }

    private fun String.toInputStream() : InputStream = this::class.java.getResourceAsStream(this)
}

val iocWebResourcesKey = AttributeKey<WebResources>("web-resources")

inline val ApplicationCall.iocWebResources get() = attributes[iocWebResourcesKey]

inline val PipelineContext<Unit, ApplicationCall>.iocWebResources get() = call.iocWebResources

fun Attributes.put(value: WebResources) = put(iocWebResourcesKey, value)

