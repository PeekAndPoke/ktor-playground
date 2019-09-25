package de.peekandpoke.ultra.vault

import de.peekandpoke.ultra.common.SimpleLazy
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

interface Vault {

    class Builder {

        private val repositories: MutableMap<Class<Repository<*>>, (DriverRegistry) -> Repository<*>> = mutableMapOf()

        internal fun build() = Blueprint(listOf(
            object : RepoProvider() {
                init {
                    repositories.forEach { (k, v) -> add(k, v) }
                }
            }
        ))

        inline fun <reified T : Repository<*>> add(noinline provider: (DriverRegistry) -> T) = add(T::class.java, provider)

        fun add(cls: Class<*>, provider: (DriverRegistry) -> Repository<*>) = apply {
            @Suppress("UNCHECKED_CAST")
            repositories[cls as Class<Repository<*>>] = provider
        }
    }

    abstract class RepoProvider {
        private val repos: MutableMap<Class<*>, (DriverRegistry) -> Repository<*>> = mutableMapOf()

        fun all(): Map<Class<*>, (DriverRegistry) -> Repository<*>> = repos.toMap()

        fun <T : Repository<*>> add(cls: Class<T>, provider: (DriverRegistry) -> T) {
            repos[cls] = provider
        }
    }

    class Blueprint(providers: List<RepoProvider>) {

        private val sharedTypeLookup = SharedRepoClassLookup()

        // merge all into a single map
        private val repos: Map<Class<*>, (DriverRegistry) -> Repository<*>> =
            providers.fold(mutableMapOf()) { acc, v -> acc.apply { putAll(v.all()) } }

        fun with(builder: (Database) -> Map<Key<Driver>, Driver>): Database {

            lateinit var database: Database

            val lazy = SimpleLazy {
                val drivers = DriverRegistry(builder(database))
                repos.map { (_, repo) -> repo(drivers) }.toList()
            }

            database = Database(lazy, sharedTypeLookup)

            return database
        }

//        fun with(builder: (Database) -> Map<Key<Driver>, Driver>) = Database(sharedTypeLookup).apply {
//
//            with(DriverRegistry(builder(this))) {
//                repositories.putAll(
//                    repos.map { (cls, repo) -> cls to repo(this) }.toMap()
//                )
//            }
//        }
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

class SharedRepoClassLookup {

    private val typeLookup = mutableMapOf<Type, Class<out Repository<*>>?>()

    private val nameLookup = mutableMapOf<String, Class<out Repository<*>>?>()

    fun getOrPut(type: Type, defaultValue: () -> Class<out Repository<*>>?) = typeLookup.getOrPut(type, defaultValue)

    fun getOrPut(name: String, defaultValue: () -> Class<out Repository<*>>?) = nameLookup.getOrPut(name, defaultValue)
}

class Database(repositories: Lazy<List<Repository<*>>>, private val repoClassLookup: SharedRepoClassLookup) {

    private val repositories: Map<Class<out Repository<*>>, Repository<*>> by lazy {
        repositories.value.map { it::class.java to it }.toMap()
    }

    fun getRepositories() = repositories.values

    fun ensureRepositories() {
        repositories.values.forEach { it.ensure() }
    }


    fun <T> hasRepositoryStoring(type: Class<T>): Boolean {
        // todo put some caching in place
        return null != repoClassLookup.getOrPut(type) {
            @Suppress("UNCHECKED_CAST")
            repositories.values.firstOrNull { it.stores(type) }?.let { it::class.java as Class<Repository<*>> }
        }
    }

    fun <T> getRepositoryStoring(type: Class<T>): Repository<T> {

        val cls = repoClassLookup.getOrPut(type) {
            repositories.values.firstOrNull { it.stores(type) }?.let { it::class.java }
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
        val cls = repoClassLookup.getOrPut(name) {
            repositories.values.firstOrNull { it.name == name }?.let { it::class.java }
        }

        if (cls != null) {
            return getRepository(cls)
        }

        // TODO: use customer exception
        error("No repository with name '$name' was found")
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
