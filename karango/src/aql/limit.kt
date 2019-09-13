package de.peekandpoke.karango.aql

internal data class OffsetAndLimit(val offset: Int, val limit: Int) : Statement {

    override fun printAql(p: AqlPrinter) = p.append("LIMIT $offset, $limit").appendLine()
}
