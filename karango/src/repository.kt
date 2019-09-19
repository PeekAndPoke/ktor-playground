package de.peekandpoke.karango

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
import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.jackson.KarangoJacksonModule
import de.peekandpoke.ultra.vault.*
import kotlin.system.measureTimeMillis

val karangoDefaultDriver = Key<KarangoDriver>("karango_default_driver")

class KarangoDriver(
    private val database: Database,
    private val arangoDb: ArangoDatabase,
    private val onSaveHooks: List<OnSaveHook> = listOf(),
    private val refCache: RefCache = RefCache()
) : Driver {

    /**
     * The object mapper used for serializing queries
     */
    private val serializer = ObjectMapper().apply {
        // default modules
        registerModule(KotlinModule())
        registerModule(Jdk8Module())

        // custom modules
        registerModule(KarangoJacksonModule())

        // serialization features
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)

        // deserialization features
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        injectableValues = InjectableValues.Std(
            mapOf(
                "database" to database,
                "mapper" to this,
                "cache" to refCache
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

        return CursorImpl(result, query, time, serializer)
    }
}

abstract class EntityRepository<T : Any>(
    private val driver: KarangoDriver,
    protected val coll: IEntityCollection<T>
) : Repository<T> {

    override val name = coll.getAlias()

    override val storedType by lazy { coll.getType().down<T>() }

    override fun ensure() {
        driver.ensureEntityCollection(name)
    }

    /**
     * Get the number of entries in the collection
     */
    fun count(): Long = queryFirst { RETURN(COUNT(coll)) }!!.toLong()

    /**
     * Save or update the given object.
     *
     * When the _id of the object is null an INSERT is tried.
     * Otherwise an UPSERT is tried.
     *
     * Returns the saved version of the input
     */
    fun save(obj: T): Stored<T> = onBeforeSave(obj).let {
        findFirst {
            INSERT(it) INTO coll
        }!!
    }

    /**
     * TODO: implement me with UPSERT
     */
    private fun onBeforeSave(obj: T) = obj

    fun save(stored: Stored<T>): Stored<T> {
        TODO("implement me with UPSERT")
    }

    /**
     * Removes the given entity
     */
    fun remove(entity: Stored<T>): RemoveResult = remove(entity._id)

    /**
     * Remove the document with the given id or key
     */
    fun remove(idOrKey: String): RemoveResult = try {
        RemoveResult.from(
            query { REMOVE(idOrKey.ensureKey) IN coll }
        )
    } catch (e: KarangoQueryException) {
        RemoveResult.from(e)
    }

    /**
     * Remove all entries from the collection
     */
    fun removeAll() = RemoveResult.from(
        query {
            FOR(coll) { c ->
                REMOVE(c._key) IN coll
            }
        }
    )

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
     * Returns all results as [Stored] entities
     */
    fun findList(builder: AqlBuilder.() -> TerminalExpr<T>): List<Stored<T>> = find(builder).toList()

    /**
     * Returns the first result as [Stored] entity
     */
    fun findFirst(builder: AqlBuilder.() -> TerminalExpr<T>): Stored<T>? = queryFirst { builder().cast() }

    /**
     * Find one by id or key and return it as [Stored] entity
     */
    override fun findById(id: String) = queryFirst {
        RETURN(
            DOCUMENT(coll, id.ensureKey)
        ).cast()
    }

    /**
     * Find multiple by id or key and returns them as [Stored] entities
     */
    fun findByIds(vararg ids: String): Cursor<Stored<T>> = query {

        FOR(DOCUMENT(coll, ids.map { it.ensureKey })) { d ->
            RETURN(d)
        }.cast()
    }

    /**
     * Performs the query
     */
    fun <T> query(query: TypedQuery<T>): Cursor<T> = driver.query(query)

    /**
     * Performs a query and returns a cursor of results
     */
    fun <X> query(builder: AqlBuilder.() -> TerminalExpr<X>): Cursor<X> = driver.query(builder)

    /**
     * Performs a query and return a list of results
     */
    fun <X> queryList(builder: AqlBuilder.() -> TerminalExpr<X>): List<X> = query(builder).toList()

    /**
     * Performs a query and returns the first result or null
     */
    fun <X> queryFirst(builder: AqlBuilder.() -> TerminalExpr<X>): X? = query(builder).firstOrNull()

    /**
     * Cast a terminal expr to a [Stored] entity.
     *
     * This is used to tell the deserialization, that we actually want [Stored] entities to be returned
     */
    private fun TerminalExpr<T>.cast(): TerminalExpr<Stored<T>> = AS(coll.getType().down<T>().wrapWith<Stored<T>>().up())
}


