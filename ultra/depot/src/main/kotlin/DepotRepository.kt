package de.peekandpoke.ultra.depot

interface DepotRepository {

    val name: String

    val type: String

    val location: String

    fun listBuckets(): List<DepotBucket>

    fun listNewest(limit: Int = 100): List<DepotBucket>

    fun get(bucketName: String): DepotBucket

    fun createBucket(bucketName: String): DepotBucket
}
