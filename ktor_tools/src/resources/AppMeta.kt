package io.ultra.ktor_tools.resources

import io.ultra.common.md5

/**
 * Created by gerk on 11.07.19 00:27
 */
abstract class AppMeta {
    fun getVersion(): String? = this::class.java.`package`.implementationVersion

    fun getVersionMd5() = getVersion()?.md5() ?: System.currentTimeMillis().toString().md5()
}
