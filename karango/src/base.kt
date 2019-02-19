@file:Suppress("ObjectPropertyName")

package de.peekandpoke.karango

import de.peekandpoke.karango.aql.*

interface CollectionDefinition<T> : IterableExpression<T> {

    fun getName() : String
    
    val configurations: Map<PropertyPath<*, *>, String>

    fun addConfiguration(key: PropertyPath<*, *>, config: String)
}

interface EntityCollectionDefinition<T> : CollectionDefinition<T>

interface EdgeCollectionDefinition<T> : CollectionDefinition<T>

abstract class CollectionDefinitionImpl<T>(private val name_: String, private val type: TypeRef<T>) : CollectionDefinition<T> {

    override val configurations = mutableMapOf<PropertyPath<*, *>, String>()

    override fun getName() = name_

    override fun toIterator() = IteratorExpr("i_$name_", this)

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(name_)

    override fun addConfiguration(key: PropertyPath<*, *>, config: String) = run { configurations[key] = config }
}

abstract class EntityCollectionDefinitionImpl<T>(name: String, type: TypeRef<T>) : CollectionDefinitionImpl<T>(name, type), EntityCollectionDefinition<T>

abstract class EdgeCollectionDefinitionImpl<T>(name: String, type: TypeRef<T>) : CollectionDefinitionImpl<T>(name, type), EdgeCollectionDefinition<T>

inline fun <S, reified T> IterableExpression<S>.startPropPath(key: String) = PropertyPath<S, T>(this, listOf(key), typeRef())

@Suppress("unused")
inline val <T> CollectionDefinition<T>._id
    inline get() = startPropPath<T, String>("._id")

@Suppress("unused")
inline val <T> CollectionDefinition<T>._key
    inline get() = startPropPath<T, String>("._key")

@Suppress("unused")
inline val <T> CollectionDefinition<T>._rev
    inline get() = startPropPath<T, String>("._rev")

data class PropertyPath<S, T>(
    private val named: IterableExpression<S>, private val path: List<String> = listOf(), private val type: TypeRef<T>
) : Expression<T> {

    fun getPath() = path

    fun getNamed() = named

    inline fun <reified NT> append(step: String) = PropertyPath<S, NT>(getNamed(), getPath().plus(step), typeRef())

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(
        listOf(named.toIterator().getName()).plus(path).joinToString("")
    )
}

inline val <S, reified T> PropertyPath<S, List<T>>.`*` inline get() = append<T>("[*]")

inline val <S, reified T> PropertyPath<S, T>.`**` inline get() = append<T>("[**]") 
