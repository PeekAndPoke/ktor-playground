@file:Suppress("ObjectPropertyName")

package de.peekandpoke.karango

import de.peekandpoke.karango.aql.*

interface CollectionDefinition<T> : IterableExpression<T>, Aliased {

    override fun getAlias(): String

    val configurations: Map<PropertyPath<*>, String>

    fun addConfiguration(key: PropertyPath<*>, config: String)
}

interface EntityCollectionDefinition<T> : CollectionDefinition<T>

interface EdgeCollectionDefinition<T> : CollectionDefinition<T>

abstract class CollectionDefinitionImpl<T>(private val name_: String, private val type: TypeRef<T>) : CollectionDefinition<T> {

    override val configurations = mutableMapOf<PropertyPath<*>, String>()

    override fun getAlias() = name_

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)

    override fun addConfiguration(key: PropertyPath<*>, config: String) = run { configurations[key] = config }
}

abstract class EntityCollectionDefinitionImpl<T>(name: String, type: TypeRef<T>) : CollectionDefinitionImpl<T>(name, type), EntityCollectionDefinition<T>

abstract class EdgeCollectionDefinitionImpl<T>(name: String, type: TypeRef<T>) : CollectionDefinitionImpl<T>(name, type), EdgeCollectionDefinition<T>

@Suppress("unused")
inline val <reified T> CollectionDefinition<T>._id
    inline get() = PropertyPath.start(this).append<String>("_id")

@Suppress("unused")
inline val <reified T> CollectionDefinition<T>._key
    inline get() = PropertyPath.start(this).append<String>("_key")

@Suppress("unused")
inline val <reified T> CollectionDefinition<T>._rev
    inline get() = PropertyPath.start(this).append<String>("_rev")
