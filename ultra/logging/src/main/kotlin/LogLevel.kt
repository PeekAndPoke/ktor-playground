package de.peekandpoke.ultra.logging

enum class LogLevel(val severity: Int) {
    OFF(Int.MAX_VALUE),
    EMERGENCY(800),
    ALERT(700),
    CRITICAL(600),
    ERROR(500),
    WARNING(400),
    NOTICE(300),
    INFO(200),
    DEBUG(100)
}
