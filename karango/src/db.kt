package de.peekandpoke.karango

import com.arangodb.*
import com.arangodb.entity.CollectionType
import com.arangodb.entity.CursorEntity
import com.arangodb.model.AqlQueryOptions
import com.arangodb.model.CollectionCreateOptions
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.InjectableValues
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.convertValue
import de.peekandpoke.karango.aql.*
import kotlin.system.measureTimeMillis

class Db(private val database: ArangoDatabase) {

    companion object {
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

    private val serializer = ObjectMapper()

    private val deserializerBlueprint = ObjectMapper().apply {
        registerModule(KotlinModule())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        injectableValues = InjectableValues.Std().addValue("__db", this@Db)
    }

    fun <T : Entity, D : EntityCollectionDefinition<T>> collection(def: D): DbCollection<T, D> {

        val name = def.getAlias()
        val coll = database.collection(name)

        if (!coll.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.DOCUMENT))
        }

        return DbCollection(this, database.collection(name), def)
    }

    fun <T : Edge, D : EdgeCollectionDefinition<T>> edgeCollection(def: D): DbCollection<T, D> {

        val name = def.getAlias()
        val coll = database.collection(name)

        if (!coll.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.EDGES))
        }

        return DbCollection(this, database.collection(name), def)
    }

    fun <T> query(builder: AqlBuilder.() -> TerminalExpr<T>): Cursor<T> = query(deserializerBlueprint, builder)

    internal fun <T> query(deserializer: ObjectMapper, builder: AqlBuilder.() -> TerminalExpr<T>): Cursor<T> {

        val query = de.peekandpoke.karango.query(builder)

        val options = AqlQueryOptions().count(true)

        lateinit var result: ArangoCursor<*>

        val mapped = serializer.convertValue<Map<String, Any>>(query.vars)

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

interface Cursor<T> : Iterable<T> {
    val query: TypedQuery<T>
    val timeMs: Long
    val stats: CursorEntity.Stats
}


class CursorImpl<T>(
    private val arangoCursor: ArangoCursor<*>,
    override val query: TypedQuery<T>,
    override val timeMs: Long,
    mapper: ObjectMapper
) : Cursor<T> {

    private val iterator = It(arangoCursor, query.ret.innerType(), mapper)

    class It<T>(private val inner: ArangoIterator<*>, private val type: TypeRef<T>, private val mapper: ObjectMapper) : Iterator<T> {

        override fun hasNext(): Boolean = inner.hasNext()

        override fun next(): T = mapper.convertValue(inner.next(), type)
    }

    override val stats: CursorEntity.Stats get() = arangoCursor.stats

    override fun iterator() = iterator
}

@Suppress("PropertyName")
interface Entity {
    @get:JsonInclude(JsonInclude.Include.NON_EMPTY)
    val _id: String?
}

val Entity?.id: String
    get() = when {
        this === null -> ""
        this._id === null -> ""
        else -> this._id!!
    }

@Suppress("PropertyName")
interface WithKey {
    @get:JsonInclude(JsonInclude.Include.NON_EMPTY)
    val _key: String?
}

@Suppress("PropertyName")
interface WithRev {
    val _rev: String
}

@Suppress("PropertyName")
interface Edge : Entity {
    val _from: String
    val _to: String
}


class DbCollection<T : Entity, D : CollectionDefinition<T>> internal constructor(
    private val db: Db,
    private val dbColl: ArangoCollection,
    private val def: D
) {
    fun save(obj: T): T = when (obj._id) {

        null -> db.query { INSERT(obj) INTO def }.first()

        else -> db.query { UPSERT(obj) INTO def }.first()
    }


    fun update(entity: T, builder: KeyValueBuilder<T>.(Expression<T>) -> Unit): Cursor<Any> =
        db.query {
            UPDATE(entity, def, builder)
        }

    fun find(builder: ForLoop.(Iter<T>) -> Unit): Cursor<T> =
        db.query {
            FOR(def) { t ->
                builder(t)
                RETURN(t)
            }
        }

    fun findOne(builder: ForLoop.(Iter<T>) -> Unit): T? =
        db.query {

            FOR(def) { t ->
                builder(t)
                LIMIT(1)
                RETURN(t)
            }
        }.first()

    fun findByIds(vararg keys: String) =
        db.query {

            val params = keys.filter { it.startsWith(def.getAlias()) }

            FOR(DOCUMENT(def, params)) { d ->
                RETURN(d)
            }
        }

    fun findByKey(key: String): T? =
        db.query {
            RETURN(
                DOCUMENT(def, key.ensureKey)
            )
        }.first()

    // TODO: CREATE DSL
    fun count() =
        dbColl.db().query("RETURN COUNT(${dbColl.name()})", Int::class.java).first()!!

    // TODO: CREATE DSL
    fun removeAll(): ArangoCursor<Any> =
        dbColl.db().query("FOR x IN ${dbColl.name()} REMOVE x IN ${dbColl.name()}", Any::class.java)
}
