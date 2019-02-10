@file:Suppress("FunctionName")

package de.peekandpoke.karango.query

import de.peekandpoke.karango.NamedType

internal data class FilterContainsByValue(val column: NamedType<*>, val search: String) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("CONTAINS(${column.getQueryName()}, ").value(column, search).append(")")
    }
}

internal data class FilterContainsByNamed(val column: NamedType<*>, val search: NamedType<String>) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("CONTAINS(${column.getQueryName()}, ${search.getQueryName()})")
    }
}

infix fun NamedType<String>.CONTAINS(value: String): Filter = FilterContainsByValue(this, value)
infix fun NamedType<String>.CONTAINS(value: NamedType<String>): Filter = FilterContainsByNamed(this, value)


