package de.peekandpoke.karango.aql

class IterableLet<T>(name: String, private val value: Collection<T>, type: TypeRef<T>) : Statement {

    private val lName = "l_$name"

    private val expr: IterableExpression<T> = IterableExpressionImpl("l_$name", type)

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(lName).append(" = ").value(lName, value as Any).appendLine()
}

class ScalarLet<T>(name: String, private val value: T, type: TypeRef<T>) : Statement {

    private val lName = "l_$name"
    
    private val expr: Expression<T> = ExpressionImpl(lName, type)

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(lName).append(" = ").value(lName, value as Any).appendLine()
}

