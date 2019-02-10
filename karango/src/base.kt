package de.peekandpoke.karango

import de.peekandpoke.karango.query.QueryPrinter

@DslMarker
annotation class KarangoDslMarker

interface Statement {
    fun print(printer: QueryPrinter)
}

internal class Name(private val name: String) : Statement {
    override fun print(printer: QueryPrinter) {
        printer.append(name)
    }
}

interface NamedType<T> {
    fun getSimpleName() : String
    fun getQueryName() : String
}

interface IterableType<T> : NamedType<T> {
    fun getReturnType() : Class<T>
}

interface CollectionDefinition<T> : IterableType<T> {
    val configurations : Map<List<String>, String>
    
    fun addConfiguration(key : List<String>, config: String)
}

interface EntityCollectionDefinition<T> : CollectionDefinition<T>

interface EdgeCollectionDefinition<T> : CollectionDefinition<T>

abstract class CollectionDefinitionImpl<T> : CollectionDefinition<T> {
    override val configurations = mutableMapOf<List<String>, String>()

    override fun addConfiguration(key : List<String>, config: String) = run { configurations[key] = config }
}

abstract class EntityCollectionDefinitionImpl<T> : CollectionDefinitionImpl<T>(), EntityCollectionDefinition<T>

abstract class EdgeCollectionDefinitionImpl<T> : CollectionDefinitionImpl<T>(), EdgeCollectionDefinition<T>

class PathInCollection<S, T>(
    private val collection: CollectionDefinition<S>,
    private val path: List<String> = listOf()
) : NamedType<T> {

    fun getPath() = path
    
    fun getCollection() = collection
    
    override fun getSimpleName() = collection.getSimpleName()

    override fun getQueryName() = listOf(collection.getQueryName()).plus(path).joinToString(".")

    fun <NT> append(step: String) = PathInCollection<S, NT>(collection, path.plus(step))
}

fun <S, T> PathInCollection<S, T>.configure(value : String) = getCollection().addConfiguration(getPath(), value)
