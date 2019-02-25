package de.peekandpoke.karango.aql

enum class ArithmeticOperator(val op: String) {
    PLUS("+"),
    MINUS("-"),
    MUL("*"),
    DIV("/"),
    REM("%"),
}

internal class ArithmeticExpression<L, R>(
    private val left: Expression<L>,
    private val op: ArithmeticOperator,
    private val right: Expression<R>
) : Expression<Number> {

    override fun getType() = TypeRef.Number

    override fun printAql(p: AqlPrinter) =
        p.append("(").append(left).append(" ${op.op} ").append(right).append(")")

}

operator fun <L, R> Expression<L>.plus(right: Expression<R>): Expression<Number> = ArithmeticExpression(this, ArithmeticOperator.PLUS, right)
operator fun <L> Expression<L>.plus(right: Number): Expression<Number> = this + right.aql()
operator fun <R> Number.plus(right: Expression<R>): Expression<Number> = this.aql() + right

operator fun <L, R> Expression<L>.minus(right: Expression<R>): Expression<Number> = ArithmeticExpression(this, ArithmeticOperator.MINUS, right)
operator fun <L> Expression<L>.minus(right: Number): Expression<Number> = this - right.aql()
operator fun <R> Number.minus(right: Expression<R>): Expression<Number> = this.aql() - right

operator fun <L, R> Expression<L>.times(right: Expression<R>): Expression<Number> = ArithmeticExpression(this, ArithmeticOperator.MUL, right)
operator fun <L> Expression<L>.times(right: Number): Expression<Number> = this * right.aql()
operator fun <R> Number.times(right: Expression<R>): Expression<Number> = this.aql() * right

operator fun <L, R> Expression<L>.div(right: Expression<R>): Expression<Number> = ArithmeticExpression(this, ArithmeticOperator.DIV, right)
operator fun <L> Expression<L>.div(right: Number): Expression<Number> = this / right.aql()
operator fun <R> Number.div(right: Expression<R>): Expression<Number> = this.aql() / right

operator fun <L, R> Expression<L>.rem(right: Expression<R>): Expression<Number> = ArithmeticExpression(this, ArithmeticOperator.REM, right)
operator fun <L> Expression<L>.rem(right: Number): Expression<Number> = this % right.aql()
operator fun <R> Number.rem(right: Expression<R>): Expression<Number> = this.aql() % right
