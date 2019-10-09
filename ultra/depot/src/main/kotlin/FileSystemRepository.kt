package de.peekandpoke.ultra.depot

import java.io.File
import java.time.Instant

abstract class FileSystemRepository(override val name: String, dir: String) : DepotRepository {

    data class FsBucket(override val name: String, private val root: File) : DepotBucket {

        override val lastModifiedAt: Instant by lazy {
            Instant.ofEpochMilli(root.lastModified())
        }

        override fun listFiles(): List<DepotFile> {
            return root.listFiles()
                ?.filter { it.isFile }
                ?.map { FsFile(this, it.name, it) }
                ?: listOf()
        }

        override fun listNewest(limit: Int): List<DepotFile> {
            return listFiles().sortedBy { it.lastModifiedAt }.reversed().take(limit)
        }

        override fun getFile(name: String): DepotFile {
            return FsFile(this, name, File(root, name))
        }

        override fun putFile(name: String, contents: String): DepotFile {

            val file = File(root, name)

            file.writeText(contents)

            return FsFile(this, name, file)
        }
    }

    data class FsFile(override val bucket: FsBucket, override val name: String, private val file: File) : DepotFile {

        override val lastModifiedAt: Instant by lazy {
            Instant.ofEpochMilli(file.lastModified())
        }

        override fun getContentBytes(): ByteArray {
            return file.readBytes()
        }
    }

    private val root = File(dir).absoluteFile

    override val type = "File system"

    override val location: String = root.absolutePath

    override fun listBuckets(): List<DepotBucket> {
        return root.listFiles()
            ?.filter { it.isDirectory }
            ?.map { FsBucket(it.name, it) }
            ?: listOf()
    }

    override fun listNewest(limit: Int): List<DepotBucket> {
        return root.listFiles()
            ?.filter { it.isDirectory }
            ?.sortedBy { it.lastModified() }
            ?.reversed()
            ?.take(limit)
            ?.map { FsBucket(it.name, it) }
            ?: listOf()
    }

    override fun get(bucketName: String): DepotBucket {
        return FsBucket(bucketName, File(root, bucketName))
    }

    override fun createBucket(bucketName: String): DepotBucket {

        val bucketDir = File(root.absolutePath, bucketName)

        if (!bucketDir.exists()) {
            bucketDir.mkdirs()
        }

        return FsBucket(bucketName, bucketDir)
    }
}
