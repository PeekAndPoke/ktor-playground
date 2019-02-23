package de.peekandpoke

import de.peekandpoke.domain.*
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.aql.*
import kotlin.system.measureTimeMillis

val db = Db.default(user = "root", pass = "root", host = "localhost", port = 8529, database = "kotlindev")

val persons = db.collection(PersonCollection)

fun main() {

    val strList =
        listOf(
            listOf(
                listOf("a")
            )
        )

    val type = strList.toTypeRef()

    println("--  TYPE  -----------------------------------------------------------------------------------------")
    println(type.type)
//    println("--  UP  -----------------------------------------------------------------------------------------")
//    println(type.up.type)
//    println("--  DOWN  -----------------------------------------------------------------------------------------")
//    println(type.down.type)
//    println("--  UP DOWN  -----------------------------------------------------------------------------------------")
//    println(type.up.down.type)

    val result = db.query {
        val a = LET("a", "text")
        RETURN(a)
    }

    println("-------------------------------------------------------------------------------------------")
    println(result.query.ret.getType())
    println(result.map { it })

    val result2 = db.query {
        RETURN(PersonCollection)
    }

    println("-------------------------------------------------------------------------------------------")
    println(result2.query.ret.getType())
    println(result2.map { it })

//    x()
//    y()
}

fun y() {

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

                FOR("x") IN (PersonCollection) { person ->

                    FILTER(person.name EQ str)
                    FILTER(person.name CONTAINS str)

//                    FILTER { person.nr EQ num }
                    FILTER(person.books[`*`].authors[`*`].firstName[`**`] ALL IN(names))

                    LIMIT(0, 20)

                    RETURN(person)
                }
            }

            println(result.query.aql)
            println(result.query.vars)

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

        RETURN(let)

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

        FOR("x") IN (objs) { o ->
            INSERT(o) INTO PersonCollection
        }
    }

    println("Inserted " + result.stats.writesExecuted)

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
}
