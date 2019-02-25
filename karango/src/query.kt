package de.peekandpoke.karango

import de.peekandpoke.karango.aql.AqlBuilder
import de.peekandpoke.karango.aql.AqlPrinter
import de.peekandpoke.karango.aql.TerminalExpr


data class TypedQuery<T>(val ret: TerminalExpr<T>, val aql: String, val vars: Map<String, Any>)

fun <T> query(builder: AqlBuilder.() -> TerminalExpr<T>): TypedQuery<T> {

    val root = AqlBuilder()
    val ret = root.builder()

    val query = AqlPrinter().append(root).build()

    return TypedQuery(ret, query.query, query.vars)
}
