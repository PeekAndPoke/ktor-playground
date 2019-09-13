package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.Db
import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.aql.Expression
import de.peekandpoke.karango.aql.print
import de.peekandpoke.ultra.common.surround
import io.kotlintest.TestContext
import io.kotlintest.matchers.withClue

val db = Db.default(user = "root", pass = "", host = "localhost", port = 8529, database = "_system")

@Karango
data class E2ePerson(val name: String, val age: Int)

@Suppress("unused")
fun <T> TestContext.withClue(expr: Expression<T>, expected: Any?, block: () -> Any) {

    val printerResult = expr.print()

    return withClue(
        listOf(
            "Type:     $expr",
            "AQL:      ${printerResult.query}",
            "Vars:     ${printerResult.vars}",
            "Readable: ${printerResult.raw}",
            "Expected: $expected"
        ).joinToString("\n").surround("\n"),
        block
    )
}
