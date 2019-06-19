package de.peekandpoke.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.sessions.*
import io.ktor.util.pipeline.PipelineContext

val PipelineContext<Unit, ApplicationCall>.flashSession get() = FlashSession(call.sessions)

class FlashSession(private val session: CurrentSession) {

    companion object {
        fun register(config: Sessions.Configuration) {

            config.cookie<Data>("flash-messages") {

                cookie.path = "/"

                val jackson = ObjectMapper().registerKotlinModule()

                serializer = object : SessionSerializer {

                    override fun deserialize(text: String): Data = try {
                        jackson.readValue(text)
                    } catch (_: Exception) {
                        Data()
                    }

                    override fun serialize(session: Any): String = jackson.writeValueAsString(session)
                }
            }
        }
    }

    data class Data(val entries: List<Entry> = listOf()) {
        data class Entry(val message: String, val type: String)
    }

    fun pull() : List<Data.Entry> {

        val current = read()

        session.set(Data())

        return current.entries
    }

    fun add(message: String, type: String) {

        val current = read()

        session.set(
            current.copy(
                entries = current.entries.plus(
                    Data.Entry(message, type)
                )
            )
        )
    }

    private fun read() = session.get<Data>() ?: Data()
}
