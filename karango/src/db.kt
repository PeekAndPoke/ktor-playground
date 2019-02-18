package de.peekandpoke.karango

import com.arangodb.*
import com.arangodb.entity.CollectionType
import com.arangodb.entity.CursorEntity
import com.arangodb.model.AqlQueryOptions
import com.arangodb.model.CollectionCreateOptions
import com.arangodb.model.DocumentCreateOptions
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.peekandpoke.karango.query.*
import kotlin.system.measureTimeMillis

class Db(private val database: ArangoDatabase) {

    companion object {
        fun default(user: String, pass: String, host: String, port: Int, database: String): Db {

            val velocyJack = VelocyJack().apply {
                configure { mapper ->
                    mapper.registerModule(KotlinModule())
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
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

    fun <T : Entity, D : EntityCollectionDefinition<T>> collection(def: D): DbCollection<T, D> {

        val name = def.getName()
        val coll = database.collection(name)

        if (!coll.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.DOCUMENT))
        }

        return DbCollection(this, database.collection(name), def)
    }

    fun <T : Edge, D : EdgeCollectionDefinition<T>> edgeCollection(def: D): DbCollection<T, D> {

        val name = def.getName()
        val coll = database.collection(name)

        if (!coll.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.EDGES))
        }

        return DbCollection(this, database.collection(name), def)
    }

    fun <T> query(builder: RootBuilder.() -> Expression<T>): Cursor<T> {

        val query = de.peekandpoke.karango.query.query(builder)

        println(query)

        val options = AqlQueryOptions().count(true)

        lateinit var result: ArangoCursor<*>

        val time = measureTimeMillis {
            result = database.query(query.aql, query.vars, options, Object::class.java)
        }

        return CursorImpl(result, query, time, query.returnType)
    }
}

interface Cursor<T> : Iterable<T> {
    val query: TypedQuery<T>
    val timeMs: Long

    val stats: CursorEntity.Stats
}

private val cursorMapper = ObjectMapper()
    .registerModule(KotlinModule())
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

class CursorImpl<T>(
    private val arangoCursor: ArangoCursor<*>,
    override val query: TypedQuery<T>,
    override val timeMs: Long,
    type: Class<T>
) : Cursor<T> {

    private val iterator = It(arangoCursor, type)

    class It<T>(private val inner: ArangoIterator<*>, private val type: Class<T>) : Iterator<T> {

        override fun hasNext(): Boolean = inner.hasNext()

        override fun next(): T = cursorMapper.convertValue(inner.next(), type)
    }

    override fun iterator() = iterator

    override val stats: CursorEntity.Stats get() = arangoCursor.stats
}

@Suppress("PropertyName")
interface Entity {
    val _id: String
}

@Suppress("PropertyName")
interface Edge : Entity {
    val _from: String
    val _to: String
}

@Suppress("PropertyName")
interface WithKey {
    val _key: String
}

@Suppress("PropertyName")
interface WithRev {
    val _rev: String
}

class DbCollection<T : Entity, D : CollectionDefinition<T>> internal constructor(
    private val db: Db,
    private val dbColl: ArangoCollection,
    private val def: D
) {

    /**
     * Get document by _id or _key
     */
    fun get(idOrKey: String): T? = getAs(idOrKey, def.getType())

    /**
     * Get document by _id or _key as the given type
     */
    fun <X> getAs(idOrKey: String, type: Class<X>): X? = dbColl.getDocument(idOrKey.ensureKey, type)

    fun save(obj: T): T =
        dbColl.insertDocument(
            obj,
            DocumentCreateOptions()
                .returnNew(true)
                .overwrite(true)
        ).new

    fun update(entity: T, builder: KeyValueBuilder<T>.(D) -> Unit): Cursor<T> =
        db.query {
            UPDATE(entity, def, builder)
        }

    fun find(builder: ForLoopBuilder<T>.(D) -> Unit): Cursor<T> =
        db.query {
            FOR(def) { t ->
                builder(t)
                RETURN(t)
            }
        }

    fun findOne(builder: ForLoopBuilder<T>.(D) -> Unit): T? =
        db.query {
            FOR(def) { t ->
                builder(t)
                LIMIT(1)
                RETURN(t)
            }
        }.first()

    fun findByIds(vararg keys: String) =
        db.query {
            RETURN(def.getType(), *keys.filter { it.startsWith(def.getName()) }.toTypedArray())
        }

    fun findByKey(key: String): T? =
        db.query {
            RETURN(def.getName(), key.ensureKey, def.getType())
        }.first()

    // TODO: CREATE DSL
    fun count() =
        dbColl.db().query("RETURN COUNT(${dbColl.name()})", Int::class.java).first()!!

    // TODO: CREATE DSL
    fun removeAll(): ArangoCursor<Any> =
        dbColl.db().query("FOR x IN ${dbColl.name()} REMOVE x IN ${dbColl.name()}", Any::class.java)
}
