package de.peekandpoke.karango

import de.peekandpoke.karango.query.QueryPrinter

@DslMarker
annotation class KarangoDslMarker

interface Statement {
    fun print(printer: QueryPrinter)
}

interface NamedType<T> {
    fun getSimpleName() : String
    fun getQueryName() : String
}

interface ReturnType<T> {
    fun getReturnType(): Class<T>
}

interface IterableType<T> : NamedType<T>, ReturnType<T>

inline val <T> CollectionDefinition<T>._id inline get() = de.peekandpoke.karango.PathInCollection<T, String>(this, kotlin.collections.listOf("._id"))
inline val <T> CollectionDefinition<T>._key inline get() = de.peekandpoke.karango.PathInCollection<T, String>(this, kotlin.collections.listOf("._key"))

interface CollectionDefinition<T> : IterableType<T> {
    val configurations : Map<PathInCollection<*, *>, String>
    
    fun addConfiguration(key : PathInCollection<*, *>, config: String)
}

interface EntityCollectionDefinition<T> : CollectionDefinition<T>

interface EdgeCollectionDefinition<T> : CollectionDefinition<T>

abstract class CollectionDefinitionImpl<T> : CollectionDefinition<T> {
    override val configurations = mutableMapOf<PathInCollection<*, *>, String>()

    override fun addConfiguration(key : PathInCollection<*, *>, config: String) = run { configurations[key] = config }
}

abstract class EntityCollectionDefinitionImpl<T> : CollectionDefinitionImpl<T>(), EntityCollectionDefinition<T>

abstract class EdgeCollectionDefinitionImpl<T> : CollectionDefinitionImpl<T>(), EdgeCollectionDefinition<T>

data class PathInCollection<S, T>(
    private val collection: CollectionDefinition<S>,
    private val path: List<String> = listOf()
) : NamedType<T> {

    fun getPath() = path
    
    fun getCollection() = collection
    
    override fun getSimpleName() = collection.getSimpleName()

    override fun getQueryName() = listOf(collection.getQueryName()).plus(path).joinToString("")

    fun <NT> append(step: String) = PathInCollection<S, NT>(collection, path.plus(step))
}

fun <S, T> PathInCollection<S, T>.configure(value : String) = getCollection().addConfiguration(this, value)

inline val <S, T> PathInCollection<S, List<T>>.`*` inline get() = append<T>("[*]")

inline val <S, T> PathInCollection<S, T>.`**` inline get() = append<T>("[**]") 
