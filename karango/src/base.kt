@file:Suppress("ObjectPropertyName")

package de.peekandpoke.karango

import de.peekandpoke.karango.query.AqlPrinter
import de.peekandpoke.karango.query.PrintableExpression
import de.peekandpoke.karango.query.PrintableStatement

@DslMarker
annotation class KarangoDslMarker

interface Typed<T> {
    fun getReturnType(): Class<T>
}

interface Named {
    fun getName(): String
}

interface Expression<T> : Typed<T>, PrintableExpression

interface NamedExpression<T> : Expression<T>, Named

interface IterableExpression<T> : Expression<T>

interface NamedIterableExpression<T> : IterableExpression<T>, NamedExpression<T>

interface Statement<T> : Typed<T>, PrintableStatement

internal class NamedExpressionImpl<T>(private val name_: String, private val type: Class<T>) : NamedExpression<T> {

    override fun getName() = name_

    override fun getReturnType() = type

    override fun printExpr(p: AqlPrinter) = p.name(this)
}

internal class NamedIterableExpressionImpl<T>(private val name_: String, private val type: Class<T>) : NamedIterableExpression<T> {

    override fun getName() = name_

    override fun getReturnType() = type

    override fun printExpr(p: AqlPrinter) = p.name(this)
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

inline fun <S, reified T> CollectionDefinition<S>.startPropPath(key: String) = PropertyPath(this, listOf(key), T::class.java)

interface CollectionDefinition<T> : NamedIterableExpression<T> {

    val configurations: Map<PropertyPath<*, *>, String>

    fun addConfiguration(key: PropertyPath<*, *>, config: String)
}

interface EntityCollectionDefinition<T> : CollectionDefinition<T>

interface EdgeCollectionDefinition<T> : CollectionDefinition<T>

abstract class CollectionDefinitionImpl<T>(private val name_: String, private val type: Class<T>) : CollectionDefinition<T> {

    override val configurations = mutableMapOf<PropertyPath<*, *>, String>()

    override fun getName() = name_

    override fun getReturnType() = type

    override fun printExpr(p: AqlPrinter) = p.name(this)

    override fun addConfiguration(key: PropertyPath<*, *>, config: String) = run { configurations[key] = config }
}

abstract class EntityCollectionDefinitionImpl<T>(name: String, type: Class<T>) : CollectionDefinitionImpl<T>(name, type), EntityCollectionDefinition<T>

abstract class EdgeCollectionDefinitionImpl<T>(name: String, type: Class<T>) : CollectionDefinitionImpl<T>(name, type), EdgeCollectionDefinition<T>

data class PropertyPath<S, T>(
    private val collection: CollectionDefinition<S>,
    private val path: List<String> = listOf(),
    private val type: Class<T>
) : NamedExpression<T> {
    
    override fun getName() = listOf(collection.getName()).plus(path).joinToString("")
    
    fun getPath() = path

    fun getCollection() = collection

    inline fun <reified NT> append(step: String) = PropertyPath(getCollection(), getPath().plus(step), NT::class.java)

    override fun getReturnType() = type

    override fun printExpr(p: AqlPrinter) = p.identifier(this)
}

fun <S, T> PropertyPath<S, T>.configure(value: String) = getCollection().addConfiguration(this, value)

inline val <S, reified T> PropertyPath<S, List<T>>.`*` inline get() = append<T>("[*]")

inline val <S, reified T> PropertyPath<S, T>.`**` inline get() = append<T>("[**]") 
