package de.peekandpoke.karango

import com.arangodb.ArangoCollection
import com.arangodb.ArangoCursor
import com.arangodb.ArangoDatabase
import com.arangodb.entity.CollectionType
import com.arangodb.model.AqlQueryOptions
import com.arangodb.model.CollectionCreateOptions
import com.arangodb.model.DocumentCreateOptions
import de.peekandpoke.karango.query.ForLoopBuilder
import de.peekandpoke.karango.query.RootBuilder

class Db(private val database: ArangoDatabase) {

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

    fun <T> query(builder: RootBuilder.() -> IterableType<T>): ArangoCursor<T> {

        val query = de.peekandpoke.karango.query.query(builder)

//        println(query)

        val options = AqlQueryOptions().count(true)

        return database.query(query.query, query.vars, options, query.returnType)
    }
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

val String.asKey get() = if (contains('/')) split('/')[1] else this

class DbCollection<T : Entity, D : IterableType<T>> internal constructor(
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
    fun <X> getAs(idOrKey: String, type: Class<X>): X? = dbColl.getDocument(idOrKey.asKey, type)

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

    fun fetch(builder: ForLoopBuilder<T>.(D) -> Unit) =
        db.query {
            FOR(def) { t ->
                builder(t)
                RETURN(t)
            }
        }

    fun count() =
        dbColl.db().query("RETURN COUNT(${dbColl.name()})", Int::class.java).first()!!

    fun removeAll(): ArangoCursor<Any> =
        dbColl.db().query("FOR x IN ${dbColl.name()} REMOVE x IN ${dbColl.name()}", Any::class.java)
}
