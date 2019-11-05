package de.peekandpoke.karango.e2e

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.aql.Expression
import de.peekandpoke.karango.aql.print
import de.peekandpoke.karango.slumber.KarangoCodec
import de.peekandpoke.karango.testdomain.TestPersonsRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.common.SimpleLookup
import de.peekandpoke.ultra.common.TypedAttributes
import de.peekandpoke.ultra.common.surround
import de.peekandpoke.ultra.slumber.Config
import de.peekandpoke.ultra.slumber.builtin.BuiltInModule
import de.peekandpoke.ultra.slumber.builtin.DateTimeModule
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.DefaultEntityCache
import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.SharedRepoClassLookup
import de.peekandpoke.ultra.vault.slumber.VaultSlumberModule
import io.kotlintest.TestContext
import io.kotlintest.matchers.withClue

private val arangoDb: ArangoDB = ArangoDB.Builder().user("root").password("").host("localhost", 8529).build()
private val arangoDatabase: ArangoDatabase = arangoDb.db("_system")

fun createDatabase(): Pair<Database, KarangoDriver> {

    lateinit var db: Database
    lateinit var driver: KarangoDriver

    val repos = SimpleLookup<Repository<*>> {
        listOf(
            TestPersonsRepository(driver)
        )
    }


    db = Database(repos, SharedRepoClassLookup())

    val codec = KarangoCodec(
        Config(
            listOf(
                VaultSlumberModule,
                DateTimeModule,
                BuiltInModule
            )
        ),
        TypedAttributes.of {
            add(VaultSlumberModule.DatabaseKey, db)
            add(VaultSlumberModule.EntityCacheKey, DefaultEntityCache())
        }
    )

    driver = KarangoDriver(arangoDatabase, codec)

    return Pair(db, driver)
}

private val dbAndDriver = createDatabase()
val database: Database = dbAndDriver.first
val driver: KarangoDriver = dbAndDriver.second

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
