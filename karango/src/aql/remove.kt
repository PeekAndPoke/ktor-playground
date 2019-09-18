@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.ICollection
import de.peekandpoke.ultra.vault.Stored

@Suppress("unused")
@KarangoTerminalFuncMarker
fun REMOVE(what: String) = REMOVE(what.aql)

@Suppress("unused")
@KarangoTerminalFuncMarker
fun REMOVE(what: Expression<String>) = RemovePreStage(what)

@Suppress("unused")
@KarangoTerminalFuncMarker
fun <T> REMOVE(entity: Stored<T>) = RemovePreStage(entity._id.ensureKey.aql)

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
