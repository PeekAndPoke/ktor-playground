@file:Suppress("FunctionName", "unused")

package de.peekandpoke.karango.query

import de.peekandpoke.karango.Column

interface Filter : Expression {

    enum class Comparator(val op: String) {
        EQ("=="),
        NE("!="),
        GT(">"),
        GTE(">="),
        LT("<"),
        LTE("<="),
        IN("IN"),
        NOT_IN("NOT IN"),
        LIKE("LIKE"),
        REGEX("=~"),
    }

    enum class Logic(val op: String) {
        AND("AND"),
        OR("OR"),
    }
}

infix fun <T> Column<T>.EQ(value: T): Filter = FilterByValue(this, Filter.Comparator.EQ, value)
infix fun <T> Column<T>.EQ(value: Column<T>): Filter = FilterByColumn(this, Filter.Comparator.EQ, value)

infix fun <T> Column<T>.NE(value: T): Filter = FilterByValue(this, Filter.Comparator.NE,value)
infix fun <T> Column<T>.NE(value: Column<T>): Filter = FilterByColumn(this, Filter.Comparator.NE, value)

infix fun <T> Column<T>.GT(value: T): Filter = FilterByValue(this, Filter.Comparator.GT, value)
infix fun <T> Column<T>.GT(value: Column<T>): Filter = FilterByColumn(this, Filter.Comparator.GT, value)

infix fun <T> Column<T>.GTE(value: T): Filter = FilterByValue(this, Filter.Comparator.GTE, value)
infix fun <T> Column<T>.GTE(value: Column<T>): Filter = FilterByColumn(this, Filter.Comparator.GTE, value)

infix fun <T> Column<T>.LT(value: T): Filter = FilterByValue(this, Filter.Comparator.LT, value)
infix fun <T> Column<T>.LT(value: Column<T>): Filter = FilterByColumn(this, Filter.Comparator.LT, value)

infix fun <T> Column<T>.LTE(value: T): Filter = FilterByValue(this, Filter.Comparator.LTE, value)
infix fun <T> Column<T>.LTE(value: Column<T>): Filter = FilterByColumn(this, Filter.Comparator.LTE, value)

infix fun <T> Column<T>.IN(value: Array<T>): Filter = IN(value.toList())
infix fun <T> Column<T>.IN(value: List<T>): Filter = FilterByArray(this, Filter.Comparator.IN, value)
infix fun <T> Column<T>.IN(value: Column<T>): Filter = FilterByColumn(this, Filter.Comparator.IN, value)

infix fun <T> Column<T>.NOT_IN(value: Array<T>): Filter = NOT_IN(value.toList())
infix fun <T> Column<T>.NOT_IN(value: List<T>): Filter = FilterByArray(this, Filter.Comparator.NOT_IN, value)
infix fun <T> Column<T>.NOT_IN(value: Column<T>): Filter = FilterByColumn(this, Filter.Comparator.NOT_IN, value)

infix fun <T> Column<T>.LIKE(value: String): Filter = FilterByValue(this, Filter.Comparator.LIKE, value)
infix fun <T> Column<T>.LIKE(value: Column<String>): Filter = FilterByValue(this, Filter.Comparator.LIKE, value)

infix fun <T> Column<T>.REGEX(value: String): Filter = FilterByValue(this, Filter.Comparator.REGEX, value)
infix fun <T> Column<T>.REGEX(value: Column<String>): Filter = FilterByValue(this, Filter.Comparator.REGEX, value)

infix fun Filter.AND(other: Filter): Filter = FilterLogic(this, Filter.Logic.AND, other)
infix fun Filter.OR(other: Filter): Filter = FilterLogic(this, Filter.Logic.OR, other)
fun Filter.NOT(): Filter = FilterNot(this)

internal data class FilterByValue<L, R>(val left: Column<L>, val op: Filter.Comparator, val right: R) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("${left.fqn} ${op.op} ").value(left, right as Any)
    }
}

internal data class FilterByArray<L, R>(val left: Column<L>, val op: Filter.Comparator, val right: List<R>) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("${left.fqn} ${op.op} ").value(left, right)
    }
}

internal data class FilterByColumn<L, R>(val left: Column<L>, val op: Filter.Comparator, val right: Column<R>) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("${left.fqn} ${op.op} ${right.fqn}")
    }
}

internal data class FilterLogic(val left: Filter, val op: Filter.Logic, val right: Filter) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("(").append(left).append(" ${op.op} ").append(right).append(")")
    }
}

internal data class FilterNot(val filter: Filter) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("NOT(").append(filter).append(")")
    }
}
