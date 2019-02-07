package de.peekandpoke.karango.query

import de.peekandpoke.karango.Column
import de.peekandpoke.karango.Statement

interface Sort : Statement {

    enum class Direction(val op: String) {
        ASC("ASC"),
        DESC("DESC"),
    }
}

val <T> Column<T>.ASC: Sort get() = SortBy(this, Sort.Direction.ASC)
val <T> Column<T>.DESC: Sort get() = SortBy(this, Sort.Direction.DESC)

internal data class SortBy(val column: Column<*>, val direction: Sort.Direction) : Sort {
    override fun print(printer: QueryPrinter) {
        printer.append("${column.getQueryName()} ${direction.op}")
    }
}


