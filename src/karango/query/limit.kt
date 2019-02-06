package de.peekandpoke.karango.query

internal data class OffsetAndLimit(val offset: Int, val limit: Int) : Expression {
    override fun print(printer: QueryPrinter) {
        printer.append("$offset, $limit")
    }
}
