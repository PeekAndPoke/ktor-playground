package de.peekandpoke.ultra.vault

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

        fun with(builder: (Database) -> List<Pair<Key<Driver>, Driver>>) = Database().apply {

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

    fun findById(id: String): Stored<T>?
}

interface Driver

class DriverRegistry(drivers: List<Pair<Key<Driver>, Driver>>) {

    private val drivers: Map<Key<Driver>, Driver> = mutableMapOf(*drivers.toTypedArray())

    fun <T : Driver> get(key: Key<T>): T {

        @Suppress("UNCHECKED_CAST")
        return drivers[key] as T?
            ?: throw VaultException("No driver for key '${key.name}' was found")
    }
}

class Database internal constructor() {

    internal val repositories: MutableMap<Class<*>, Repository<*>> = mutableMapOf()

    private val repositoriesByName: MutableMap<String, Repository<*>?> = mutableMapOf()

    fun getRepositories() = repositories.values

    fun ensureRepositories() {
        repositories.values.forEach { it.ensure() }
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
