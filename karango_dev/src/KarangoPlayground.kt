package de.peekandpoke.karango_dev

import de.peekandpoke.karango.Db
import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango_dev.domain.*
import kotlin.system.measureTimeMillis

val db = Db.default(user = "root", pass = "root", host = "localhost", port = 8529, database = "kotlindev")

val persons = db.collection(PersonCollection)

fun main() {

    println(listOf("s1").aql().getType())

    val result = db.query {
        val persons = LET("persons") {
            listOf(
                Person("a", 10),
                Person("b", 20),
                Person("c", 30)
            )
        }

        RETURN(
            SUM(
                FOR("p") IN (persons) { p ->
                    FILTER (p.age GT 10)
                    RETURN(p.age)
                }
            )
        )
    }

    println(result.query.aql)
    result.forEach { println(it) }

    exampleInsertFromLet()
    
    
    val res2 = db.query { 
        FOR ("x") IN (PersonCollection) {x ->
            RETURN (x.books[`*`].authors[`*`].firstName[`**`])
        }
    }
    
    println("--------------------------------------------------------------------")
    println(res2.toList())
    println("--------------------------------------------------------------------")
    
//    y()
}

fun y() {

//    val addresses = db.collection(Address.)

    exampleReturningFromScalarLet(db)
//    exampleReturningFromIterableLet(db)

    persons.removeAll()

    val insertTime = measureTimeMillis {
        exampleInsertFromLet()
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
                    FILTER(CONTAINS(person.name, str))

                    val x1 = person
                    val x2 = person.books
                    val x3 = person.books[`*`]
                    val x4 = person.books[`*`].authors
                    val x5 = person.books[`*`].authors[`*`]
                    val x6 = person.books[`*`].authors[`*`].firstName
                    val x7 = person.books[`*`].authors[`*`].firstName[`**`]
                    
                    val y1 = person.books[`*`].title
                    
//                    FILTER { person.nr EQ num }
                    FILTER(person.books[`*`].title ALL IN(names))
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

fun exampleInsertFromLet() {

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

fun exampleFilterFromLet() {

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
            FILTER(o.name EQ "Greta")
            RETURN(o)
        }
    }

    println(result.query.aql)

    result.forEach { println(it) }

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
}
