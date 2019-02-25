package de.peekandpoke.karango.aql

class Let<T>(name: String, private val value: T, type: TypeRef<T>) : Statement {

    private val lName = "l_$name"
    
    private val expr: Expression<T> = ExpressionImpl(lName, type)

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(lName).append(" = ").value(lName, value as Any).appendLine()
}

