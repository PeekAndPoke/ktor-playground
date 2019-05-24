package de.peekandpoke.karango_dev

import de.peekandpoke.frozen.Frozen
import de.peekandpoke.frozen.Mutator
import de.peekandpoke.frozen.mutator
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.aql.CONTAINS
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango_dev.domain.Author
import de.peekandpoke.karango_dev.domain.Person
import de.peekandpoke.karango_dev.domain.Persons
import de.peekandpoke.karango_dev.domain.name
import kotlin.system.measureTimeMillis


val db = Db.default(user = "root", pass = "", host = "localhost", port = 8529, database = "kotlindev")

val persons = db.collection(Persons)

@Mutator
data class FrozenAddress(val city: String, val zip: String)

@Mutator
data class FrozenPerson(val name: String, val age: Int, val address: FrozenAddress)

@Mutator
data class FrozenCompany(val boss: FrozenPerson, val addresses: List<FrozenAddress>)

val FrozenCompanyMutator.addresses
    get() = result.addresses.mutator(
        { item, onModify -> item.mutator(onModify) },
        { _, after -> modify(result::addresses, after) }
    )

fun main() {

    val t = measureTimeMillis {
        repeat(1) { playWithMutator() }
    }


    println("-- $t ms ----------------------------------------------------")

//    moreFun()
}

fun playWithMutator() {

    val address = FrozenAddress("Leipzig", "04177")
    val person = FrozenPerson("Karl", 22, address)
    val company = FrozenCompany(
        person,
        listOf(
            FrozenAddress("Berlin", "10117"),
            FrozenAddress("München", "80637")
        )
    )

    val mutation = company.mutate {
        it.boss.name = it.boss.name.toUpperCase()
        it.boss.age++

        it.boss.address.city = "Aue"
        it.boss.address.zip = "08280"

        it.addresses.forEach { address ->
            address.city = address.city.toUpperCase()
        }
    }

    println(company)
    println(mutation)
}

fun moreFun() {

    println("----------------------------------------------------")

    val person = Frozen.createLazyProxy { Person("Heinz", 55) }

    println(person)
    println(person::class)
    println(person.name)
    println(Person::class.java.methods.first { it.name == "getName" }.invoke(person))
    println(person.age)

    println(person.copy()::class)
    println(person.copy(name = "bernd"))
    println(person.copy().name)
//    println(System.identityHashCode(person.copy()))

    val person2 = Frozen.createLazyProxy { Person("Heinz 2", 55) }
    println(person2)
    println(person2::class)
}

fun y() {

//    val addresses = db.collection(Address.)

    exampleReturningFromScalarLet(db)
//    exampleReturningFromIterableLet(db)

    persons.removeAll()

    val insertTime = measureTimeMillis {
        //        exampleInsertFromLet()
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


                FOR(Persons) { person ->

                    FILTER(person.name EQ str)
                    FILTER(CONTAINS(person.name, str))

//                    val x1 = person
//                    val x2 = person.books
//                    val x3 = person.books[`*`]
//                    val x4 = person.books[`*`].authors
//                    val x5 = person.books[`*`].authors[`*`]
//                    val x6 = person.books[`*`].authors[`*`].firstName
//                    val x7 = person.books[`*`].authors[`*`].firstName[`**`]
//
//                    val y1 = person.books[`*`].title

//                    FILTER { person.nr EQ num }
//                    FILTER(person.books[`*`].title ALL IN(names))
//                    FILTER(person.books[`*`].authors[`*`].firstName[`**`] ALL IN(names))

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

//fun exampleInsertFromLet() {
//
//    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
//    println("//  EXAMPLE: Inserting objects into a collection that where sent in via LET")
//
//    val personInserts = (1..5).map { i ->
//
//        val lotr = Book("Lotr", 1960, listOf(Author("J.R.R.", "Tolkin"), Author("X.X.X.", "Polkin")))
//        val got = Book("GoT", 1980, listOf(Author("John", "Doe")))
//        val hp = Book("HP", 1990, listOf(Author("Jane", "Doe")))
//
//        val address = when {
//            i % 3 == 0 -> Address("Leipzig")
//            i % 3 == 1 -> Address("Dresden")
//            else -> Address("irgendwo")
//        }
//
//        listOf(
//            Person("Greta", i, address, listOf(lotr, got)),
//            Person("Nadin", i, address, listOf(hp)),
//            Person("Karsten", i, address, listOf(lotr)),
//            Person("Eddi", i, address, listOf(got))
//        )
//    }.flatten()
//
//
//    val result = db.query {
//        val objs = LET("objs") { personInserts }
//
//        FOR(objs) { o ->
//            INSERT(o) INTO Persons
//        }
//    }
//
//    println("Inserted " + result.stats.writesExecuted)
//
//    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
//}


//fun exampleFilterFromLet() {
//
//    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
//    println("//  EXAMPLE: Inserting objects into a collection that where sent in via LET")
//
//    val personInserts = (1..5).map { i ->
//
//        val lotr = Book("Lotr", 1960, listOf(Author("J.R.R.", "Tolkin"), Author("X.X.X.", "Polkin")))
//        val got = Book("GoT", 1980, listOf(Author("John", "Doe")))
//        val hp = Book("HP", 1990, listOf(Author("Jane", "Doe")))
//
//        val address = when {
//            i % 3 == 0 -> Address("Leipzig")
//            i % 3 == 1 -> Address("Dresden")
//            else -> Address("irgendwo")
//        }
//
//        listOf(
//            Person("Greta", i, address, listOf(lotr, got)),
//            Person("Nadin", i, address, listOf(hp)),
//            Person("Karsten", i, address, listOf(lotr)),
//            Person("Eddi", i, address, listOf(got))
//        )
//    }.flatten()
//
//
//    val result = db.query {
//        val objs = LET("objs") { personInserts }
//
//        FOR(objs) { o ->
//            FILTER(o.name EQ "Greta")
//            RETURN(o)
//        }
//    }
//
//    println(result.query.aql)
//
//    result.forEach { println(it) }
//
//    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
//}
