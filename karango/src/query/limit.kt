package de.peekandpoke.karango.query

import de.peekandpoke.karango.Statement

internal data class OffsetAndLimit(val offset: Int, val limit: Int) : Statement<OffsetAndLimit> {

    override fun getReturnType() = OffsetAndLimit::class.java

    override fun printStmt(p: AqlPrinter) = p.append("LIMIT $offset, $limit").appendLine()
}
