@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.ICollection

@Suppress("unused")
@KarangoTerminalFuncMarker
fun <T> StatementBuilder.INSERT(what: Expression<T>) = InsertPreStage(what)

@KarangoTerminalFuncMarker
infix fun <T> InsertPreStage<T>.INTO(collection: ICollection<T>) = InsertInto(what, collection)

class InsertPreStage<T> internal constructor(val what: Expression<T>)

@KarangoTerminalFuncMarker
fun <T: Entity> INSERT(entity: T) = InsertPreStageEntity(entity)

@KarangoTerminalFuncMarker
infix fun <T : Entity> InsertPreStageEntity<T>.INTO(collection: ICollection<T>) = InsertInto(entity, collection)

class InsertPreStageEntity<T: Entity> internal constructor(val entity: T)

class InsertInto<T> internal constructor(private val expr: Expression<T>, private val collection: ICollection<T>) : TerminalExpr<T> {

    constructor(doc: T, collection: ICollection<T>) : this(Value(collection.getType().down(), doc), collection)

    override fun innerType() = expr.getType()

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) = with(p) {

        append("INSERT ").append(expr).append(" INTO ").append(collection).appendLine()
        append("RETURN NEW").appendLine()
    }
}
