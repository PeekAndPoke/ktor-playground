@file:Suppress("FunctionName", "unused")

package de.peekandpoke.karango.query

import de.peekandpoke.karango.NamedType
import de.peekandpoke.karango.Statement

interface Filter : Statement {

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

infix fun <T> NamedType<T>.EQ(value: T): Filter = FilterByValue(this, Filter.Comparator.EQ, value)
infix fun <T> NamedType<T>.EQ(value: NamedType<T>): Filter = FilterByNamed(this, Filter.Comparator.EQ, value)

infix fun <T> NamedType<T>.NE(value: T): Filter = FilterByValue(this, Filter.Comparator.NE, value)
infix fun <T> NamedType<T>.NE(value: NamedType<T>): Filter = FilterByNamed(this, Filter.Comparator.NE, value)

infix fun <T> NamedType<T>.GT(value: T): Filter = FilterByValue(this, Filter.Comparator.GT, value)
infix fun <T> NamedType<T>.GT(value: NamedType<T>): Filter = FilterByNamed(this, Filter.Comparator.GT, value)

infix fun <T> NamedType<T>.GTE(value: T): Filter = FilterByValue(this, Filter.Comparator.GTE, value)
infix fun <T> NamedType<T>.GTE(value: NamedType<T>): Filter = FilterByNamed(this, Filter.Comparator.GTE, value)

infix fun <T> NamedType<T>.LT(value: T): Filter = FilterByValue(this, Filter.Comparator.LT, value)
infix fun <T> NamedType<T>.LT(value: NamedType<T>): Filter = FilterByNamed(this, Filter.Comparator.LT, value)

infix fun <T> NamedType<T>.LTE(value: T): Filter = FilterByValue(this, Filter.Comparator.LTE, value)
infix fun <T> NamedType<T>.LTE(value: NamedType<T>): Filter = FilterByNamed(this, Filter.Comparator.LTE, value)

infix fun <T> NamedType<T>.IN(value: Array<T>): Filter = IN(value.toList())
infix fun <T> NamedType<T>.IN(value: List<T>): Filter = FilterByArray(this, Filter.Comparator.IN, value)
infix fun <T> NamedType<T>.IN(value: NamedType<T>): Filter = FilterByNamed(this, Filter.Comparator.IN, value)

infix fun <T> NamedType<T>.NOT_IN(value: Array<T>): Filter = NOT_IN(value.toList())
infix fun <T> NamedType<T>.NOT_IN(value: List<T>): Filter = FilterByArray(this, Filter.Comparator.NOT_IN, value)
infix fun <T> NamedType<T>.NOT_IN(value: NamedType<T>): Filter = FilterByNamed(this, Filter.Comparator.NOT_IN, value)

infix fun <T> NamedType<T>.LIKE(value: String): Filter = FilterByValue(this, Filter.Comparator.LIKE, value)
infix fun <T> NamedType<T>.LIKE(value: NamedType<String>): Filter = FilterByValue(this, Filter.Comparator.LIKE, value)

infix fun <T> NamedType<T>.REGEX(value: String): Filter = FilterByValue(this, Filter.Comparator.REGEX, value)
infix fun <T> NamedType<T>.REGEX(value: NamedType<String>): Filter = FilterByValue(this, Filter.Comparator.REGEX, value)

infix fun Filter.AND(other: Filter): Filter = FilterLogic(this, Filter.Logic.AND, other)
infix fun Filter.OR(other: Filter): Filter = FilterLogic(this, Filter.Logic.OR, other)
fun Filter.NOT(): Filter = FilterNot(this)

internal data class FilterByValue<L, R>(val left: NamedType<L>, val op: Filter.Comparator, val right: R) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("${left.getQueryName()} ${op.op} ").value(left, right as Any)
    }
}

internal data class FilterByArray<L, R>(val left: NamedType<L>, val op: Filter.Comparator, val right: List<R>) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("${left.getQueryName()} ${op.op} ").value(left, right)
    }
}

internal data class FilterByNamed<L, R>(val left: NamedType<L>, val op: Filter.Comparator, val right: NamedType<R>) : Filter {
    override fun print(printer: QueryPrinter) {
        printer.append("${left.getQueryName()} ${op.op} ${right.getQueryName()}")
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
