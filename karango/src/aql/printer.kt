package de.peekandpoke.karango.aql

import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.math.max

/**
 * Marks a class printable by the [AqlPrinter]
 */
interface Printable {
    fun printAql(p: AqlPrinter): Any
}

/**
 * Prints the raw query, with all parameter value included
 */
fun Printable.printRawQuery() = AqlPrinter.raw(this)

/**
 * Prints the query, with placeholders for parameters
 */
fun Printable.printQuery() = AqlPrinter.sandbox(this)

/**
 * Prints the query and returns a [AqlPrinter.Result]
 */
fun Printable.print() = AqlPrinter.sandboxQuery(this)

/**
 * The Aql printer
 */
class AqlPrinter {

    /**
     * Printer result
     */
    data class Result(val query: String, val vars: Map<String, Any?>) {

        /**
         * The raw text query with parameters (like @my_param) replaced with actual values.
         *
         * This is a debugging helper and e.g. used in the unit tests.
         */
        val raw: String by lazy(LazyThreadSafetyMode.NONE) {

            vars.entries.fold(query) { acc, (key, value) ->
                acc.replace("@$key", jsonPrinter.writeValueAsString(value))
            }
        }
    }

    /**
     * Internal static helpers
     */
    companion object {
        private val jsonPrinter = ObjectMapper().writerWithDefaultPrettyPrinter()

        internal fun raw(expr: Printable): String = sandboxQuery(expr).raw
        internal fun sandbox(expr: Printable): String = sandboxQuery(expr).query
        internal fun sandboxQuery(expr: Printable): Result = AqlPrinter().append(expr).build()
    }

    private val stringBuilder = StringBuilder()
    private val queryVars = mutableMapOf<String, Any?>()

    private var indent = ""
    private var newLine = true

    /**
     * Build the [Result]
     */
    fun build() = Result(stringBuilder.toString(), queryVars)

    /**
     * Append a printable
     */
    fun append(printable: Printable) = apply { printable.printAql(this) }

    /**
     * Appends a list of printables
     */
    fun append(printables: List<Printable>) = apply { printables.forEach { append(it) } }

    /**
     * Appends a name
     */
    fun name(name: String) = append(name.toName())

    /**
     * Appends a user value (e.g. obtained by "abc".aql("paramName")
     *
     * If the given expression is an instance of Aliased, then the name for the user parameter will be taken from it.
     * Otherwise the name will default to "v".
     */
    fun value(expr: Expression<*>, value: Any?) = value(if (expr is Aliased) expr.getAlias() else "v", value)

    /**
     * Appends a user value with the given parameter name
     */
    fun value(parameterName: String, value: Any?) = apply {

        val key = parameterName.toParamName() + "_" + (queryVars.size + 1)

        append("@$key")
        queryVars[key] = value
    }

    /**
     * Appends multiple expression and put the delimiter between each of them
     */
    fun join(args: List<Expression<*>>, delimiter: String = ", ") = join(args.toTypedArray(), delimiter)

    /**
     * Appends multiple expression and put the delimiter between each of them
     */
    fun join(args: Array<out Expression<*>>, delimiter: String = ", ") = apply {

        args.forEachIndexed { idx, a ->

            append(a)

            if (idx < args.size - 1) {
                append(delimiter)
            }
        }
    }

    /**
     * Appends a string
     */
    fun append(str: String) = apply {

        if (newLine) {
            stringBuilder.append(indent)
            newLine = false
        }

        stringBuilder.append(str)
    }

    /**
     * Appends a string followed by a line break
     */
    fun appendLine(str: String = "") = apply {

        append(str)
        stringBuilder.appendln()

        newLine = true
    }

    /**
     * Increases the indent for everything added by the [block]
     */
    fun indent(block: AqlPrinter.() -> Unit) {

        indent += "    "

        this.block()

        indent = indent.substring(0, max(0, indent.length - 4))
    }

    /**
     * Cleans up parameter names
     */
    private fun String.toParamName() = this
        .replace("`", "")
        .replace(".", "__")
        .replace("[*]", "_STAR")
        .replace("[**]", "_STAR2")
        .replace("[^a-zA-Z0-9_]".toRegex(), "_")

    /**
     * Ensures that all names are surrounded by ticks
     */
    private fun String.toName() = split(".").joinToString(".") { it.tick() }

    /**
     * Surround a string with ticks and replaces all inner ticks with a "?"
     */
    private fun String.tick() = "`${replace("`", "?")}`"
}
