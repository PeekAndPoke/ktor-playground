package de.peekandpoke.karango

import com.arangodb.*
import com.arangodb.entity.CollectionType
import com.arangodb.model.AqlQueryOptions
import com.arangodb.model.CollectionCreateOptions
import com.arangodb.model.DocumentCreateOptions
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.peekandpoke.karango.query.ForLoopBuilder
import de.peekandpoke.karango.query.RootBuilder
import de.peekandpoke.karango.query.TypedQuery
import de.peekandpoke.karango.query.ensureKey
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

        val name = def.getSimpleName()
        val coll = database.collection(name)

        if (!coll.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.DOCUMENT))
        }

        return DbCollection(this, database.collection(name), def)
    }

    fun <T : Edge, D : EdgeCollectionDefinition<T>> edgeCollection(def: D): DbCollection<T, D> {

        val name = def.getSimpleName()
        val coll = database.collection(name)

        if (!coll.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.EDGES))
        }

        return DbCollection(this, database.collection(name), def)
    }

    fun <T> query(builder: RootBuilder.() -> ReturnType<T>): Cursor<T> {

        val query = de.peekandpoke.karango.query.query(builder)

//        println(query)

        val options = AqlQueryOptions().count(true)

        lateinit var result: ArangoCursor<T>
        
        val time = measureTimeMillis { 
            result = database.query(query.aql, query.vars, options, query.returnType)
        }
        
        return Cursor(result, query, time)
    }
}

class Cursor<T>(private val inner : ArangoCursor<T>, 
                val query: TypedQuery<T>,
                val timeMs: Long) : ArangoCursor<T> by inner 

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
    fun get(idOrKey: String): T? = getAs(idOrKey, def.getReturnType())

    /**
     * Get document by _id or _key as the given type
     */
    fun <X> getAs(idOrKey: String, type: Class<X>): X? = dbColl.getDocument(idOrKey.ensureKey, type)

    fun save(obj: T): T = dbColl.insertDocument(
        obj,
        DocumentCreateOptions()
            .returnNew(true)
            .overwrite(true)
    ).new

    fun bulkInsert(objs: List<T>) = dbColl.insertDocuments(
        objs,
        DocumentCreateOptions()
            .waitForSync(false)
            .overwrite(true)
            .returnNew(false)
            .returnOld(false)
    ).documents.size

    fun query(builder: ForLoopBuilder<T>.(D) -> Unit) =
        db.query {
            FOR(def) { t ->
                builder(t)
                RETURN(t)
            }
        }

    fun queryOne(builder: ForLoopBuilder<T>.(D) -> Unit) : T? =
            db.query {
                FOR(def) {t ->
                    builder(t)
                    LIMIT(1)
                    RETURN(t)
                }
            }.first()
    
    fun queryByKey(key: String) = db.query { 
        RETURN(def.getSimpleName(), key.ensureKey, def.getReturnType())
    }.first()
    
    fun count() =
        dbColl.db().query("RETURN COUNT(${dbColl.name()})", Int::class.java).first()!!

    fun removeAll(): ArangoCursor<Any> =
        dbColl.db().query("FOR x IN ${dbColl.name()} REMOVE x IN ${dbColl.name()}", Any::class.java)
}
