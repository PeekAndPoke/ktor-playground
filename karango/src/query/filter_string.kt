@file:Suppress("FunctionName")

package de.peekandpoke.karango.query

import de.peekandpoke.karango.Expression
import de.peekandpoke.karango.NamedExpression

internal data class FilterContainsByValue(val expr: NamedExpression<*>, val search: String) : FilterPredicate {

    override fun printAql(p: AqlPrinter) =
        p.append("CONTAINS(").append(expr).append(", ").value(expr, search).append(")")
}

internal data class FilterContainsByNamed(val expr: NamedExpression<*>, val search: Expression<String>) : FilterPredicate {
    
    override fun printAql(p: AqlPrinter) {
        p.append("CONTAINS(").append(expr).append(", ").append(search).append(")")
    }
}

infix fun NamedExpression<String>.CONTAINS(value: String): FilterPredicate = FilterContainsByValue(this, value)
infix fun NamedExpression<String>.CONTAINS(value: Expression<String>): FilterPredicate = FilterContainsByNamed(this, value)


