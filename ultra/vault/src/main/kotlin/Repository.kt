package de.peekandpoke.ultra.vault

import kotlin.reflect.KClassifier

interface Repository<T> {

    /**
     * The name of the repository
     */
    val name: String

    /**
     * The type of the entities stored in the repository.
     *
     * This is needed for deserialization
     */
    val storedType: TypeRef<T>

    /**
     * Ensures that the repository is set up properly
     *
     * e.g. creating a database collection, ensuring indexes, ...
     */
    fun ensure()

    /**
     * Checks whether the repository stores the given cls
     */
    fun stores(type: KClassifier): Boolean = type == storedType.type.classifier

    fun findById(id: String): Stored<T>?
}
