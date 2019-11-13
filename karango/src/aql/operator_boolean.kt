@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.ultra.common.TypeRef
import de.peekandpoke.ultra.common.kType

enum class BooleanOperator(val op: String) {
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

enum class ArrayOperator(val op: String) {
    ANY("ANY"),
    NONE("NONE"),
    ALL("ALL"),
}

enum class LogicOperator(val op: String) {
    AND("AND"),
    OR("OR"),
}

typealias PartialBooleanExpression<T> = (Expression<T>) -> Expression<Boolean>

data class ArrayOpExpr<T>(val expr: Expression<List<T>>, val op: ArrayOperator, private val type: TypeRef<T>) : Expression<T>, Aliased {

    override fun getAlias() = if (expr is Aliased) expr.getAlias() + "_${op.op}" else "v"

    override fun getType() = type

    override fun printAql(p: AqlPrinter) = p.append(expr).append(" ${op.op}")
}

inline infix fun <reified T> Expression<List<T>>.ANY(partial: PartialBooleanExpression<T>): Expression<Boolean> =
    partial(ArrayOpExpr(this, ArrayOperator.ANY, kType()))

inline infix fun <reified T> Expression<List<T>>.NONE(partial: PartialBooleanExpression<T>): Expression<Boolean> =
    partial(ArrayOpExpr(this, ArrayOperator.NONE, kType()))

inline infix fun <reified T> Expression<List<T>>.ALL(partial: PartialBooleanExpression<T>): Expression<Boolean> =
    partial(ArrayOpExpr(this, ArrayOperator.ALL, kType()))


fun <T> EQ(value: T?): PartialBooleanExpression<T> = { x -> x EQ value }
fun <T> EQ(value: Expression<T>): PartialBooleanExpression<T> = { x -> x EQ value }
infix fun <T> Expression<T>.EQ(value: T?): Expression<Boolean> = FilterBy.value(this, BooleanOperator.EQ, value)
infix fun <T> Expression<T>.EQ(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.EQ, value)

fun <T> NE(value: T?): PartialBooleanExpression<T> = { x -> x NE value }
fun <T> NE(value: Expression<T>): PartialBooleanExpression<T> = { x -> x NE value }
infix fun <T> Expression<T>.NE(value: T?): Expression<Boolean> = FilterBy.value(this, BooleanOperator.NE, value)
infix fun <T> Expression<T>.NE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.NE, value)

fun <T> GT(value: T?): PartialBooleanExpression<T> = { x -> x GT value }
fun <T> GT(value: Expression<T>): PartialBooleanExpression<T> = { x -> x GT value }
infix fun <T> Expression<T>.GT(value: T?): Expression<Boolean> = FilterBy.value(this, BooleanOperator.GT, value)
infix fun <T> Expression<T>.GT(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.GT, value)

fun <T> GTE(value: T?): PartialBooleanExpression<T> = { x -> x GTE value }
fun <T> GTE(value: Expression<T>): PartialBooleanExpression<T> = { x -> x GTE value }
infix fun <T> Expression<T>.GTE(value: T?): Expression<Boolean> = FilterBy.value(this, BooleanOperator.GTE, value)
infix fun <T> Expression<T>.GTE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.GTE, value)

fun <T> LT(value: T?): PartialBooleanExpression<T> = { x -> x LT value }
fun <T> LT(value: Expression<T>): PartialBooleanExpression<T> = { x -> x LT value }
infix fun <T> Expression<T>.LT(value: T?): Expression<Boolean> = FilterBy.value(this, BooleanOperator.LT, value)
infix fun <T> Expression<T>.LT(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.LT, value)

fun <T> LTE(value: T?): PartialBooleanExpression<T> = { x -> x LTE value }
fun <T> LTE(value: Expression<T>): PartialBooleanExpression<T> = { x -> x LTE value }
infix fun <T> Expression<T>.LTE(value: T?): Expression<Boolean> = FilterBy.value(this, BooleanOperator.LTE, value)
infix fun <T> Expression<T>.LTE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.LTE, value)

fun <T> IN(value: Array<T>): PartialBooleanExpression<T> = { x -> x IN value }
fun <T> IN(value: Collection<T>): PartialBooleanExpression<T> = { x -> x IN value }
fun <T> IN(value: Expression<List<T>>): PartialBooleanExpression<T> = { x -> x IN value }
infix fun <T> Expression<T>.IN(value: Array<T>): Expression<Boolean> = IN(value.toList())
infix fun <T> Expression<T>.IN(value: Collection<T>): Expression<Boolean> = FilterBy.value(this, BooleanOperator.IN, value)
infix fun <T> Expression<T>.IN(value: Expression<List<T>>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.IN, value)

fun <T> NOT_IN(value: Array<T>): PartialBooleanExpression<T> = { x -> x NOT_IN value }
fun <T> NOT_IN(value: Collection<T>): PartialBooleanExpression<T> = { x -> x NOT_IN value }
fun <T> NOT_IN(value: Expression<List<T>>): PartialBooleanExpression<T> = { x -> x NOT_IN value }
infix fun <T> Expression<T>.NOT_IN(value: Array<T>): Expression<Boolean> = NOT_IN(value.toList())
infix fun <T> Expression<T>.NOT_IN(value: Collection<T>): Expression<Boolean> = FilterBy.value(this, BooleanOperator.NOT_IN, value)
infix fun <T> Expression<T>.NOT_IN(value: Expression<List<T>>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.NOT_IN, value)

infix fun <T> Expression<T>.LIKE(value: String): Expression<Boolean> = FilterBy.value(this, BooleanOperator.LIKE, value)
infix fun <T> Expression<T>.LIKE(value: Expression<String>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.LIKE, value)

infix fun <T> Expression<T>.REGEX(value: String): Expression<Boolean> = FilterBy.value(this, BooleanOperator.REGEX, value)
infix fun <T> Expression<T>.REGEX(value: Expression<String>): Expression<Boolean> = FilterBy.expr(this, BooleanOperator.REGEX, value)

infix fun Expression<Boolean>.AND(value: Boolean): Expression<Boolean> = FilterLogic(this, LogicOperator.AND, value.aql)
infix fun Expression<Boolean>.AND(value: Expression<Boolean>): Expression<Boolean> = FilterLogic(this, LogicOperator.AND, value)

infix fun Expression<Boolean>.OR(value: Boolean): Expression<Boolean> = FilterLogic(this, LogicOperator.OR, value.aql)
infix fun Expression<Boolean>.OR(value: Expression<Boolean>): Expression<Boolean> = FilterLogic(this, LogicOperator.OR, value)

fun Expression<Boolean>.NOT(): Expression<Boolean> = AqlFunc.NOT.boolCall(this)
