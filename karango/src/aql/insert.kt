@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.ICollection
import de.peekandpoke.ultra.vault.Storable

@Suppress("unused")
@KarangoTerminalFuncMarker
fun <T> StatementBuilder.INSERT(what: Expression<T>) = InsertExpression(what)

@KarangoDslMarker
class InsertExpression<T> internal constructor(private val what: Expression<T>) {
    @KarangoTerminalFuncMarker
    infix fun INTO(collection: ICollection<T>): TerminalExpr<T> = InsertExpressionInto(what, collection)
}

internal class InsertExpressionInto<T>(private val expr: Expression<T>, private val collection: ICollection<T>) : TerminalExpr<T> {

    override fun innerType() = expr.getType()

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) = with(p) {

        append("INSERT ").append(expr).append(" INTO ").append(collection).appendLine()
        append("RETURN NEW").appendLine()
    }
}

@Suppress("unused")
@KarangoTerminalFuncMarker
fun <T : Any> StatementBuilder.INSERT(entity: Storable<T>) = InsertNewStorable(entity)

class InsertNewStorable<T> internal constructor(val entity: Storable<T>)

@KarangoTerminalFuncMarker
infix fun <T> InsertNewStorable<T>.INTO(collection: ICollection<T>): TerminalExpr<T> = InsertNewStorableInto(entity, collection)

internal class InsertNewStorableInto<T>(private val new: Storable<T>, private val coll: ICollection<T>) : TerminalExpr<T> {

    override fun innerType() = coll.getType().down<T>()

    override fun getType() = coll.getType()

    override fun printAql(p: AqlPrinter) = with(p) {

        append("INSERT ").value("v", new).append(" INTO ").append(coll).appendLine()
        append("RETURN NEW").appendLine()
    }
}
