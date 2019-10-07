package de.peekandpoke.ultra.depot

import java.time.Instant

interface DepotFile {

    val bucket: DepotBucket

    val name: String

    val lastModifiedAt: Instant?

    fun getContentBytes(): ByteArray
}
