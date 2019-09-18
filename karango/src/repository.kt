package de.peekandpoke.karango

import com.arangodb.ArangoCursor
import com.arangodb.ArangoDBException
import com.arangodb.ArangoDatabase
import com.arangodb.model.AqlQueryOptions
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.InjectableValues
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.convertValue
import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.jackson.KarangoJacksonModule
import de.peekandpoke.ultra.vault.*
import kotlin.system.measureTimeMillis

val karangoDefaultDriver = Key<KarangoDriver>("karango_default_driver")

class KarangoDriver(
//    private val database: Database,
    private val arangoDb: ArangoDatabase,
    private val onSaveHooks: List<OnSaveHook> = listOf(),
    private val refCache: RefCache = RefCache()
) : Driver {

    /**
     * The object mapper used for serializing queries
     */
    private val serializer = ObjectMapper().apply {
        registerModule(KotlinModule())
        registerModule(Jdk8Module())
        // custom
        registerModule(KarangoJacksonModule())

        // serialization features
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)

        // deserialization features
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    /**
     * Performs the query
     */
    fun <T> query(query: TypedQuery<T>): Cursor<T> {

        val options = AqlQueryOptions().count(true)
        val params = serializer.convertValue<Map<String, Any>>(query.vars)

        lateinit var result: ArangoCursor<*>

        println(query)
//        println(query.ret.innerType())
//        println(mapped)

        val time = measureTimeMillis {
            try {
                result = arangoDb.query(query.aql, params, options, Object::class.java)
            } catch (e: ArangoDBException) {
                throw KarangoQueryException(query, "Error while querying '${e.message}':\n\n${query.aql}\nwith params\n\n$params", e)
            }
        }

        return CursorImpl(
            result,
            query,
            time,
            serializer.copy().apply {
                injectableValues = InjectableValues.Std(
                    mapOf(
                        "mapper" to this@apply,
//                        "database" to database,
                        "cache" to refCache
                    )
                )
            }
        )
    }
}

abstract class EntityRepository<T>(
    private val driver: KarangoDriver,
    private val coll: IEntityCollection<T>
) : Repository {

    /**
     * Finds all return them as [Stored] entities
     */
    fun findAll(): Cursor<Stored<T>> = find {
        FOR(coll) {
            RETURN(it)
        }
    }

    /**
     * Returns all results as [Stored] entities
     */
    fun find(builder: AqlBuilder.() -> TerminalExpr<T>): Cursor<Stored<T>> = query {
        builder().cast()
    }

    /**
     * Returns the first result as [Stored] entity
     */
    fun findFirst(builder: AqlBuilder.() -> TerminalExpr<T>): Stored<T>? = queryFirst { builder().cast() }

    /**
     * Find multiple by key and returns them as [Stored] entities
     */
    fun findByKeys(vararg keys: String): Cursor<Stored<T>> = query {

        val params = keys.filter { it.startsWith(coll.getAlias()) }

        FOR(DOCUMENT(coll, params.map { it.ensureKey })) { d ->
            RETURN(d)
        }.cast()
    }

    /**
     * Find one by key and return it as [Stored] entity
     */
    fun findByKey(key: String): Stored<T>? = queryFirst {
        RETURN(
            DOCUMENT(coll, key.ensureKey)
        ).cast()
    }

    /**
     * Performs the query
     */
    fun <T> query(query: TypedQuery<T>): Cursor<T> = driver.query(query)

    /**
     * Performs a query and returns a cursor of results
     */
    fun <X> query(builder: AqlBuilder.() -> TerminalExpr<X>): Cursor<X> = query(de.peekandpoke.karango.query(builder))

    /**
     * Performs a query and return a list of results
     */
    fun <X> queryList(builder: AqlBuilder.() -> TerminalExpr<X>): List<X> = query(builder).toList()

    /**
     * Performs a query and returns the first result or null
     */
    fun <X> queryFirst(builder: AqlBuilder.() -> TerminalExpr<X>): X? = queryFirst(builder)

    /**
     * Cast a terminal expr to a [Stored] entity.
     *
     * This is used to tell the deserialization, that we actually want [Stored] entities to be returned
     */
    private fun TerminalExpr<T>.cast(): TerminalExpr<Stored<T>> = AS(coll.getType().down<T>().wrapWith<Stored<T>>().up())
}


