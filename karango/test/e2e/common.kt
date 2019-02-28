package de.peekandpoke.karango.e2e

import com.fasterxml.jackson.databind.ObjectMapper
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.aql.Expression
import de.peekandpoke.karango.aql.surround
import de.peekandpoke.karango.aql.toPrinterResult
import io.kotlintest.TestContext
import io.kotlintest.matchers.withClue

val db = Db.default(user = "root", pass = "root", host = "localhost", port = 8529, database = "kotlindev")

data class X(val name: String, val age: Int)

private val toJsonMapper = ObjectMapper()

@Suppress("unused")
fun <T> TestContext.withClue(expr: Expression<T>, expected: Any?, thunk: () -> Any) {

    return withClue(
        listOf(
            "Type:       $expr", 
            "Expression: ${expr.toPrinterResult().raw}",
            "Expected:   $expected"
        ).joinToString("\n").surround("\n"),
        thunk
    )
}
