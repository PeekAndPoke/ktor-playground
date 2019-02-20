package de.peekandpoke.karango.aql

interface Printable {
    fun printAql(p: AqlPrinter): Any
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
        .map { it.tick() }
        .joinToString(".")

    private fun String.tick() = "`${replace("`", "?")}`"

    fun build() = Result(stringBuilder.toString(), queryVars)

    fun append(printable: Printable) = apply { printable.printAql(this) }

    fun append(printables: List<Printable>) = apply { printables.forEach { append(it) } }

    fun name(name: String) = append(name.toName())

    fun value(name: String, value: Any) = apply {

        val key = name.toParamName() + "_" + (queryVars.size + 1)

        append("@$key")
        queryVars[key] = value
    }

    fun join(args: List<Expression<*>>, delimiter: String = ", ") = apply {

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

        cb()

        indent = indent.substring(0, Math.max(0, indent.length - 4))
    }
}
