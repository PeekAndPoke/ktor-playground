package io.ultra.ktor_tools.resources

import de.peekandpoke.ultra.common.base64
import de.peekandpoke.ultra.common.md5
import de.peekandpoke.ultra.common.sha384
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
import io.ktor.util.pipeline.PipelineContext
import kotlinx.html.*
import java.io.InputStream

data class CacheBuster(val key: String) {
    override fun toString() = key
}

fun webResources(meta: AppMeta, builder: WebResources.() -> Unit): WebResources {
    return WebResources(meta.cacheBuster()).apply(builder)
}

class WebResources(private val cacheBuster: CacheBuster) {

    private val groups = mutableMapOf<String, WebResourceGroup>()

    fun group(name: String, builder: WebResourceGroup.Builder.() -> Unit): Unit {

        WebResourceGroup.Builder(cacheBuster).apply(builder).build().apply {
            groups[name] = this
        }
    }

    operator fun get(name: String) = groups[name] ?: throw Exception("Resource group '$name' not present")
}

data class WebResourceGroup(val css: List<WebResource>, val js: List<WebResource>) {

    class Builder(private val cacheBuster: CacheBuster) {

        private val css = mutableListOf<WebResource>()
        private val js = mutableListOf<WebResource>()

        internal fun build(): WebResourceGroup = WebResourceGroup(css, js)

        fun webjarCss(uri: String): WebResource {
            return WebResource(uri, cacheBuster.key).apply {
                css.add(this)
            }
        }

        fun resourceCss(uri: String): WebResource {

            val bytes = uri.toInputStream().readBytes()

            return WebResource(uri, bytes.md5(), "sha384-${bytes.sha384().base64()}").apply {
                css.add(this)
            }
        }

        fun webjarJs(uri: String): WebResource {
            return WebResource(uri, cacheBuster.key).apply {
                js.add(this)
            }
        }

        fun resourceJs(uri: String): WebResource {

            val bytes = uri.toInputStream().readBytes()

            return WebResource(uri, bytes.md5(), "sha384-${bytes.sha384().base64()}").apply {
                js.add(this)
            }
        }

        private fun String.toInputStream(): InputStream = this::class.java.getResourceAsStream(this)
    }
}

data class WebResource(val uri: String, val cacheKey: String? = null, val integrity: String? = null) {

    val fullUri by lazy {

        when (cacheKey) {
            null -> uri
            else -> "$uri?$cacheKey"
        }
    }
}

// HTML //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun FlowOrMetaDataContent.css(group: WebResourceGroup) = group.css.forEach { css ->
    link(rel = "stylesheet", href = css.fullUri) {
        css.integrity?.let { integrity = it }
    }
}

fun FlowOrMetaDataContent.js(group: WebResourceGroup) = group.js.forEach { js ->
    script(type = ScriptType.textJavaScript, src = js.fullUri) {
        js.integrity?.let { integrity = it }
    }
}

// IOC //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

val iocWebResourcesKey = AttributeKey<WebResources>("web-resources")

inline val ApplicationCall.iocWebResources get() = attributes[iocWebResourcesKey]

inline val PipelineContext<Unit, ApplicationCall>.iocWebResources get() = call.iocWebResources

fun Attributes.put(value: WebResources) = put(iocWebResourcesKey, value)

