@file:Suppress("FunctionName", "unused")

package de.peekandpoke.karango.query

import de.peekandpoke.karango.*

interface FilterPredicate : Expression<FilterPredicate> {

    override fun getReturnType() = FilterPredicate::class.java

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

internal class Filter(private val predicate : FilterPredicate) : Statement<Filter> {
    
    override fun getReturnType() = Filter::class.java

    override fun printStmt(p: AqlPrinter) = p.append("FILTER ").expr(predicate).appendLine()
}

//typealias PartialFilterOnIterable<T> = (IterableType<T>) -> Filter
typealias PartialFilterOnValue<T> = (NamedExpression<T>) -> FilterPredicate

inline infix fun <S, reified T> PropertyPath<S, T>.ANY(partial: PartialFilterOnValue<T>): FilterPredicate = append<T>(" ANY").let(partial)
inline infix fun <S, reified T> PropertyPath<S, T>.NONE(partial: PartialFilterOnValue<T>): FilterPredicate = append<T>(" NONE").let(partial)
inline infix fun <S, reified T> PropertyPath<S, T>.ALL(partial: PartialFilterOnValue<T>): FilterPredicate = append<T>(" ALL").let(partial)

fun <T> EQ(value: T): PartialFilterOnValue<T> = { x -> x EQ value }
fun <T> EQ(value: Expression<T>): PartialFilterOnValue<T> = { x -> x EQ value }
infix fun <T> NamedExpression<T>.EQ(value: T): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.EQ, value)
infix fun <T> NamedExpression<T>.EQ(value: Expression<T>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.EQ, value)

fun <T> NE(value: T): PartialFilterOnValue<T> = { x -> x NE value }
fun <T> NE(value: Expression<T>): PartialFilterOnValue<T> = { x -> x NE value }
infix fun <T> NamedExpression<T>.NE(value: T): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.NE, value)
infix fun <T> NamedExpression<T>.NE(value: Expression<T>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.NE, value)

fun <T> GT(value: T): PartialFilterOnValue<T> = { x -> x GT value }
fun <T> GT(value: Expression<T>): PartialFilterOnValue<T> = { x -> x GT value }
infix fun <T> NamedExpression<T>.GT(value: T): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.GT, value)
infix fun <T> NamedExpression<T>.GT(value: Expression<T>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.GT, value)

fun <T> GTE(value: T): PartialFilterOnValue<T> = { x -> x GTE value }
fun <T> GTE(value: Expression<T>): PartialFilterOnValue<T> = { x -> x GTE value }
infix fun <T> NamedExpression<T>.GTE(value: T): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.GTE, value)
infix fun <T> NamedExpression<T>.GTE(value: Expression<T>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.GTE, value)

fun <T> LT(value: T): PartialFilterOnValue<T> = { x -> x LT value }
fun <T> LT(value: Expression<T>): PartialFilterOnValue<T> = { x -> x LT value }
infix fun <T> NamedExpression<T>.LT(value: T): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.LT, value)
infix fun <T> NamedExpression<T>.LT(value: Expression<T>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.LT, value)

fun <T> LTE(value: T): PartialFilterOnValue<T> = { x -> x LTE value }
fun <T> LTE(value: Expression<T>): PartialFilterOnValue<T> = { x -> x LTE value }
infix fun <T> NamedExpression<T>.LTE(value: T): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.LTE, value)
infix fun <T> NamedExpression<T>.LTE(value: Expression<T>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.LTE, value)

fun <T> IN(value: Array<T>): PartialFilterOnValue<T> = { x -> x IN value }
fun <T> IN(value: Collection<T>): PartialFilterOnValue<T> = { x -> x IN value }
fun <T> IN(value: IterableExpression<T>): PartialFilterOnValue<T> = { x -> x IN value }
infix fun <T> NamedExpression<T>.IN(value: Array<T>): FilterPredicate = IN(value.toList())
infix fun <T> NamedExpression<T>.IN(value: Collection<T>): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.IN, value)
infix fun <T> NamedExpression<T>.IN(value: IterableExpression<T>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.IN, value)

fun <T> NOT_IN(value: Array<T>): PartialFilterOnValue<T> = { x -> x NOT_IN value }
fun <T> NOT_IN(value: Collection<T>): PartialFilterOnValue<T> = { x -> x NOT_IN value }
fun <T> NOT_IN(value: IterableExpression<T>): PartialFilterOnValue<T> = { x -> x NOT_IN value }
infix fun <T> NamedExpression<T>.NOT_IN(value: Array<T>): FilterPredicate = NOT_IN(value.toList())
infix fun <T> NamedExpression<T>.NOT_IN(value: Collection<T>): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.NOT_IN, value)
infix fun <T> NamedExpression<T>.NOT_IN(value: IterableExpression<T>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.NOT_IN, value)

infix fun <T> NamedExpression<T>.LIKE(value: String): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.LIKE, value)
infix fun <T> NamedExpression<T>.LIKE(value: Expression<String>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.LIKE, value)

infix fun <T> NamedExpression<T>.REGEX(value: String): FilterPredicate = FilterBy.value(this, FilterPredicate.Comparator.REGEX, value)
infix fun <T> NamedExpression<T>.REGEX(value: Expression<String>): FilterPredicate = FilterBy.expr(this, FilterPredicate.Comparator.REGEX, value)

infix fun FilterPredicate.AND(other: FilterPredicate): FilterPredicate = FilterLogic(this, FilterPredicate.Logic.AND, other)
infix fun FilterPredicate.OR(other: FilterPredicate): FilterPredicate = FilterLogic(this, FilterPredicate.Logic.OR, other)
fun FilterPredicate.NOT(): FilterPredicate = FilterNot(this)

internal class ValueExpression(private val named: Expression<*>, private val value: Any) : Expression<Any> {
    
    override fun getReturnType() = Any::class.java

    override fun printExpr(p: AqlPrinter): Any = p.value(named, value)
}

internal data class FilterBy<L, R>(val left: NamedExpression<L>, val op: FilterPredicate.Comparator, val right: Expression<R>) : FilterPredicate {

    companion object {
        fun <XL, XR> value(left: NamedExpression<XL>, op: FilterPredicate.Comparator, right: XR) =
            FilterBy(left, op, ValueExpression(left, right as Any))

        fun <XL, XR> expr(left: NamedExpression<XL>, op: FilterPredicate.Comparator, right: Expression<XR>) =
            FilterBy(left, op, right)
    }

    override fun printExpr(p: AqlPrinter) = p.expr(left).append(" ${op.op} ").expr(right)
}

internal data class FilterLogic(val left: FilterPredicate, val op: FilterPredicate.Logic, val right: FilterPredicate) : FilterPredicate {

    override fun printExpr(p: AqlPrinter) = p.append("(").expr(left).append(" ${op.op} ").expr(right).append(")")
}

internal data class FilterNot(val filter: FilterPredicate) : FilterPredicate {

    override fun printExpr(p: AqlPrinter) = p.append("NOT(").expr(filter).append(")")
}
