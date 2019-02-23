package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

class InsertPreStage<T> internal constructor(val what: Expression<T>)

class InsertInto<T> internal constructor(
    private val expr: Expression<T>,
    private val collection: CollectionDefinition<T>
) : TerminalExpr<T> {

    override fun innerType() = expr.getType()

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) =
        p.append("INSERT ").append(expr).append(" INTO ").append(collection).appendLine()
}

