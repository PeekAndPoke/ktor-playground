package de.peekandpoke

import com.arangodb.ArangoDB
import com.arangodb.VelocyJack
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.query.IN
import de.peekandpoke.karango.query.REGEX
import kotlin.system.measureTimeMillis

fun main() {

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

    val persons = db.collection(Persons)
    val addresses = db.collection(Addresses)

//    println(addresses.insert(Address(null, "Leipzig")))
//    println(addresses.insert(Address(null, "Dresden")))
//
//    personCol.removeAll()
//
//    for (i in 1..100000) {
//
//        personCol.insert(Person("Greta", i))
//        personCol.insert(Person("Nadin", i))
//        personCol.insert(Person("Karsten", i))
//        personCol.insert(Person("Eddi", i))
//    }

    // warm up (establish connection)
    persons.fetch { LIMIT(1) }.forEach { }

    for (y in 1..1) {
        val timeAll = measureTimeMillis {

            val result = db.query {

                FOR(Persons) { t ->

                    FILTER { t.name IN arrayOf("Eddi", "Karsten") }
                    FILTER { t.name REGEX "^Ka.*$" }

//                    FOR(Addresses) { a ->
//                        SORT { a.city.ASC() }
//                        FILTER { t.city.EQ(a.city) }
//                    }

                    LIMIT(0, 10)
                    RETURN()
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
