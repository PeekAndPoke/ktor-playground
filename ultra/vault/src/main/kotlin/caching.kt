package de.peekandpoke.ultra.vault

import java.lang.reflect.Type

interface EntityCache {
    fun <T> getOrPut(id: String, builder: () -> T?): T?
}

object NullEntityCache : EntityCache {
    override fun <T> getOrPut(id: String, builder: () -> T?): T? = builder()
}

class DefaultEntityCache : EntityCache {

    private val entries = mutableMapOf<String, Any?>()

    override fun <T> getOrPut(id: String, builder: () -> T?): T? {

        if (entries.contains(id)) {
//            println("RefCache HIT $id")

            @Suppress("UNCHECKED_CAST")
            return entries[id] as T
        }

//        println("RefCache MISS $id")

        return builder().apply {
            entries[id] = this
        }
    }
}

class SharedRepoClassLookup {

    private val typeLookup = mutableMapOf<Type, Class<out Repository<*>>?>()

    private val nameLookup = mutableMapOf<String, Class<out Repository<*>>?>()

    fun getOrPut(type: Type, defaultValue: () -> Class<out Repository<*>>?) = typeLookup.getOrPut(type, defaultValue)

    fun getOrPut(name: String, defaultValue: () -> Class<out Repository<*>>?) = nameLookup.getOrPut(name, defaultValue)
}

