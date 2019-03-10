package de.peekandpoke.karango.aql

import com.fasterxml.jackson.databind.ObjectMapper

interface Printable {
    fun printAql(p: AqlPrinter): Any
}

fun Printable.toPrintableString() = AqlPrinter.sandbox(this)

fun Printable.toPrinterResult() = AqlPrinter.sandboxQuery(this)

class AqlPrinter {

    data class Result(val query: String, val vars: Map<String, Any?>) {

        /**
         * Returns the raw text query, by replacing the parameters (like @my_param) with actual values.
         * 
         * This is a debugging helper and used in the unit tests.
         */
        val raw : String get() {
            
            var result = query
            
            vars.forEach { key, value -> 
                result = result.replace("@$key", toJsonMapper.writeValueAsString(value)) 
            }
            
            return result
        }
    }

    companion object {
        fun sandbox(expr: Printable): String = sandboxQuery(expr).query
        fun sandboxQuery(expr: Printable): Result = AqlPrinter().append(expr).build()

        private val toJsonMapper = ObjectMapper()
    }

    private val stringBuilder = StringBuilder()
    private val queryVars = mutableMapOf<String, Any?>()

    private var indent = ""
    private var newLine = true

    private fun String.toParamName() = this
        .replace("`", "")
        .replace(".", "__")
        .replace("[*]", "_STAR")
        .replace("[**]", "_STAR2")
        .replace("[^a-zA-Z0-9_]".toRegex(), "_")

    private fun String.toName() = split(".").joinToString(".") { it.tick() }

    private fun String.tick() = "`${replace("`", "?")}`"

    fun build() = Result(stringBuilder.toString(), queryVars)

    fun append(printable: Printable) = apply { printable.printAql(this) }

    fun append(printables: List<Printable>) = apply { printables.forEach { append(it) } }

    fun name(name: String) = append(name.toName())

    fun value(expr: Expression<*>, value: Any) = value(if (expr is Aliased) expr.getAlias() else "v", value)

    fun value(name: String, value: Any?) = apply {

        val key = name.toParamName() + "_" + (queryVars.size + 1)

        append("@$key")
        queryVars[key] = value
    }

    fun join(args: List<Expression<*>>, delimiter: String = ", ") = join(args.toTypedArray(), delimiter)

    fun join(args: Array<out Expression<*>>, delimiter: String = ", ") = apply {

        args.forEachIndexed { idx, a ->

            append(a)

            if (idx < args.size - 1) {
                append(delimiter)
            }
        }
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

        this.cb()

        indent = indent.substring(0, Math.max(0, indent.length - 4))
    }
}
