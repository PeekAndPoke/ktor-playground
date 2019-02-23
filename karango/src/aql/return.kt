package de.peekandpoke.karango.aql

class Return<T> internal constructor(
    private val expr: Expression<T>
) : TerminalExpr<T> {

    override fun innerType() = expr.getType()

    override fun getType() = expr.getType().up()

    override fun printAql(p: AqlPrinter) = p.append("RETURN ").append(expr).appendLine()
}


