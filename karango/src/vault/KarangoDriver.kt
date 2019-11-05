package de.peekandpoke.karango.vault

import com.arangodb.ArangoDBException
import com.arangodb.ArangoDatabase
import com.arangodb.entity.CollectionType
import com.arangodb.model.AqlQueryOptions
import com.arangodb.model.CollectionCreateOptions
import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.CursorImpl
import de.peekandpoke.karango.KarangoQueryException
import de.peekandpoke.karango.TypedQuery
import de.peekandpoke.karango.aql.AqlBuilder
import de.peekandpoke.karango.aql.TerminalExpr
import de.peekandpoke.karango.slumber.KarangoCodec
import de.peekandpoke.ultra.logging.Log
import de.peekandpoke.ultra.logging.NullLog
import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.hooks.OnSaveHook
import de.peekandpoke.ultra.vault.profiling.NullQueryProfiler
import de.peekandpoke.ultra.vault.profiling.QueryProfiler

class KarangoDriver(
    private val arangoDb: ArangoDatabase,
    private val codec: KarangoCodec,
    private val log: Log = NullLog(),
    private val onSaveHooks: List<OnSaveHook> = listOf(),
    private val profiler: QueryProfiler = NullQueryProfiler()
) {
    fun ensureEntityCollection(
        name: String,
        options: CollectionCreateOptions = CollectionCreateOptions().type(CollectionType.DOCUMENT)
    ) {

        val arangoColl = arangoDb.collection(name)

        if (!arangoColl.exists()) {
            arangoDb.createCollection(name, options)
        }
    }

    /**
     * Performs a query and returns a cursor of results
     */
    fun <X> query(builder: AqlBuilder.() -> TerminalExpr<X>): Cursor<X> = query(de.peekandpoke.karango.query(builder))

    /**
     * Performs the query
     */
    fun <T> query(query: TypedQuery<T>): Cursor<T> {

        return profiler.profile(
            connection = "Arango::${arangoDb.arango().db().name()}",
            queryLanguage = "aql",
            query = query.aql
        ) { entry ->

            // TODO: nicer interface that avoid the type-cast here
            @Suppress("UNCHECKED_CAST")
            val vars = entry.measureSerializer { codec.slumber(query.vars) } as Map<String, Any>

            entry.vars = vars

            log.debug("Arango query:\n${query.aql}\nVars:\n${vars}\n")

            val options = AqlQueryOptions().count(true)

            val result = entry.measureQuery {
                try {
                    arangoDb.query(query.aql, vars, options, Object::class.java)
                } catch (e: ArangoDBException) {
                    throw KarangoQueryException(query, "Error while querying '${e.message}':\n\n${query.aql}\nwith params\n\n$vars", e)
                }
            }

            // return the cursor
            CursorImpl(result, query, codec, entry)
        }
    }

    fun <X> applyOnSaveHooks(repo: Repository<X>, storable: Storable<X>): Storable<X> {
        return onSaveHooks.fold(storable) { acc, hook -> hook.apply(repo, acc) }
    }
}
