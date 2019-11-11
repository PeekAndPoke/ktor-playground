@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.ultra.vault.TypeRef
import de.peekandpoke.ultra.vault.kType

@KarangoDslMarker
fun <T> StatementBuilder.LET(name: String, value: Expression<T>): Expression<T> = LetExpr(name, value).addStmt().toExpression()

@KarangoDslMarker
fun StatementBuilder.LET(name: String, @Suppress("UNUSED_PARAMETER") value: Nothing?): Expression<Any?> = LET(name, NullValue())

@KarangoDslMarker
inline fun <reified T> StatementBuilder.LET(name: String, value: T): Expression<T> = Let(name, value, kType()).addStmt().toExpression()

@KarangoDslMarker
inline fun <reified T> StatementBuilder.LET(name: String, builder: () -> T): Expression<T> = Let(name, builder(), kType()).addStmt().toExpression()

/**
 * Let statement created from a user value
 */
class Let<T>(name: String, private val value: T, type: TypeRef<T>) : Statement {

    private val lName = "l_$name"

    private val expr: Expression<T> = ExpressionImpl(lName, type)

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(lName).append(" = ").value(lName, value as Any).appendLine()
}

/**
 * Let statement created from an expression
 */
class LetExpr<T>(name: String, private val value: Expression<T>) : Statement {

    private val lName = "l_$name"

    private val expr: Expression<T> = ExpressionImpl(lName, value.getType())

    fun toExpression() = expr

    override fun printAql(p: AqlPrinter) =
        p.append("LET ").name(lName).append(" = ").append(value).appendLine()
}
