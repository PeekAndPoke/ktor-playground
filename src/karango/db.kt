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

    fun <T : Entity, D : EntityCollectionDef<T>> collection(def: D): Collection<T, D> {

        val name = def.getRealName()
        val coll = database.collection(name)

        if (!coll.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.DOCUMENT))
        }

        return Collection(this, database.collection(name), def)
    }

    fun <T : Edge, D : EdgeCollectionDef<T>> edgeCollection(def: D): Collection<T, D> {

        val name = def.getRealName()
        val coll = database.collection(name)

        if (!coll.exists()) {
            database.createCollection(name, CollectionCreateOptions().type(CollectionType.EDGES))
        }

        return Collection(this, database.collection(name), def)
    }

    fun <T> query(builder: RootBuilder.() -> Iteratable<T>): ArangoCursor<T> {

        val query = de.peekandpoke.karango.query.query(builder)

        println(query)

        val options = AqlQueryOptions().count(true)

        return database.query(query.query, query.vars, options, query.returnType)
    }
}

@Suppress("unused")
class Column<T>(private val coll: CollectionDef<*>, private val name: String) : Named<T> {
    override fun getRealName() = name
    override fun getQueryName() = "${coll.getQueryName()}.$name"
}

@Suppress("PropertyName")
interface Entity {
    val _id: String?
}

@Suppress("PropertyName")
interface Edge : Entity {
    val _from: String
    val _to: String
}

interface CollectionDef<T> : Iteratable<T> {
    
    fun string(name: String) = Column<String>(this, name)
    fun number(name: String) = Column<Number>(this, name)
    fun <T> array(name: String) = Column<List<T>>(this, name)
}

abstract class EntityCollectionDef<T : Entity>(private val name: String, private val type: Class<T>) : CollectionDef<T> {
    override fun getRealName() = name
    override fun getQueryName() = "c_$name"
    override fun getReturnType() = type
}

abstract class EdgeCollectionDef<T : Edge>(private val name: String, private val type: Class<T>) : CollectionDef<T> {
    override fun getRealName() = name
    override fun getQueryName() = "e_$name"
    override fun getReturnType() = type
}

class Collection<T : Entity, D : CollectionDef<T>> internal constructor(
    private val db: Db,
    private val dbColl: ArangoCollection,
    private val def: D
) {
    fun save(obj: T): T = dbColl.insertDocument(obj, DocumentCreateOptions().returnNew(true)).new

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
