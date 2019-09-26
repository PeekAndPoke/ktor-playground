package de.peekandpoke.ultra.vault

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
