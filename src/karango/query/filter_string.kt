@file:Suppress("FunctionName")

package de.peekandpoke.karango.query

import de.peekandpoke.karango.Column
import de.peekandpoke.karango.Named

internal data class FilterContainsByValue(val column: Named<*>, val search: String) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("CONTAINS(${column.getQueryName()}, ").value(column, search).append(")")
    }
}

internal data class FilterContainsByNamed(val column: Named<*>, val search: Named<String>) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("CONTAINS(${column.getQueryName()}, ${search.getQueryName()})")
    }
}

infix fun Column<String>.CONTAINS(value: String): Filter = FilterContainsByValue(this, value)
infix fun Column<String>.CONTAINS(value: Named<String>): Filter = FilterContainsByNamed(this, value)


