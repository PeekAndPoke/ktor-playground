package de.peekandpoke.karango.vault

import com.arangodb.ArangoCursor
import com.arangodb.ArangoDBException
import com.arangodb.ArangoDatabase
import com.arangodb.entity.CollectionType
import com.arangodb.model.AqlQueryOptions
import com.arangodb.model.CollectionCreateOptions
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.InjectableValues
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.convertValue
import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.CursorImpl
import de.peekandpoke.karango.KarangoQueryException
import de.peekandpoke.karango.TypedQuery
import de.peekandpoke.karango.aql.AqlBuilder
import de.peekandpoke.karango.aql.TerminalExpr
import de.peekandpoke.karango.jackson.KarangoJacksonModule
import de.peekandpoke.ultra.vault.*
import de.peekandpoke.ultra.vault.hooks.OnSaveHook
import de.peekandpoke.ultra.vault.jackson.VaultJacksonModule
import de.peekandpoke.ultra.vault.profiling.QueryProfiler
import kotlin.system.measureTimeMillis

class KarangoDriver(
    private val database: Database,
    private val arangoDb: ArangoDatabase,
    private val onSaveHooks: List<OnSaveHook> = listOf(),
    private val entityCache: EntityCache = NullEntityCache(),
    private val profiler: QueryProfiler
) {

    /**
     * The object mapper used for serializing queries
     */
    private val serializer = ObjectMapper().apply {
        // default modules
        registerModule(KotlinModule())
        registerModule(Jdk8Module())

        // Vault specific
        registerModule(VaultJacksonModule())

        // Karango specific
        registerModule(KarangoJacksonModule())

        // serialization features
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)

        // deserialization features
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        injectableValues = InjectableValues.Std(
            mapOf(
                "database" to database,
                "mapper" to this,
                "entityCache" to entityCache
            )
        )
    }

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

        return profiler.add(
            connection = "Arango::${arangoDb.arango().db().name()}",
            queryLanguage = "aql",
            query = query.aql,
            vars = query.vars
        ) {
            val options = AqlQueryOptions().count(true)
            val params = serializer.convertValue<Map<String, Any>>(query.vars)

            lateinit var result: ArangoCursor<*>

            println(query)
//        println(query.ret.innerType())
            println(params)

            val time = measureTimeMillis {
                try {
                    result = arangoDb.query(query.aql, params, options, Object::class.java)
                } catch (e: ArangoDBException) {
                    throw KarangoQueryException(query, "Error while querying '${e.message}':\n\n${query.aql}\nwith params\n\n$params", e)
                }
            }

            // return the cursor
            CursorImpl(result, query, time, serializer)
        }
    }

    fun <X> applyOnSaveHooks(repo: Repository<X>, storable: Storable<X>): Storable<X> {
        return onSaveHooks.fold(storable) { acc, hook -> hook.apply(repo, acc) }
    }
}
