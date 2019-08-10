@file:Suppress("ObjectPropertyName")

package de.peekandpoke.karango

import de.peekandpoke.karango.aql.*

interface ICollection<T> : Expression<List<T>>, Aliased

interface IEntityCollection<T> : ICollection<T>

interface IEdgeCollection<T> : ICollection<T>

abstract class Collection<T>(private val name_: String, private val type: TypeRef<List<T>>) : ICollection<T> {

    override fun getAlias() = name_
    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)
}

abstract class EntityCollection<T>(name: String, type: TypeRef<List<T>>) :
    Collection<T>(name, type), IEntityCollection<T>

abstract class EdgeCollection<T>(name: String, type: TypeRef<List<T>>) :
    Collection<T>(name, type), IEdgeCollection<T>

@Suppress("unused")
inline val <reified T: Entity> Expression<T>._id
    inline get() = PropertyPath.start(this).append<String, String>("_id")

@Suppress("unused")
inline val <reified T: Entity> Expression<T>._key
    inline get() = PropertyPath.start(this).append<String, String>("_key")

@Suppress("unused")
inline val <reified T: Entity> Expression<T>._rev
    inline get() = PropertyPath.start(this).append<String, String>("_rev")
