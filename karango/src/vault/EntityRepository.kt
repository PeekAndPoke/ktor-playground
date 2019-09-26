package de.peekandpoke.karango.vault

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.*
import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.Stored

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
     * Inserts the given object into the database and returns the saved version
     *
     * TODO: apply onSaveHooks
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

    /**
     * Updates the given obj in the database and returns the saved version
     *
     * TODO: apply onSaveHooks
     */
    fun save(stored: Storable<T>): Stored<T> = driver.applyOnSaveHooks(this, stored).let { modified ->
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
    private fun TerminalExpr<T>.cast(): TerminalExpr<Stored<T>> = AS(coll.getType().down<T>().wrapWith<Stored<T>>().up())
}
