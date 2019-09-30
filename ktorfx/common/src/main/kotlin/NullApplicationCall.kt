package de.peekandpoke.ktorfx.common

import io.ktor.sessions.CurrentSession
import kotlin.reflect.KClass

class NullCurrentSession : CurrentSession {
    override fun clear(name: String): Unit = Unit

    override fun findName(type: KClass<*>): String = ""

    override fun get(name: String): Any? = null

    override fun set(name: String, value: Any?): Unit = Unit
}
