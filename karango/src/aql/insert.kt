package de.peekandpoke.karango.aql

import de.peekandpoke.karango.CollectionDefinition

class InsertPreStage<T>(val what: Expression<T>)

internal class InsertInto<T>(private val expr: Expression<T>, private val collection: CollectionDefinition<T>) : Expression<T> {

    override fun getType() = collection.getType()

    override fun printAql(p: AqlPrinter) =
        p.append("INSERT ").append(expr).append(" INTO ").append(collection).appendLine()
}

