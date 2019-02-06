package de.peekandpoke.karango.query

import de.peekandpoke.karango.Column

class QueryPrinter {

    data class Result(val query: String, val vars: Map<String, Any>)

    private val stringBuilder = StringBuilder()
    private val queryVars = mutableMapOf<String, Any>()

    private var indent = ""
    private var newLine = true;

    fun build() = Result(stringBuilder.toString(), queryVars)

    fun value(column: Column<*>, value: Any) = apply {

        val key = column.fqn.replace(".", "__") + "_" + (queryVars.size + 1)

        append("@$key")
        queryVars[key] = value
    }

    fun append(str: String) = apply {

        if (newLine) {
            stringBuilder.append(indent)
            newLine = false
        }

        stringBuilder.append(str);
    }

    fun append(expression: Expression) = apply { expression.print(this) }

    fun appendAll(all: List<Expression>) = apply { all.forEach { append(it) }}

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
