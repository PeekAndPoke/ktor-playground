package de.peekandpoke.ktorfx.webresources

import de.peekandpoke.ultra.common.md5

abstract class AppMeta {

    fun getVersion(): String? = this::class.java.`package`.implementationVersion

    fun getVersionMd5() = getVersion()?.md5() ?: System.currentTimeMillis().toString().md5()

    fun cacheBuster() = CacheBuster(getVersionMd5())
}
