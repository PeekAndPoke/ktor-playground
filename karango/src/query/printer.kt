package de.peekandpoke.karango.query

import de.peekandpoke.karango.Named

interface PrintableStatement {
    fun printStmt(p: AqlPrinter): Any
}

interface PrintableExpression {
    fun printExpr(p: AqlPrinter): Any
}

class AqlPrinter {

    data class Result(val query: String, val vars: Map<String, Any>)

    private val stringBuilder = StringBuilder()
    private val queryVars = mutableMapOf<String, Any>()

    private var indent = ""
    private var newLine = true

    private fun String.toParamName() = this
        .replace(".", "__")
        .replace("[*]", "_STAR")
        .replace("[**]", "_STAR2")
        .replace("[^a-zA-Z0-9_]".toRegex(), "_")

    private fun String.toName() = this
        .split(".")
        .joinToString(".") { it.replace("[^a-zA-Z0-9_\\[*\\] `]".toRegex(), "_") }

    fun build() = Result(stringBuilder.toString(), queryVars)

    fun expr(expr: PrintableExpression) = apply { expr.printExpr(this) }

    fun stmt(stmt: PrintableStatement) = apply { stmt.printStmt(this) }

    fun stmts(all: List<PrintableStatement>) = apply { all.forEach { stmt(it) } }

    fun identifier(name: Named) = name("i_${name.getName()}")
    
    fun name(name: Named) = name(name.getName())

    private fun name(name: String) = append(name.toName())

//    fun value(named: Queryable<*>, value: Any) = value(named.getQueryName(), value)

    fun value(expr: Any, value: Any) = apply {
        if (expr is Named) {
            value(expr.getName(), value)
        } else {
            value("v", value)
        }
    }

    fun value(name: String, value: Any) = apply {

        val key = name.toParamName() + "_" + (queryVars.size + 1)

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


    fun appendLine(str: String = "") = apply {
        append(str)
        stringBuilder.appendln()

        newLine = true
    }

    fun indent(cb: AqlPrinter.() -> Unit) {

        indent += "    "

        cb()

        indent = indent.substring(0, Math.max(0, indent.length - 4))
    }
}
