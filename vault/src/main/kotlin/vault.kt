package de.peekandpoke.ultra.vault

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

interface Vault {

    class Builder {

        private val repositories: MutableMap<Class<*>, (DriverRegistry) -> Repository<*>> = mutableMapOf()

        internal fun build() = Blueprint(repositories.toMap())

        inline fun <reified T : Repository<*>> add(noinline provider: (DriverRegistry) -> T) = add(T::class.java, provider)

        fun <T : Repository<*>> add(cls: Class<T>, provider: (DriverRegistry) -> T) = apply {
            repositories[cls] = provider
        }
    }

    class Blueprint internal constructor(private val repos: Map<Class<*>, (DriverRegistry) -> Repository<*>>) {

        private val sharedTypeLookup: MutableMap<Type, Class<Repository<*>>?> = mutableMapOf()

        fun with(builder: (Database) -> Map<Key<Driver>, Driver>) = Database(sharedTypeLookup).apply {

            with(DriverRegistry(builder(this))) {
                repositories.putAll(
                    repos.map { (cls, repo) -> cls to repo(this) }.toMap()
                )
            }
        }
    }

    companion object {
        fun setup(builder: Builder.() -> Unit) = Builder().apply(builder).build()
    }
}

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
    fun stores(type: Type): Boolean = when (storedType.type) {
        is ParameterizedType -> (storedType.type as ParameterizedType).rawType == type

        else -> storedType == type
    }

    fun findById(id: String): Stored<T>?
}

interface Driver

class DriverRegistry(private val drivers: Map<Key<Driver>, Driver>) {

    fun <T : Driver> get(key: Key<T>): T {

        @Suppress("UNCHECKED_CAST")
        return drivers[key] as T?
            ?: throw VaultException("No driver for key '${key.name}' was found")
    }
}

class Database internal constructor(private val typeLookup: MutableMap<Type, Class<Repository<*>>?>) {

    internal val repositories: MutableMap<Class<*>, Repository<*>> = mutableMapOf()

    private val repositoriesByName: MutableMap<String, Repository<*>?> = mutableMapOf()

    fun getRepositories() = repositories.values

    fun ensureRepositories() {
        repositories.values.forEach { it.ensure() }
    }


    fun <T> hasRepositoryStoring(type: Class<T>): Boolean {
        // todo put some caching in place
        return null != typeLookup.getOrPut(type) {
            @Suppress("UNCHECKED_CAST")
            repositories.values.firstOrNull { it.stores(type) }?.let { it::class.java as Class<Repository<*>> }
        }
    }

    fun <T> getRepositoryStoring(type: Class<T>): Repository<T> {

        val cls = typeLookup.getOrPut(type) {
            @Suppress("UNCHECKED_CAST")
            repositories.values.firstOrNull { it.stores(type) }?.let { it::class.java as Class<Repository<*>> }
        }

        if (cls != null) {
            @Suppress("UNCHECKED_CAST")
            return getRepository(cls) as Repository<T>
        }

        // TODO: use customer exception
        error("No repository stores the type '$type'")
    }

    fun <T : Repository<*>> getRepository(cls: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return repositories[cls] as T?
            ?: throw VaultException("No repository of class '$cls' is registered.")
    }

    inline fun <reified T : Repository<*>> getRepository() = getRepository(T::class.java)

    fun getRepository(name: String): Repository<*>? {

        return repositoriesByName.getOrPut(name) {
            repositories.values.firstOrNull { it.name == name }
        }
    }
}


class Key<out T>(val name: String) {
    override fun toString(): String = if (name.isEmpty())
        super.toString()
    else
        "Key: $name"
}

interface EntityCache {
    fun <T> getOrPut(id: String, builder: () -> T?): T?
}

class NullEntityCache : EntityCache {
    override fun <T> getOrPut(id: String, builder: () -> T?): T? = builder()
}

class DefaultEntityCache : EntityCache {

    private val entries = mutableMapOf<String, Any?>()

    override fun <T> getOrPut(id: String, builder: () -> T?): T? {

        if (entries.contains(id)) {
            println("RefCache HIT $id")

            @Suppress("UNCHECKED_CAST")
            return entries[id] as T
        }

        println("RefCache MISS $id")

        return builder().apply {
            entries[id] = this
        }
    }
}
