package de.peekandpoke.karango.query

import de.peekandpoke.karango.Statement

internal data class OffsetAndLimit(val offset: Int, val limit: Int) : Statement {
    override fun print(printer: QueryPrinter) {
        printer.append("$offset, $limit")
    }
}
