@file:Suppress("FunctionName", "unused")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.PropertyPath

internal enum class Operator(val op: String) {
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

internal enum class Logic(val op: String) {
    AND("AND"),
    OR("OR"),
}

internal class Filter(private val predicate: Expression<Boolean>) : Statement {

    override fun printAql(p: AqlPrinter) = p.append("FILTER ").append(predicate).appendLine()
}

typealias PartialFilterOnValue<T> = (Expression<T>) -> Expression<Boolean>

inline infix fun <S, reified T> PropertyPath<S, T>.ANY(partial: PartialFilterOnValue<T>): Expression<Boolean> = append<T>(" ANY").let(partial)
inline infix fun <S, reified T> PropertyPath<S, T>.NONE(partial: PartialFilterOnValue<T>): Expression<Boolean> = append<T>(" NONE").let(partial)
inline infix fun <S, reified T> PropertyPath<S, T>.ALL(partial: PartialFilterOnValue<T>): Expression<Boolean> = append<T>(" ALL").let(partial)

fun <T> EQ(value: T): PartialFilterOnValue<T> = { x -> x EQ value }
fun <T> EQ(value: Expression<T>): PartialFilterOnValue<T> = { x -> x EQ value }
infix fun <T> Expression<T>.EQ(value: T): Expression<Boolean> = FilterBy.value(this, Operator.EQ, value)
infix fun <T> Expression<T>.EQ(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.EQ, value)

fun <T> NE(value: T): PartialFilterOnValue<T> = { x -> x NE value }
fun <T> NE(value: Expression<T>): PartialFilterOnValue<T> = { x -> x NE value }
infix fun <T> Expression<T>.NE(value: T): Expression<Boolean> = FilterBy.value(this, Operator.NE, value)
infix fun <T> Expression<T>.NE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.NE, value)

fun <T> GT(value: T): PartialFilterOnValue<T> = { x -> x GT value }
fun <T> GT(value: Expression<T>): PartialFilterOnValue<T> = { x -> x GT value }
infix fun <T> Expression<T>.GT(value: T): Expression<Boolean> = FilterBy.value(this, Operator.GT, value)
infix fun <T> Expression<T>.GT(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.GT, value)

fun <T> GTE(value: T): PartialFilterOnValue<T> = { x -> x GTE value }
fun <T> GTE(value: Expression<T>): PartialFilterOnValue<T> = { x -> x GTE value }
infix fun <T> Expression<T>.GTE(value: T): Expression<Boolean> = FilterBy.value(this, Operator.GTE, value)
infix fun <T> Expression<T>.GTE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.GTE, value)

fun <T> LT(value: T): PartialFilterOnValue<T> = { x -> x LT value }
fun <T> LT(value: Expression<T>): PartialFilterOnValue<T> = { x -> x LT value }
infix fun <T> Expression<T>.LT(value: T): Expression<Boolean> = FilterBy.value(this, Operator.LT, value)
infix fun <T> Expression<T>.LT(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.LT, value)

fun <T> LTE(value: T): PartialFilterOnValue<T> = { x -> x LTE value }
fun <T> LTE(value: Expression<T>): PartialFilterOnValue<T> = { x -> x LTE value }
infix fun <T> Expression<T>.LTE(value: T): Expression<Boolean> = FilterBy.value(this, Operator.LTE, value)
infix fun <T> Expression<T>.LTE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.LTE, value)

fun <T> IN(value: Array<T>): PartialFilterOnValue<T> = { x -> x IN value }
fun <T> IN(value: Collection<T>): PartialFilterOnValue<T> = { x -> x IN value }
fun <T> IN(value: IterableExpression<T>): PartialFilterOnValue<T> = { x -> x IN value }
infix fun <T> Expression<T>.IN(value: Array<T>): Expression<Boolean> = IN(value.toList())
infix fun <T> Expression<T>.IN(value: Collection<T>): Expression<Boolean> = FilterBy.value(this, Operator.IN, value)
infix fun <T> Expression<T>.IN(value: IterableExpression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.IN, value)

fun <T> NOT_IN(value: Array<T>): PartialFilterOnValue<T> = { x -> x NOT_IN value }
fun <T> NOT_IN(value: Collection<T>): PartialFilterOnValue<T> = { x -> x NOT_IN value }
fun <T> NOT_IN(value: IterableExpression<T>): PartialFilterOnValue<T> = { x -> x NOT_IN value }
infix fun <T> Expression<T>.NOT_IN(value: Array<T>): Expression<Boolean> = NOT_IN(value.toList())
infix fun <T> Expression<T>.NOT_IN(value: Collection<T>): Expression<Boolean> = FilterBy.value(this, Operator.NOT_IN, value)
infix fun <T> Expression<T>.NOT_IN(value: IterableExpression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.NOT_IN, value)

infix fun <T> Expression<T>.LIKE(value: String): Expression<Boolean> = FilterBy.value(this, Operator.LIKE, value)
infix fun <T> Expression<T>.LIKE(value: Expression<String>): Expression<Boolean> = FilterBy.expr(this, Operator.LIKE, value)

infix fun <T> Expression<T>.REGEX(value: String): Expression<Boolean> = FilterBy.value(this, Operator.REGEX, value)
infix fun <T> Expression<T>.REGEX(value: Expression<String>): Expression<Boolean> = FilterBy.expr(this, Operator.REGEX, value)

infix fun Expression<Boolean>.AND(other: Expression<Boolean>): Expression<Boolean> = FilterLogic(this, Logic.AND, other)
infix fun Expression<Boolean>.OR(other: Expression<Boolean>): Expression<Boolean> = FilterLogic(this, Logic.OR, other)
fun Expression<Boolean>.NOT(): Expression<Boolean> = BoolFuncCall(AqlFunc.NOT, listOf(this))

internal data class FilterBy<L, R>(val left: Expression<L>, val op: Operator, val right: Expression<R>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean

    companion object {
        fun <XL, XR> value(left: Expression<XL>, op: Operator, right: XR) =
            FilterBy(left, op, ValueExpression(left, right as Any))

        fun <XL, XR> expr(left: Expression<XL>, op: Operator, right: Expression<XR>) =
            FilterBy(left, op, right)
    }

    override fun printAql(p: AqlPrinter) = p.append(left).append(" ${op.op} ").append(right)
}

internal data class FilterLogic(val left: Expression<Boolean>, val op: Logic, val right: Expression<Boolean>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean

    override fun printAql(p: AqlPrinter) = p.append("(").append(left).append(" ${op.op} ").append(right).append(")")
}

internal data class FilterNot(val filter: Expression<Boolean>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean

    override fun printAql(p: AqlPrinter) = p.append("NOT(").append(filter).append(")")
}
