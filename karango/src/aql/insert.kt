package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

class InsertPreStage<T>(val what: NamedExpression<T>)

internal class InsertInto<T>(private val expr: NamedExpression<T>, private val collection: CollectionDefinition<T>) : Expression<T> {

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) =
        p.append("INSERT ").iterator(expr).append(" INTO ").name(collection).appendLine()
}

