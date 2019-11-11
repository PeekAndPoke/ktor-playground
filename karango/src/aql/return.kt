@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.ultra.vault.TypeRef

@Suppress("unused")
@KarangoTerminalFuncMarker
fun <R> RETURN(ret: Expression<R>): TerminalExpr<R> = Return(ret)

internal class Return<T>(private val expr: Expression<T>) : TerminalExpr<T> {

    override fun innerType() = expr.getType()

    override fun getType(): TypeRef<List<T>> = expr.getType().list

    override fun printAql(p: AqlPrinter) = p.append("RETURN ").append(expr).appendLine()
}
