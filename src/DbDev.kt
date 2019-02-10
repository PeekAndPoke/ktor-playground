package de.peekandpoke

import arrow.optics.optics
import com.arangodb.ArangoDB
import com.arangodb.VelocyJack
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.peekandpoke.domain.PersonCollection
import de.peekandpoke.domain.address
import de.peekandpoke.domain.city
import de.peekandpoke.domain.name
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.configure
import de.peekandpoke.karango.query.EQ
import de.peekandpoke.karango.query.query
import kotlin.system.measureTimeMillis


fun x() {

    println(PersonCollection.name.getPath())
    println(PersonCollection.address.city.getPath())

    val q = query {
        FOR(PersonCollection) { p ->
            FILTER { p.address.city EQ "Karsten" }
            RETURN(p)
        }
    }

    println(q)
}


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


fun main() {

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


//    persons.removeAll()
//
//    val personInserts = (1..1000).map { i ->
//
//        val address = when {
//            i % 3 == 0 -> Address("Leipzig")
//            i % 3 == 1 -> Address("Dresden")
//            else -> Address("irgendwo")
//        }
//
//        listOf(
//            Person("Greta", i, address),
//            Person("Nadin", i, address),
//            Person("Karsten", i, address),
//            Person("Eddi", i, address)
//        )
//    }.flatten()
//
//    
//    val insertTime = measureTimeMillis {
//        val numInserted = persons.bulkInsert(personInserts)
//        println("Inserted $numInserted")
//    }
//
//    println("Insertion took $insertTime ms")
    
    // warm up (establish connection)
    persons.fetch { LIMIT(1) }.forEach { }

    PersonCollection.name.configure("TEST")
    
    println(PersonCollection.configurations)
    
    
    for (y in 1..1) {
        val timeAll = measureTimeMillis {

            val result = db.query {

                val x = LET("x") { "Karsten" }

                FOR(PersonCollection) { person ->

                    FILTER { person.name EQ "Karsten" }
                    FILTER { person.address.city EQ "Leipzig" }
//                    FILTER { t.name IN arrayOf("Eddi", "Karsten") }
//                    FILTER { t.name REGEX "^Ka.*$" }

//                    FOR(Addresses) { address ->
//                        SORT { address.city.ASC }
//                        FILTER { person.city EQ address.city }
//                    }

                    LIMIT(0, 10)
                    RETURN(person)
                }

            }

            result.forEach { println(it) }
        }

        println("fetchAll took $timeAll ms\n\n")
        println()
    }

//    val time100x = measureTimeMillis {
//
//        for (x in 1..10) {
//
//            val resultOne = persons.fetchOne { t ->
//                FILTER { t.name.EQ("Eddi") }
//            }
//
////            println(resultOne)
//        }
//    }
//
//    println("fetchOne 100x took $time100x ms")

    println(persons.count())

}
