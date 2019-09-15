package de.peekandpoke.karango

import com.arangodb.ArangoDBException

class KarangoException(message : String, cause: Throwable? = null) : Throwable(message, cause)

class KarangoQueryException(val query: TypedQuery<*>, message: String, override val cause: ArangoDBException): Throwable(message)
