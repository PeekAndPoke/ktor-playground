package de.peekandpoke.karango

/**
 * Remove result
 *
 * TODO: move to own file
 */
data class RemoveResult(val count: Int, val query: TypedQuery<*>) {

    companion object {
        fun from(e: KarangoQueryException) = RemoveResult(0, e.query)

        fun from(cursor: Cursor<*>) = RemoveResult(cursor.count, cursor.query)
    }
}

// TODO: remove commented code ... and file

///**
// * Base class for all entity collections
// */
//@Deprecated(message = "No longer supported", level = DeprecationLevel.ERROR)
//open class DbEntityCollection<T : Any>(private val db: Db, val coll: IEntityCollection<T>) {
//
//    /**
//     * The name of the collection (the same as the collection name in the database itself)
//     */
//    val name: String = coll.getAlias()
//
//    /**
//     * The underlying low-level arango collection
//     */
//    val arangoColl: ArangoCollection = db.arangoDb.collection(name)
//
//    /**
//     * Get the database that the collection is associated with
//     */
//    fun getDb() = db
//
//    /**
//     * Cast a terminal expr to a [Stored] entity.
//     *
//     * This is used to tell the deserialization, that we actually want [Stored] entities to be returned
//     */
//    private fun TerminalExpr<T>.cast() : TerminalExpr<Stored<T>> = AS(coll.getType().down<T>().wrapWith<Stored<T>>().up())
//
//    /**
//     * Save or update the given object.
//     *
//     * When the _id of the object is null an INSERT is tried.
//     * Otherwise an UPSERT is tried.
//     *
//     * Returns the saved version of the input
//     */
//    fun save(obj: T): T = onBeforeSave(obj).let {
//        db.query { INSERT(it) INTO coll }.first()
//    }
//
//    /**
//     * Finds all return them as [Stored] entities
//     */
//    fun findAll(): Cursor<Stored<T>> = find {
//        FOR(coll) {
//            RETURN(it)
//        }
//    }
//
//    /**
//     * Returns all results as [Stored] entities
//     */
//    fun find(builder: AqlBuilder.() -> TerminalExpr<T>): Cursor<Stored<T>> = db.query {
//        builder().cast()
//    }
//
//    /**
//     * Returns the first result as [Stored] entity
//     */
//    fun findFirst(builder: AqlBuilder.() -> TerminalExpr<T>): Stored<T>? = db.queryFirst { builder().cast() }
//
//    /**
//     * Find multiple by key and returns them as [Stored] entities
//     */
//    fun findByKeys(vararg keys: String): Cursor<Stored<T>> = db.query {
//
//        val params = keys.filter { it.startsWith(coll.getAlias()) }
//
//        FOR(DOCUMENT(coll, params.map { it.ensureKey })) { d ->
//            RETURN(d)
//        }.cast()
//    }
//
//    /**
//     * Find one by key and return it as [Stored] entity
//     */
//    fun findByKey(key: String): Stored<T>? = db.queryFirst {
//        RETURN(
//            DOCUMENT(coll, key.ensureKey)
//        ).cast()
//    }
//
//    /**
//     * Performs a query and returns a cursor of results
//     */
//    fun <X> query(builder: AqlBuilder.() -> TerminalExpr<X>): Cursor<X> = db.query(builder)
//
//    /**
//     * Performs a query and return a list of results
//     */
//    fun <X> queryList(builder: AqlBuilder.() -> TerminalExpr<X>): List<X> = query(builder).toList()
//
//    /**
//     * Performs a query and returns the first result or null
//     */
//    fun <X> queryFirst(builder: AqlBuilder.() -> TerminalExpr<X>): X? = db.queryFirst(builder)
//
//    /**
//     * Explains the query created by the builder
//     */
//    fun <X> explain(builder: AqlBuilder.() -> TerminalExpr<X>) = db.explain(builder)
//
//    /**
//     * Get the number of entries in the collection
//     */
//    fun count(): Long = db.query { RETURN(COUNT(coll)) }.map { it.toLong() }.first()
//
//
//    /**
//     * Applies an plugin on an entity before it is saved
//     */
//    private fun onBeforeSave(obj: T): T = db.getOnSaveHooks().fold(obj, { acc, onSaveHook -> onSaveHook(acc) })
//}

