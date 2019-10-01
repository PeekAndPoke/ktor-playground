package de.peekandpoke.ultra.depot

import java.time.Instant

interface DepotBucket {

    val name: String

    val lastModifiedAt: Instant?

    fun listFiles(): List<DepotFile>

    fun getFile(name: String): DepotFile

    fun putFile(name: String, contents: String): DepotFile
}
