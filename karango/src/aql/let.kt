package de.peekandpoke.karango.aql

class IterableLet<T>(name: String, private val value: Collection<T>, type: TypeRef<T>) : Statement {

    private val expr: NamedIterableExpression<T> = NamedIterableExpressionImpl("l_$name", type)

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(expr).append(" = ").value(this, value as Any).appendLine()
}

class ScalarLet<T>(name: String, private val value: T, type: TypeRef<T>) : Statement {

    private val expr: NamedExpression<T> = NamedExpressionImpl("l_$name", type)

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(expr).append(" = ").value(this, value as Any).appendLine()
}

