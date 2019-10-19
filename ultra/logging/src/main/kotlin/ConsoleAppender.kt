package de.peekandpoke.ultra.logging

import java.time.ZonedDateTime

class ConsoleAppender : LogAppender {

    override suspend fun append(ts: ZonedDateTime, level: LogLevel, message: String, loggerName: String) {
        println(
            format(ts, level, message, loggerName)
        )
    }

    companion object {
        fun format(ts: ZonedDateTime, level: LogLevel, message: String, loggerName: String): String {

            val name = when {
                loggerName.length <= 30 -> loggerName

                else -> {
                    val parts = loggerName.split(".")

                    if (parts.size == 1) {
                        parts[0]
                    } else {
                        // only use the first character for all but the simple class name
                        parts.take(parts.size - 1).map { it[0] }.joinToString(".") + "." + parts.last()
                    }
                }
            }

            return "${ts.toLocalDate()} ${ts.toLocalTime()} $level $name - $message"
        }
    }
}
