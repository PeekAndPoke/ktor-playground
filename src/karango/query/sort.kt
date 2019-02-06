package de.peekandpoke.karango.query

import de.peekandpoke.karango.Column

interface Sort : Expression {

    enum class Direction(val op: String) {
        asc("ASC"),
        desc("DESC"),
    }

    companion object {
        fun asc(column: Column<*>): Sort = SortBy(column, Direction.asc)
        fun desc(column: Column<*>): Sort = SortBy(column, Direction.desc)
    }
}

internal data class SortBy(val column: Column<*>, val direction: Sort.Direction) : Sort {
    override fun print(printer: QueryPrinter) {
        printer.append("${column.fqn} $direction")
    }
}

fun <T> Column<T>.ASC(): Sort = Sort.asc(this)
fun <T> Column<T>.desc(): Sort = Sort.desc(this)

