package de.peekandpoke.ultra.vault

import de.peekandpoke.ultra.common.Lookup

class Database(
    private val repositories: Lookup<Repository<*>>,
    private val repoClassLookup: SharedRepoClassLookup
) {

//    private val repositories: Map<Class<out Repository<*>>, Repository<*>> by lazy {
//        repositories.all().map { it::class.java to it }.toMap()
//    }

    fun getRepositories() = repositories.all()

    fun ensureRepositories() {
        repositories.all().forEach { it.ensure() }
    }


    fun <T : Any> hasRepositoryStoring(type: Class<T>): Boolean {
        // todo put some caching in place
        return null != repoClassLookup.getOrPut(type) {
            @Suppress("UNCHECKED_CAST")
            repositories.all().firstOrNull { it.stores(type.kotlin) }?.let { it::class.java as Class<Repository<*>> }
        }
    }

    fun <T : Any> getRepositoryStoring(type: Class<T>): Repository<T> {

        val cls = repoClassLookup.getOrPut(type) {
            repositories.all().firstOrNull { it.stores(type.kotlin) }?.let { it::class.java }
        }

        if (cls != null) {
            @Suppress("UNCHECKED_CAST")
            return getRepository(cls) as Repository<T>
        }

        // TODO: use customer exception
        error("No repository stores the type '$type'")
    }

    fun <T : Repository<*>> getRepository(cls: Class<T>): T {
        return repositories.getOrNull(cls.kotlin)
            ?: throw VaultException("No repository of class '$cls' is registered.")
    }

    inline fun <reified T : Repository<*>> getRepository() = getRepository(T::class.java)

    fun getRepository(name: String): Repository<*>? {
        val cls = repoClassLookup.getOrPut(name) {
            repositories.all().firstOrNull { it.name == name }?.let { it::class.java }
        }

        if (cls != null) {
            return getRepository(cls)
        }

        // TODO: use customer exception
        error("No repository with name '$name' was found")
    }
}
