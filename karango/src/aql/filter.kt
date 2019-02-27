@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

data class Filter(private val predicate: Expression<Boolean>) : Statement {
    override fun printAql(p: AqlPrinter) = p.append("FILTER ").append(predicate).appendLine()
}

data class FilterBy<L, R>(val left: Expression<L>, val op: BooleanOperator, val right: Expression<R>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean
    override fun printAql(p: AqlPrinter) = p.append(left).append(" ${op.op} ").append(right)

    /**
     * Internal helper for projecting the potential input value alias of expr onto the value
     */
    internal data class ValueExpression(private val expr: Expression<*>, private val value: Any) : Expression<Any> {

        override fun getType() = TypeRef.Any
        override fun printAql(p: AqlPrinter): Any = p.value(expr, value)
    }

    companion object {

        fun <XL, XR> value(left: Expression<XL>, op: BooleanOperator, right: XR) =
            FilterBy(left, op, ValueExpression(left, right as Any))
        
        fun <XL, XR> expr(left: Expression<XL>, op: BooleanOperator, right: Expression<XR>) =
            FilterBy(left, op, right)
    }
}

data class FilterLogic(val left: Expression<Boolean>, val op: LogicOperator, val right: Expression<Boolean>) : Expression<Boolean> {

    override fun getType() = TypeRef.Boolean
    override fun printAql(p: AqlPrinter) = p.append("(").append(left).append(" ${op.op} ").append(right).append(")")
}
