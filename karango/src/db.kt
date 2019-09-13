package de.peekandpoke.karango

import com.arangodb.*
import com.arangodb.entity.AqlExecutionExplainEntity
import com.arangodb.entity.CollectionType
import com.arangodb.model.AqlQueryExplainOptions
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
import de.peekandpoke.karango.jackson.KarangoDateTimeModule
import de.peekandpoke.karango.jackson.RefCache
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

typealias EntityCollectionProvider<T> = (db: Db) -> DbEntityCollection<T>

typealias OnSaveHookProvider = (db: Db) -> OnSaveHook

/**
 * The database class
 */
class Db(val arangoDb: ArangoDatabase, private val settings: Settings) {

    companion object {
        /**
         * Creates a Db with default setup
         */
        fun default(user: String, pass: String, host: String, port: Int, database: String, builder: Builder.() -> Unit = {}): Db {

            val arango = ArangoDB.Builder()
                .user(user).password(pass)
                .host(host, port)
                .build()

            return Builder(arango.db(database)).apply(builder).build().apply {
                ensureCollections()
            }
        }

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
         * The deserializer used for de-serializing results
         */
        private val deserializerBlueprint = ObjectMapper().apply {
            registerModule(KotlinModule())
            registerModule(Jdk8Module())
            // custom
            registerModule(KarangoDateTimeModule())

            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    data class Settings(
        val entityCollections: List<EntityCollectionProvider<*>>,
        val onSaveHooks: List<OnSaveHookProvider>
    )

    class Builder(private val arangoDb: ArangoDatabase) {

        /**
         * All entity collections registered on the Db
         */
        private val entityCollections: MutableList<EntityCollectionProvider<*>> = mutableListOf()

        /**
         * List of hooks that will be called before an entity is saved
         */
        private val onSaveHooks: MutableList<OnSaveHookProvider> = mutableListOf()

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
        @JvmName("registerEntityCollection")
        fun <T : Entity> addEntityCollection(provider: EntityCollectionProvider<T>) = apply { entityCollections.add(provider) }

        /**
         * Register a onSave hook
         */
        @JvmName("registerOnSaveHook")
        fun addOnSaveHook(provider: OnSaveHookProvider) = apply { onSaveHooks.add(provider) }

        fun build() = Db(
            arangoDb,
            Settings(
                entityCollections.toList(),
                onSaveHooks.toList()
            )
        )
    }

    private val entityCollections: MutableMap<KClass<out DbEntityCollection<*>>, DbEntityCollection<*>> = mutableMapOf()

    private val onSaveHooks: MutableList<OnSaveHook> = mutableListOf()

    init {
        settings.entityCollections.forEach { registerCollection(it) }

        settings.onSaveHooks.forEach { onSaveHooks.add(it(this)) }
    }

    /**
     * Return a new Db instance while changing its settings.
     *
     * The 'builder' can create new Settings which will be used for the new Db instance
     */
    fun customize(builder: (Settings) -> Settings): Db = Db(arangoDb, builder(settings))

    /**
     * Get a list of all registered entity collections
     */
    fun getEntityCollections(): List<DbEntityCollection<*>> = entityCollections.values.toList()

    /**
     * Get the list of all registered onSave hooks
     */
    fun getOnSaveHooks(): List<OnSaveHook> = onSaveHooks.toList()

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
     * Executes the query created by the given builder and returns a list of results
     */
    fun <T> query(builder: AqlBuilder.() -> TerminalExpr<T>): Cursor<T> = query(de.peekandpoke.karango.query(builder))

    /**
     * Executes the query created by the given builder and returns the first result or null
     */
    fun <T> queryFirst(builder: AqlBuilder.() -> TerminalExpr<T>): T? = query(builder).firstOrNull()

    /**
     * Explains the query created by the given builder and returns a list of results
     */
    fun <T> explain(builder: AqlBuilder.() -> TerminalExpr<T>): AqlExecutionExplainEntity = explain(de.peekandpoke.karango.query(builder))

    /**
     * Creates all collections that are not yet present in the database
     */
    fun ensureCollections() {

        entityCollections.values.forEach {

            if (!it.arangoColl.exists()) {
                arangoDb.createCollection(it.name, CollectionCreateOptions().type(CollectionType.DOCUMENT))
            }
        }
    }

    /**
     * Register am entity collection
     *
     * Fails and throws an exception when:
     *
     * 1. A collection with the same name is already registered
     * 2. A collection with the same type is already registered
     */
    private fun registerCollection(builder: EntityCollectionProvider<*>) {

        val result = builder(this)
        val type = result::class
        val name = result.name

        if (entityCollections.values.any { it.name == name }) {
            throw KarangoException("Collection with name '$name' is already registered!")
        }

        if (entityCollections.contains(type)) {
            throw KarangoException("Collection with type '$type' is already registered!")
        }

        entityCollections[type] = result
    }

    /**
     * Performs the query
     */
    private fun <T> query(query: TypedQuery<T>): Cursor<T> {

        val options = AqlQueryOptions().count(true)
        val params = serializer.convertValue<Map<String, Any>>(query.vars)

        lateinit var result: ArangoCursor<*>

//        println(query)
//        println(query.ret.innerType())
//        println(mapped)

        val time = measureTimeMillis {
            try {
                result = arangoDb.query(query.aql, params, options, Object::class.java)
            } catch (e: ArangoDBException) {
                throw KarangoException("Error while querying '${e.message}':\n\n${query.aql}\nwith params\n\n$params", e)
            }
        }

        val values = InjectableValues.Std(
            mapOf(
                "db" to this,
                "cache" to RefCache()
            )
        )

        return CursorImpl(
            result,
            query,
            time,
            deserializerBlueprint.copy().apply { injectableValues = values }
        )
    }

    private fun explain(query: TypedQuery<*>): AqlExecutionExplainEntity {

        val options = AqlQueryExplainOptions()
        val params = serializer.convertValue<Map<String, Any>>(query.vars)

        return arangoDb.explainQuery(query.aql, params, options)
    }
}

/**
 * Base class for all entity collections
 */
open class DbEntityCollection<T : Entity>(private val db: Db, val coll: IEntityCollection<T>) {

    /**
     * The name of the collection (the same as the collection name in the database itself)
     */
    val name: String = coll.getAlias()

    /**
     * The underlying low-level arango collection
     */
    val arangoColl: ArangoCollection = db.arangoDb.collection(name)

    /**
     * Get the database that the collection is associated with
     */
    fun getDb() = db

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
     * Performs a query and returns a list of results
     */
    fun <T> query(builder: AqlBuilder.() -> TerminalExpr<T>): Cursor<T> = db.query(builder)

    /**
     * Performs a query and returns the first result or null
     */
    fun <T> queryFirst(builder: AqlBuilder.() -> TerminalExpr<T>): T? = db.queryFirst(builder)

    /**
     * Explains the query created by the builder
     */
    fun <T> explain(builder: AqlBuilder.() -> TerminalExpr<T>) = db.explain(builder)

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
    fun findFirst(builder: ForLoop.(Iter<T>) -> Unit): T? = db.queryFirst {
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
    private fun onBeforeSave(obj: T): T = db.getOnSaveHooks().fold(obj, { acc, onSaveHook -> onSaveHook(acc) })
}
