package de.peekandpoke.karango.query

import de.peekandpoke.karango.NamedType
import de.peekandpoke.karango.Statement

class QueryPrinter {

    data class Result(val query: String, val vars: Map<String, Any>)

    private val stringBuilder = StringBuilder()
    private val queryVars = mutableMapOf<String, Any>()

    private var indent = ""
    private var newLine = true

    fun build() = Result(stringBuilder.toString(), queryVars)

    fun value(named: NamedType<*>, value: Any) = value(named.getQueryName().replace(".", "__"), value)

    fun value(name: String, value: Any) = apply {

        val key = name + "_" + (queryVars.size + 1)

        append("@$key")
        queryVars[key] = value
    }

    fun append(str: String) = apply {

        if (newLine) {
            stringBuilder.append(indent)
            newLine = false
        }

        stringBuilder.append(str)
    }

    fun append(statement: Statement) = apply { statement.print(this) }

    fun appendAll(all: List<Statement>) = apply { all.forEach { append(it) } }

    fun appendLine(str: String = "") = apply {
        append(str)
        stringBuilder.appendln()

        newLine = true
    }

    fun indent(cb: QueryPrinter.() -> Unit) {

        indent += "    "

        cb()

        indent = indent.substring(0, Math.max(0, indent.length - 4))
    }
}
