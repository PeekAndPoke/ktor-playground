package de.peekandpoke

import arrow.optics.optics
import com.arangodb.ArangoDB
import com.arangodb.VelocyJack
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.peekandpoke.domain.*
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.query.CONTAINS
import de.peekandpoke.karango.query.EQ
import kotlin.system.measureTimeMillis


@optics
data class MyClass1(
    val name: String,
    val address: Address2
) {
    companion object
}

@optics
data class MyClass2(
    val name: String,
    val address: Address2
) {
    companion object
}

@optics
data class Address2(val city: String, val street: String) {
    companion object
}

inline fun <reified X> getType(v : X) : TypeReference<X> {
    return object : TypeReference<X>() {}
}

inline fun <reified L : List<X>, reified X> getType(v : L) : TypeReference<L> {
    return object : TypeReference<L>() {}
}

@JvmName("ll")
inline fun <reified L2: List<L>, reified L : List<X>, reified X> getType(v : L2) : TypeReference<L2> {
    return object : TypeReference<L2>() {}
}

@Suppress("unused")
@JvmName("wrap0")
inline fun <reified X> TypeReference<X>.wrap(): TypeReference<List<X>> = 
    object: TypeReference<List<X>>() {}

@Suppress("unused")
@JvmName("wrap1")
inline fun <reified X> TypeReference<List<X>>.wrap(): TypeReference<List<List<X>>> = 
    object : TypeReference<List<List<X>>>() {}

@Suppress("unused")
@JvmName("wrap2")
inline fun <reified X> TypeReference<List<List<X>>>.wrap(): TypeReference<List<List<List<X>>>> = 
    object : TypeReference<List<List<List<X>>>>() {}



fun main() {

//    val l2 = ArrayList<String>::class.java 
//    println()
//    
    println("--------------")

    val t1 = getType("Test")
    println(t1)
    println(t1.type)
//    println(t1.wrap().type)

    println()
    println("--------------")

    val t2 = getType(listOf("Test"))
    println(t2)
    println(t2.type)
//    println(t2.wrap().type)

    println()
    println("--------------")

    val t3 = getType(listOf(listOf("Test")))
    println(t3)
    println(t3.type)
//    println(t3.wrap().type)

    println()
    println("--------------")
    
    val mapper = ObjectMapper()
    
    val result : List<List<String>> = mapper.readValue("[[\"a\", \"b\"]]", t3)
    
    println(result)
    
//    x()
    y()
}

fun y() {

    val velocyJack = VelocyJack().apply { configure { mapper -> mapper.registerModule(KotlinModule()) } }
    val arango = ArangoDB.Builder()
        .serializer(velocyJack)
        .user("root").password("root")
        .host("localhost", 8529)
        .build()

    arango.databases.forEach {
        println("collection: $it")
    }

    val db = Db(arango.db("kotlindev"))

    val persons = db.collection(PersonCollection)
//    val addresses = db.collection(Address.)



    println(PersonCollection.configurations)

    exampleReturningFromScalarLet(db)
//    exampleReturningFromIterableLet(db)

    persons.removeAll()

    val insertTime = measureTimeMillis {
        exampleInsertFromLet(db)
    }

    println("Insertion took $insertTime ms")

//    
//    println(result)

    for (y in 1..10) {
        val timeAll = measureTimeMillis {

            val result = db.query {

                val str = LET("str", "Karsten")
                val num = LET("num", 2)

                val names = LET("names") {
                    listOf("J.R.R.", "X.X.X.")
                }

                FOR(PersonCollection) { person ->

                    FILTER { person.name EQ str }
                    
                    FILTER { person.name CONTAINS str }
                    
//                    FILTER { person.nr EQ num }
//                    FILTER { person.books.`*`.authors.`*`.firstName.`**` ALL IN(names) }

                    LIMIT(0, 20)

                    RETURN(person)
                }

            }

            result.forEach { println(it) }
        }

        println("fetchAll took $timeAll ms\n\n")
        println()
    }

    println(persons.count())
}

fun exampleReturningFromScalarLet(db: Db) {

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
    println("//  EXAMPLE: Returning a scalar value that was sent in via LET")

    val result = db.query {
        val let = LET("let", "TEST")

        RETURN(let)
    }

    println("---------------------------------------------------------------------------------------------------------")
    result.forEach { println(it) }
    println("---------------------------------------------------------------------------------------------------------")
    println()
}

fun exampleReturningFromIterableLet(db: Db) {

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
    println("//  EXAMPLE: Returning iterable values that where sent in via LET")

    val result = db.query {
        val let = LET("let") {
            listOf(
                Author("first", "last"),
                Author("first_2", "last_2")
            )
        }

        RETURN (let)
        
//        FOR (let) { x ->
//            FILTER { x.firstName EQ "first"}
//            RETURN (x)
//        }
    }

    println("---------------------------------------------------------------------------------------------------------")
    result.forEach { println(it) }
    println("---------------------------------------------------------------------------------------------------------")
    println()
}

fun exampleInsertFromLet(db: Db) {

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
    println("//  EXAMPLE: Inserting objects into a collection that where sent in via LET")

    val personInserts = (1..5).map { i ->

        val lotr = Book("Lotr", 1960, listOf(Author("J.R.R.", "Tolkin"), Author("X.X.X.", "Polkin")))
        val got = Book("GoT", 1980, listOf(Author("John", "Doe")))
        val hp = Book("HP", 1990, listOf(Author("Jane", "Doe")))

        val address = when {
            i % 3 == 0 -> Address("Leipzig")
            i % 3 == 1 -> Address("Dresden")
            else -> Address("irgendwo")
        }

        listOf(
            Person("Greta", i, address, listOf(lotr, got)),
            Person("Nadin", i, address, listOf(hp)),
            Person("Karsten", i, address, listOf(lotr)),
            Person("Eddi", i, address, listOf(got))
        )
    }.flatten()


    val result = db.query {
        val objs = LET("objs") { personInserts }

        FOR(objs) { o ->
            INSERT(o) INTO PersonCollection
        }
    }

    println("Inserted " + result.stats.writesExecuted)

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
}
