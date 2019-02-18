package de.peekandpoke.karango.query

import de.peekandpoke.karango.Statement

data class OffsetAndLimit(val offset: Int, val limit: Int) : Statement {

    override fun printAql(p: AqlPrinter) = p.append("LIMIT $offset, $limit").appendLine()
}
