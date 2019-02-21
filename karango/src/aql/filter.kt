@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

internal class Filter(private val predicate: Expression<Boolean>) : Statement {
    override fun printAql(p: AqlPrinter) = p.append("FILTER ").append(predicate).appendLine()
}

internal data class FilterBy<L, R>(val left: Expression<L>, val op: Operator, val right: Expression<R>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean

    companion object {
        fun <XL, XR> value(left: Expression<XL>, op: Operator, right: XR) =
            FilterBy(left, op, ValueExpr(left, right as Any))

        fun <XL, XR> expr(left: Expression<XL>, op: Operator, right: Expression<XR>) =
            FilterBy(left, op, right)
    }

    override fun printAql(p: AqlPrinter) = p.append(left).append(" ${op.op} ").append(right)
}

internal data class FilterLogic(val left: Expression<Boolean>, val op: Logic, val right: Expression<Boolean>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean

    override fun printAql(p: AqlPrinter) = p.append("(").append(left).append(" ${op.op} ").append(right).append(")")
}

typealias PartialBooleanExpression<T> = (Expression<T>) -> Expression<Boolean>

internal data class FilterArray<L>(val left: Expression<L>, val op: ArrayOp, val right: PartialBooleanExpression<L>) : Expression<Boolean> {

    /** Resolve the right side from the partial filter */
    private val resolved = (right(left) as FilterBy<*, *>)

    override fun getType() = TypeRef.Boolean

    override fun printAql(p: AqlPrinter) =
        p.append(left).append(" ${op.op} ${resolved.op.op} ").append(resolved.right)
}

