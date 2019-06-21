@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.Entity

@KarangoFuncMarker
fun <T> INSERT(what: Expression<T>) = InsertPreStage(what)

@KarangoFuncMarker
infix fun <T> InsertPreStage<T>.INTO(collection: CollectionDefinition<T>) = InsertInto(what, collection)

class InsertPreStage<T> internal constructor(val what: Expression<T>)

@KarangoFuncMarker
fun <T: Entity> INSERT(entity: T) = InsertPreStageEntity(entity)

@KarangoFuncMarker
infix fun <T : Entity> InsertPreStageEntity<T>.INTO(collection: CollectionDefinition<T>) = InsertInto(entity, collection)

class InsertPreStageEntity<T: Entity> internal constructor(val entity: T)

class InsertInto<T> internal constructor(private val expr: Expression<T>, private val collection: CollectionDefinition<T>) : TerminalExpr<T> {

    constructor(doc: T, collection: CollectionDefinition<T>) : this(Value(collection.getType().down(), doc), collection)

    override fun innerType() = expr.getType()

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) = with(p) {

        append("INSERT ").append(expr).append(" INTO ").append(collection).appendLine()
        append("RETURN NEW").appendLine()
    }
}
