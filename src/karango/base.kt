package de.peekandpoke.karango

import de.peekandpoke.karango.query.QueryPrinter

interface Statement {
    fun print(printer: QueryPrinter)
}

internal class Name(private val name: String) : Statement {
    override fun print(printer: QueryPrinter) {
        printer.append(name)
    }
}

interface Named<T> {
    fun getRealName() : String
    fun getQueryName() : String
}

interface Iteratable<T> : Named<T> {
    fun getReturnType() : Class<T>
}
