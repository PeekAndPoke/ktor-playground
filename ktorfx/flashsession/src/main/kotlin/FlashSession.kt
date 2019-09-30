package de.peekandpoke.ktorfx.flashsession

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.sessions.*

interface FlashSession {

    data class Data(val entries: List<Entry> = listOf()) {
        data class Entry(val message: String, val type: String)
    }

    fun pull(): List<Data.Entry>

    fun add(message: String, type: String)

    companion object {

        fun of(session: CurrentSession): FlashSession = when {
            session.get<Data>() != null -> SimpleFlashSession(session)

            else -> NullFlashSession()
        }

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
}

class NullFlashSession internal constructor() : FlashSession {

    override fun pull(): List<FlashSession.Data.Entry> = listOf()

    override fun add(message: String, type: String) = Unit
}

class SimpleFlashSession internal constructor(private val session: CurrentSession) : FlashSession {

    override fun pull(): List<FlashSession.Data.Entry> {

        val current = read()

        session.set(FlashSession.Data())

        return current.entries
    }

    override fun add(message: String, type: String) {

        val current = read()

        session.set(
            current.copy(
                entries = current.entries.plus(
                    FlashSession.Data.Entry(message, type)
                )
            )
        )
    }

    private fun read() = session.get<FlashSession.Data>() ?: FlashSession.Data()
}
