package de.peekandpoke.karango.aql

class Return<T>(private val expr: Expression<T>) : Expression<T> {

    override fun getType() = expr.getType()

    override fun printAql(p: AqlPrinter) = p.append("RETURN ").append(expr).appendLine()
}
