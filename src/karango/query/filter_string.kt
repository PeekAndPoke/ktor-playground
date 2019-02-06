@file:Suppress("FunctionName")

package de.peekandpoke.karango.query

import de.peekandpoke.karango.Column

internal data class FilterContains(val column: Column<*>, val search: String) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("CONTAINS(${column.fqn}, $search)")
    }
}

fun Column<String>.CONTAINS(other: String): Filter = FilterContains(this, "'$other'")


