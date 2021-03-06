package de.peekandpoke.ultra.logging

import java.time.ZonedDateTime

interface LogAppender {
    suspend fun append(ts: ZonedDateTime, level: LogLevel, message: String, loggerName: String)
}
