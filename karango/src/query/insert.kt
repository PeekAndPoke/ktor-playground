package de.peekandpoke.karango.query

import de.peekandpoke.karango.CollectionDefinition
import de.peekandpoke.karango.Expression
import de.peekandpoke.karango.NamedExpression

class InsertPreStage<T>(val what: NamedExpression<T>)

internal class InsertInto<T>(private val expr: NamedExpression<T>, private val collection: CollectionDefinition<T>) : Expression<T> {

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) =
        p.append("INSERT ").iterator(expr).append(" INTO ").name(collection).appendLine()
}

