@file:Suppress("ObjectPropertyName")

package de.peekandpoke.karango

import de.peekandpoke.karango.aql.*

interface CollectionDefinition<T> : Expression<List<T>>, Aliased

interface EntityCollectionDefinition<T> : CollectionDefinition<T>

interface EdgeCollectionDefinition<T> : CollectionDefinition<T>

abstract class CollectionDefinitionImpl<T>(private val name_: String, private val type: TypeRef<List<T>>) : CollectionDefinition<T> {

    override fun getAlias() = name_
    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)
}

abstract class EntityCollectionDefinitionImpl<T>(name: String, type: TypeRef<List<T>>) :
    CollectionDefinitionImpl<T>(name, type), EntityCollectionDefinition<T>

abstract class EdgeCollectionDefinitionImpl<T>(name: String, type: TypeRef<List<T>>) :
    CollectionDefinitionImpl<T>(name, type), EdgeCollectionDefinition<T>

@Suppress("unused")
inline val <reified T: Entity> Expression<T>._id
    inline get() = PropertyPath.start(this).append<String, String>("_id")

@Suppress("unused")
inline val <reified T: Entity> Expression<T>._key
    inline get() = PropertyPath.start(this).append<String, String>("_key")

@Suppress("unused")
inline val <reified T: Entity> Expression<T>._rev
    inline get() = PropertyPath.start(this).append<String, String>("_rev")
