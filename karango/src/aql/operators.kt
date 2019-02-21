@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

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

internal enum class ArrayOp(val op: String) {
    ANY("ANY"),
    NONE("NONE"),
    ALL("ALL"),
}

internal enum class Logic(val op: String) {
    AND("AND"),
    OR("OR"),
}

infix fun <T> Expression<T>.ANY(partial: PartialBooleanExpression<T>): Expression<Boolean> = FilterArray(this, ArrayOp.ANY, partial)
infix fun <T> Expression<T>.NONE(partial: PartialBooleanExpression<T>): Expression<Boolean> = FilterArray(this, ArrayOp.NONE, partial)
infix fun <T> Expression<T>.ALL(partial: PartialBooleanExpression<T>): Expression<Boolean> = FilterArray(this, ArrayOp.ALL, partial)

fun <T> EQ(value: T): PartialBooleanExpression<T> = { x -> x EQ value }
fun <T> EQ(value: Expression<T>): PartialBooleanExpression<T> = { x -> x EQ value }
infix fun <T> Expression<T>.EQ(value: T): Expression<Boolean> = FilterBy.value(this, Operator.EQ, value)
infix fun <T> Expression<T>.EQ(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.EQ, value)

fun <T> NE(value: T): PartialBooleanExpression<T> = { x -> x NE value }
fun <T> NE(value: Expression<T>): PartialBooleanExpression<T> = { x -> x NE value }
infix fun <T> Expression<T>.NE(value: T): Expression<Boolean> = FilterBy.value(this, Operator.NE, value)
infix fun <T> Expression<T>.NE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.NE, value)

fun <T> GT(value: T): PartialBooleanExpression<T> = { x -> x GT value }
fun <T> GT(value: Expression<T>): PartialBooleanExpression<T> = { x -> x GT value }
infix fun <T> Expression<T>.GT(value: T): Expression<Boolean> = FilterBy.value(this, Operator.GT, value)
infix fun <T> Expression<T>.GT(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.GT, value)

fun <T> GTE(value: T): PartialBooleanExpression<T> = { x -> x GTE value }
fun <T> GTE(value: Expression<T>): PartialBooleanExpression<T> = { x -> x GTE value }
infix fun <T> Expression<T>.GTE(value: T): Expression<Boolean> = FilterBy.value(this, Operator.GTE, value)
infix fun <T> Expression<T>.GTE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.GTE, value)

fun <T> LT(value: T): PartialBooleanExpression<T> = { x -> x LT value }
fun <T> LT(value: Expression<T>): PartialBooleanExpression<T> = { x -> x LT value }
infix fun <T> Expression<T>.LT(value: T): Expression<Boolean> = FilterBy.value(this, Operator.LT, value)
infix fun <T> Expression<T>.LT(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.LT, value)

fun <T> LTE(value: T): PartialBooleanExpression<T> = { x -> x LTE value }
fun <T> LTE(value: Expression<T>): PartialBooleanExpression<T> = { x -> x LTE value }
infix fun <T> Expression<T>.LTE(value: T): Expression<Boolean> = FilterBy.value(this, Operator.LTE, value)
infix fun <T> Expression<T>.LTE(value: Expression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.LTE, value)

fun <T> IN(value: Array<T>): PartialBooleanExpression<T> = { x -> x IN value }
fun <T> IN(value: Collection<T>): PartialBooleanExpression<T> = { x -> x IN value }
fun <T> IN(value: IterableExpression<T>): PartialBooleanExpression<T> = { x -> x IN value }
infix fun <T> Expression<T>.IN(value: Array<T>): Expression<Boolean> = IN(value.toList())
infix fun <T> Expression<T>.IN(value: Collection<T>): Expression<Boolean> = FilterBy.value(this, Operator.IN, value)
infix fun <T> Expression<T>.IN(value: IterableExpression<T>): Expression<Boolean> = FilterBy.expr(this, Operator.IN, value)

fun <T> NOT_IN(value: Array<T>): PartialBooleanExpression<T> = { x -> x NOT_IN value }
fun <T> NOT_IN(value: Collection<T>): PartialBooleanExpression<T> = { x -> x NOT_IN value }
fun <T> NOT_IN(value: IterableExpression<T>): PartialBooleanExpression<T> = { x -> x NOT_IN value }
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
