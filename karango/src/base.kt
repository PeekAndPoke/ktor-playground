@file:Suppress("ObjectPropertyName")

package de.peekandpoke.karango

import de.peekandpoke.karango.query.AqlPrinter
import de.peekandpoke.karango.query.Printable

@DslMarker
annotation class KarangoDslMarker

interface Typed<T> {
    fun getType(): TypeRef<T>
}

interface Named {
    fun getName(): String
}

interface Statement : Printable

interface Expression<T> : Typed<T>, Printable

interface NamedExpression<T> : Expression<T>, Named

interface IterableExpression<T> : Expression<T>

interface NamedIterableExpression<T> : IterableExpression<T>, NamedExpression<T>

internal class NamedExpressionImpl<T>(private val name_: String, private val type: TypeRef<T>) : NamedExpression<T> {

    override fun getName() = name_

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(this)
}

internal class NamedIterableExpressionImpl<T>(private val name_: String, private val type: TypeRef<T>) : NamedIterableExpression<T> {

    override fun getName() = name_

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(this)
}
//
//interface Queryable<T> {
//    fun getQueryName() : String
//}


@Suppress("unused")
inline val <T> CollectionDefinition<T>._id
    inline get() = startPropPath<T, String>("._id")

@Suppress("unused")
inline val <T> CollectionDefinition<T>._key
    inline get() = startPropPath<T, String>("._key")

@Suppress("unused")
inline val <T> CollectionDefinition<T>._rev
    inline get() = startPropPath<T, String>("._rev")

inline fun <S, reified T> NamedExpression<S>.startPropPath(key: String) = PropertyPath<S, T>(this, listOf(key), typeRef())

interface CollectionDefinition<T> : NamedIterableExpression<T> {

    val configurations: Map<PropertyPath<*, *>, String>

    fun addConfiguration(key: PropertyPath<*, *>, config: String)
}

interface EntityCollectionDefinition<T> : CollectionDefinition<T>

interface EdgeCollectionDefinition<T> : CollectionDefinition<T>

abstract class CollectionDefinitionImpl<T>(private val name_: String, private val type: TypeRef<T>) : CollectionDefinition<T> {

    override val configurations = mutableMapOf<PropertyPath<*, *>, String>()

    override fun getName() = name_

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.name(this)

    override fun addConfiguration(key: PropertyPath<*, *>, config: String) = run { configurations[key] = config }
}

abstract class EntityCollectionDefinitionImpl<T>(name: String, type: TypeRef<T>) : CollectionDefinitionImpl<T>(name, type), EntityCollectionDefinition<T>

abstract class EdgeCollectionDefinitionImpl<T>(name: String, type: TypeRef<T>) : CollectionDefinitionImpl<T>(name, type), EdgeCollectionDefinition<T>

data class PropertyPath<S, T>(
    private val named: NamedExpression<S>, private val path: List<String> = listOf(), private val type: TypeRef<T>
) : NamedExpression<T> {

    override fun getName() = listOf(named.getName()).plus(path).joinToString("")

    fun getPath() = path

    fun getNamed() = named

    inline fun <reified NT> append(step: String) = PropertyPath<S, NT>(getNamed(), getPath().plus(step), typeRef())

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.iterator(this)
}

inline val <S, reified T> PropertyPath<S, List<T>>.`*` inline get() = append<T>("[*]")

inline val <S, reified T> PropertyPath<S, T>.`**` inline get() = append<T>("[**]") 
