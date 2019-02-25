@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

internal class Filter(private val predicate: Expression<Boolean>) : Statement {
    override fun printAql(p: AqlPrinter) = p.append("FILTER ").append(predicate).appendLine()
}

internal data class FilterBy<L, R>(val left: Expression<L>, val op: BooleanOperator, val right: Expression<R>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean

    companion object {
        fun <XL, XR> value(left: Expression<XL>, op: BooleanOperator, right: XR) =
            FilterBy(left, op, ValueExpression(left, right as Any))

        fun <XL, XR> expr(left: Expression<XL>, op: BooleanOperator, right: Expression<XR>) =
            FilterBy(left, op, right)
    }

    override fun printAql(p: AqlPrinter) = p.append(left).append(" ${op.op} ").append(right)
}

internal data class FilterLogic(val left: Expression<Boolean>, val op: LogicOperator, val right: Expression<Boolean>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean

    override fun printAql(p: AqlPrinter) = p.append("(").append(left).append(" ${op.op} ").append(right).append(")")
}
