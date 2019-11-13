package de.peekandpoke.karango.vault

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.*
import de.peekandpoke.ultra.common.TypeRef
import de.peekandpoke.ultra.common.unList
import de.peekandpoke.ultra.vault.*

abstract class EntityRepository<T : Any>(
    private val driver: KarangoDriver,
    protected val coll: IEntityCollection<T>
) : Repository<T> {

    /**
     * The name of the repository
     */
    override val name = coll.getAlias()

    /**
     * A reference to the type that is stored by the repository
     */
    override val storedType: TypeRef<T> = coll.getType().unList

    /**
     * Ensures that the repository is set up properly
     *
     * 1. Creates the collection in the database, if it does not exist yet
     * 2. TODO: ensure indexes on the collection
     */
    override fun ensure() {
        driver.ensureEntityCollection(name)
    }

    /**
     * Get the number of entries in the collection
     */
    fun count(): Long = queryFirst { RETURN(COUNT(coll)) }!!.toLong()

    /**
     * Saves the given storable and returns the saved version
     */
    fun save(storable: Storable<T>): Stored<T> = when (storable) {
        is New<T> -> save(storable)

        is Stored<T> -> save(storable)

        is Ref<T> -> save(storable.asStored)
    }

    /**
     * Inserts the given object into the database and returns the saved version
     */
    fun save(new: T): Stored<T> = save(New(new))

    /**
     * Inserts the given object with the given key into the database and returns the saved version
     */
    fun save(key: String, new: T): Stored<T> = save(New(_key = key, value = new))

    /**
     * Inserts the given object into the database and returns the saved version
     */
    fun save(new: New<T>): Stored<T> = driver.applyOnSaveHooks(this, new).let {
        findFirst {
            INSERT(it) INTO coll
        }!!
    }

    /**
     * Updates the given obj in the database and returns the saved version
     */
    fun save(stored: Stored<T>): Stored<T> = driver.applyOnSaveHooks(this, stored).let { modified ->
        findFirst {
            UPSERT(modified) INTO coll
        }!!
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
    private fun TerminalExpr<T>.cast(): TerminalExpr<Stored<T>> = AS(coll.getType().unList.wrapWith<Stored<T>>().list)
}
