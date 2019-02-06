package de.peekandpoke.karango.query

interface Expression {
    fun print(printer: QueryPrinter)
}

internal class Name(private val name: String) : Expression {
    override fun print(printer: QueryPrinter) {
        printer.append(name)
    }
}

data class ReturnType<T>(val type: Class<T>)
