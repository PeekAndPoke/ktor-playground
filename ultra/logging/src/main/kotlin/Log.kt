package de.peekandpoke.ultra.logging

import kotlin.reflect.KClass

class Log internal constructor(private val caller: KClass<*>, private val manager: UltraLogManager) {

    fun log(level: LogLevel, message: String) {
        manager.log(level, message, caller.qualifiedName ?: "n/a")
    }

    fun emergency(message: String) {
        log(LogLevel.EMERGENCY, message)
    }

    fun alert(message: String) {
        log(LogLevel.ALERT, message)
    }

    fun critical(message: String) {
        log(LogLevel.CRITICAL, message)
    }

    fun error(message: String) {
        log(LogLevel.ERROR, message)
    }

    fun warning(message: String) {
        log(LogLevel.WARNING, message)
    }

    fun notice(message: String) {
        log(LogLevel.NOTICE, message)
    }

    fun info(message: String) {
        log(LogLevel.INFO, message)
    }

    fun debug(message: String) {
        log(LogLevel.DEBUG, message)
    }
}
