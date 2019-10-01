package de.peekandpoke.ultra.depot

interface DepotRepository {

    val name: String

    val type: String

    fun listBuckets(): List<DepotBucket>

    fun get(bucketName: String): DepotBucket

    fun createBucket(bucketName: String): DepotBucket
}
