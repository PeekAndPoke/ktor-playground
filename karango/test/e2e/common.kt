package de.peekandpoke.karango.e2e

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.KarangoDriver
import de.peekandpoke.karango.aql.Expression
import de.peekandpoke.karango.aql.print
import de.peekandpoke.karango.karangoDefaultDriver
import de.peekandpoke.karango.testdomain.TestPersonsRepository
import de.peekandpoke.ultra.common.surround
import de.peekandpoke.ultra.vault.Vault
import io.kotlintest.TestContext
import io.kotlintest.matchers.withClue

private val arangoDb: ArangoDB = ArangoDB.Builder().user("root").password("").host("localhost", 8529).build()
private val arangoDatabase: ArangoDatabase = arangoDb.db("_system")

lateinit var driver: KarangoDriver

private val databaseBlueprint: Vault.Blueprint = Vault.setup {
    add { TestPersonsRepository(it.get(karangoDefaultDriver)) }
}

val database = databaseBlueprint.with { database ->

    driver = KarangoDriver(database, arangoDatabase)

    listOf(
        karangoDefaultDriver to driver
    )
}

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
