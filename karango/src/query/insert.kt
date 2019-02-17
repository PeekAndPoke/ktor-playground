package de.peekandpoke.karango.query

import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.NamedExpression
import de.peekandpoke.karango.Statement

class InsertPreStage<T>(val what: NamedExpression<T>)

internal class InsertInto<T>(private val expr: NamedExpression<T>, private val collection: CollectionDefinition<T>) : Statement<T> {

    override fun getReturnType() = collection.getReturnType()

    override fun printStmt(p: AqlPrinter) =
        p.append("INSERT ").identifier(expr).append(" INTO ").name(collection).appendLine()
}

