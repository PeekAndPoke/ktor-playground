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

data class PropertyPath<T>(private val previous: PropertyPath<*>?, private val current: Expression<T>) : Expression<T> {

    class PropStep<T>(private val name: String, private val type: TypeRef<T>) : Expression<T> {
        
        override fun getType() = type

        override fun printAql(p: AqlPrinter) = p.append(".").name(name)
    }
    
    companion object {
        inline fun <reified T> start(root: Expression<T>) = PropertyPath(null, root)
    }

    inline fun <reified NT> append(prop: String) = PropertyPath(this, PropStep<NT>(prop, typeRef()))
    
    fun <NT> append(expr: Expression<NT>) = PropertyPath(this, expr)

    override fun getType() = current.getType()

    override fun printAql(p: AqlPrinter) {

        p.append(allExpressions())
    }
    
    internal fun allExpressions(): List<Expression<*>> {
        val all = mutableListOf<Expression<*>>()

        var it: PropertyPath<*>? = this

        while (it != null) {
            all.add(it.current)

            it = it.previous
        }

        return all.reversed()
    }
}

inline val <reified T> PropertyPath<List<T>>.`*` inline get() = append<T>("[*]")

inline val <reified T> PropertyPath<T>.`**` inline get() = append<T>("[**]") 
