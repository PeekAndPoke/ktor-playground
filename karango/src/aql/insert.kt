@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.ICollection
import de.peekandpoke.ultra.vault.TypeRef

@Suppress("unused")
@KarangoTerminalFuncMarker
fun <T> StatementBuilder.INSERT(what: Expression<T>) = InsertPreStage(what)

@KarangoTerminalFuncMarker
fun <T : Any> INSERT(entity: T) = InsertPreStage(Value(TypeRef(entity::class.java), entity))

@KarangoDslMarker
class InsertPreStage<T> internal constructor(private val what: Expression<T>) {
    @KarangoTerminalFuncMarker
    infix fun INTO(collection: ICollection<T>): TerminalExpr<T> = InsertInto(what, collection)
}

internal class InsertInto<T> internal constructor(private val expr: Expression<T>, private val collection: ICollection<T>) : TerminalExpr<T> {

    override fun innerType() = expr.getType()

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) = with(p) {

        append("INSERT ").append(expr).append(" INTO ").append(collection).appendLine()
        append("RETURN NEW").appendLine()
    }
}
