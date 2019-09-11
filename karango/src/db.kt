package de.peekandpoke.karango

import com.arangodb.*
import com.arangodb.entity.CollectionType
import com.arangodb.model.AqlQueryOptions
import com.arangodb.model.CollectionCreateOptions
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.convertValue
import de.peekandpoke.karango.addon.Timestamped
import de.peekandpoke.karango.addon.updateTimestamps
import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.jackson.KarangoDateTimeModule
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

/**
 * The database class
 */
class Db(internal val database: ArangoDatabase) {

    companion object {
        /**
         * Creates a Db with default setup
         */
        fun default(user: String, pass: String, host: String, port: Int, database: String): Db {

            val velocyJack = VelocyJack().apply {
                configure { mapper ->
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    mapper.configure(MapperFeature.USE_ANNOTATIONS, true)
                }
            }

            val arango = ArangoDB.Builder()
                .serializer(velocyJack)
                .user(user).password(pass)
                .host(host, port)
                .build()

            return Db(arango.db(database))
        }
    }

    /**
     * All entity collections registered on the Db
     */
    private val entityCollections: MutableMap<KClass<out DbEntityCollection<*>>, DbEntityCollection<*>> = mutableMapOf()

    /**
     * The object mapper used for serializing queries
     */
    private val serializer = ObjectMapper().apply {
        registerModule(KotlinModule())
        registerModule(Jdk8Module())
        // custom
        registerModule(KarangoDateTimeModule())

        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
    }

    /**
     * The deserializer used for deserializing results
     */
    private val deserializer = ObjectMapper().apply {
        registerModule(KotlinModule())
        registerModule(Jdk8Module())
        // custom
        registerModule(KarangoDateTimeModule())

        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        injectableValues = InjectableValues.Std().addValue("__db", this@Db)
    }

    /**
     * Get a list of all registered entity collections
     */
    fun getEntityCollections(): List<DbEntityCollection<*>> = entityCollections.values.toList()

    /**
     * Get an entity collection by its type
     *
     * If not found an exception is thrown
     *
     * @throws KarangoException
     */
    inline fun <reified T : DbEntityCollection<*>> getEntityCollection(): T = getEntityCollection(T::class)

    /**
     * Get an entity collection by its type
     *
     * If not found an exception is thrown
     *
     * @throws KarangoException
     */
    @Suppress("UNCHECKED_CAST") fun <T : DbEntityCollection<*>> getEntityCollection(cls: KClass<out T>): T =
        entityCollections[cls] as T? ?: throw KarangoException("Collection of type '${cls.java.canonicalName}' not registered.")

    /**
     * Registers an entity collection on the database
     *
     * Fails and throws an exception when:
     *
     * 1. A collection with the same name is already registered
     * 2. A collection with the same type is already registered
     *
     * @throws KarangoException
     */
    fun <T : Entity, C : DbEntityCollection<T>> register(coll: C): C = registerCollection { coll }

    /**
     * Executes the query created by the given builder and returns a list of results
     */
    fun <T> query(builder: AqlBuilder.() -> TerminalExpr<T>): Cursor<T> = query(deserializer, de.peekandpoke.karango.query(builder))

    /**
     * Executes the query created by the given builder and returns the first result or null
     */
    fun <T> queryFirst(builder: AqlBuilder.() -> TerminalExpr<T>): T? = query(builder).firstOrNull()

    /**
     * Register am entity collection
     *
     * Fails and throws an exception when:
     *
     * 1. A collection with the same name is already registered
     * 2. A collection with the same type is already registered
     */
    private fun <T : Entity, C : DbEntityCollection<T>> registerCollection(builder: () -> C): C {

        val result = builder()
        val type = result::class
        val name = result.name

        if (entityCollections.values.any { it.name == name }) {
            throw KarangoException("Collection with name '$name' is already registered!")
        }

        if (entityCollections.contains(type)) {
            throw KarangoException("Collection with type '$type' is already registered!")
        }

        entityCollections[type] = result

        if (!result.arangoColl.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.DOCUMENT))
        }

        return result
    }

    /**
     * Performs the query
     */
    private fun <T> query(deserializer: ObjectMapper, query: TypedQuery<T>): Cursor<T> {

        val options = AqlQueryOptions().count(true)
        val mapped = serializer.convertValue<Map<String, Any>>(query.vars)

        lateinit var result: ArangoCursor<*>

//        println(query)
//        println(query.ret.innerType())
//        println(mapped)

        val time = measureTimeMillis {
            try {
                result = database.query(query.aql, mapped, options, Object::class.java)
            } catch (e: ArangoDBException) {
                throw KarangoException("Error while querying '${e.message}':\n\n${query.aql}\nwith params\n\n$mapped", e)
            }
        }

        return CursorImpl(result, query, time, deserializer)
    }

}

/**
 * Base class for all entity collections
 */
open class DbEntityCollection<T : Entity>(val db: Db, val coll: IEntityCollection<T>) {

    /**
     * The name of the collection (the same as the collection name in the database itself)
     */
    val name: String = coll.getAlias()

    /**
     * The underlying low-level arango collection
     */
    val arangoColl: ArangoCollection = db.database.collection(name)

    /**
     * Save or update the given object.
     *
     * When the _id of the object is null an INSERT is tried.
     * Otherwise an UPSERT is tried.
     *
     * Returns the saved version of the input
     */
    fun save(obj: T): T = onBeforeSave(obj).let {
        when (it._id) {

            null -> db.query { INSERT(it) INTO coll }.first()

            else -> db.query { UPSERT(it) INTO coll }.first()
        }
    }

    /**
     * Perform an UPDATE on the given object.
     *
     * TODO: not fully implemented yet
     */
    fun update(obj: T, builder: KeyValueBuilder<T>.(Expression<T>) -> Unit): Cursor<Any> = db.query {
        UPDATE(obj, coll, builder)
    }

    /**
     * Finds all in the collection
     */
    fun findAll(): Cursor<T> = db.query {
        FOR(coll) { page ->
            RETURN(page)
        }
    }

    /**
     * Shorthand method for easier FIND queries
     */
    fun find(builder: ForLoop.(Iter<T>) -> Unit): Cursor<T> = db.query {
        FOR(coll) { t ->
            builder(t)
            RETURN(t)
        }
    }

    /**
     * Shorthand method for easier FIND queries for the first result
     */
    fun findOne(builder: ForLoop.(Iter<T>) -> Unit): T? = db.queryFirst {
        FOR(coll) { t ->
            builder(t)
            LIMIT(1)
            RETURN(t)
        }
    }

    /**
     * Find all entries by the given keys
     */
    fun findByIds(vararg keys: String) = db.query {

        val params = keys.filter { it.startsWith(coll.getAlias()) }

        FOR(DOCUMENT(coll, params)) { d ->
            RETURN(d)
        }
    }

    /**
     * Find first entry by key
     */
    fun findByKey(key: String): T? = db.queryFirst {
        RETURN(
            DOCUMENT(coll, key.ensureKey)
        )
    }

    /**
     * Get the number of entries in the collection
     */
    fun count(): Long = db.query { RETURN(COUNT(coll)) }.map { it.toLong() }.first()

    // TODO: CREATE DSL
    /**
     * Remove all entries from the collection
     */
    fun removeAll(): ArangoCursor<Any> =
        arangoColl.db().query("FOR x IN $name REMOVE x IN $name", Any::class.java)

    /**
     * Applies an plugin on an entity before it is saved
     */
    private fun onBeforeSave(obj: T): T {

        if (obj is Timestamped) {
            obj.updateTimestamps()
        }

        return obj
    }
}
