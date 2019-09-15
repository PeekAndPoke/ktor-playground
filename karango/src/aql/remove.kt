@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.Entity
import de.peekandpoke.karango.ICollection

@Suppress("unused")
@KarangoTerminalFuncMarker
fun StatementBuilder.REMOVE(what: String) = REMOVE(what.aql)

@Suppress("unused")
@KarangoTerminalFuncMarker
fun StatementBuilder.REMOVE(what: Expression<String>) = RemovePreStage(what)

@KarangoTerminalFuncMarker
fun <T : Entity> REMOVE(entity: T) = RemovePreStage(entity._id?.ensureKey.aql)

@KarangoDslMarker
class RemovePreStage internal constructor(private val what: Expression<*>) {

    @KarangoTerminalFuncMarker
    infix fun <T> IN(collection: ICollection<T>): TerminalExpr<T> = RemoveIn(what, collection)
}

internal class RemoveIn<T> internal constructor(private val expr: Expression<*>, private val collection: ICollection<T>) : TerminalExpr<T> {

    override fun innerType() = collection.getType().down<T>()

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) = with(p) {
        append("REMOVE ").append(expr).append(" IN ").append(collection).appendLine()
        append("RETURN OLD").appendLine()
    }
}
