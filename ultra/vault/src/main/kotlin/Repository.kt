package de.peekandpoke.ultra.vault

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

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
    fun stores(type: Type): Boolean = when (val stored = storedType.getType()) {
        is ParameterizedType -> stored.rawType == type

        else -> storedType == type
    }

    fun findById(id: String): Stored<T>?
}
