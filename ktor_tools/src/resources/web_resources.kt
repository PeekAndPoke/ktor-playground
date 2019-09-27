package io.ultra.ktor_tools.resources

import de.peekandpoke.ultra.common.Lookup
import de.peekandpoke.ultra.common.base64
import de.peekandpoke.ultra.common.md5
import de.peekandpoke.ultra.common.sha384
import kotlinx.html.FlowOrMetaDataContent
import kotlinx.html.ScriptType
import kotlinx.html.link
import kotlinx.html.script
import java.io.InputStream
import kotlin.reflect.KClass

data class CacheBuster(val key: String)


class WebResources(private val groups: Lookup<WebResourceGroup>) {

    operator fun <T : WebResourceGroup> get(cls: KClass<T>): T = groups.get(cls)
        ?: throw Exception("Resource group '${cls.qualifiedName}' not present")
}

abstract class WebResourceGroup(cacheBuster: CacheBuster, builder: Builder.() -> Unit) {

    val css: List<WebResource>

    val js: List<WebResource>

    init {
        Builder(cacheBuster).apply(builder).apply {
            css = getCss()
            js = getJs()
        }
    }

    class Builder(private val cacheBuster: CacheBuster) {

        private val css = mutableListOf<WebResource>()
        private val js = mutableListOf<WebResource>()

        internal fun getCss() = css.toList()

        internal fun getJs() = js.toList()

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
