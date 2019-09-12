package de.peekandpoke.karango

import de.peekandpoke.karango.aql.AqlBuilder
import de.peekandpoke.karango.aql.AqlPrinter
import de.peekandpoke.karango.aql.RootExpression
import de.peekandpoke.karango.aql.TerminalExpr


data class TypedQuery<T>(val ret: TerminalExpr<T>, val aql: String, val vars: Map<String, Any?>)

fun <T> query(builder: AqlBuilder.() -> TerminalExpr<T>): TypedQuery<T> {

    val root = RootExpression.from(builder)

    val query = AqlPrinter().append(root).build()

    return TypedQuery(root, query.query, query.vars)
}
