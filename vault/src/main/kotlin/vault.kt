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

    class Blueprint internal constructor(private val repositories: Map<Class<*>, (DriverRegistry) -> Repository<*>>) {

        fun with(drivers: DriverRegistry) = Database(
            repositories.map { (cls, repo) -> cls to repo(drivers) }.toMap()
        )

        fun with(vararg drivers: Pair<Key<Driver>, Driver>) = with(DriverRegistry(*drivers))
    }

    companion object {
        fun create(builder: Builder.() -> Unit) = Builder().apply(builder).build()
    }
}

interface Repository<T> {
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

class DriverRegistry(vararg drivers: Pair<Key<Driver>, Driver>) {

    private val drivers: Map<Key<Driver>, Driver> = mutableMapOf(*drivers)

    fun <T : Driver> get(key: Key<T>): T {

        @Suppress("UNCHECKED_CAST")
        return drivers[key] as T?
            ?: throw VaultException("No driver for key '${key.name}' was found")
    }
}

class Database(private val repositories: Map<Class<*>, Repository<*>>) {

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
}


class Key<out T>(val name: String) {
    override fun toString(): String = if (name.isEmpty())
        super.toString()
    else
        "Key: $name"
}

class RefCache {
    val entries = mutableMapOf<String, Any?>()
}
