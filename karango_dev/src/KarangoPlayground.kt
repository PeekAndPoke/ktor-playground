package de.peekandpoke.karango_dev

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.karango_dev.domain.*
import de.peekandpoke.ultra.kontainer.kontainer
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.EntityCache
import de.peekandpoke.ultra.vault.NullEntityCache
import de.peekandpoke.ultra.vault.SharedRepoClassLookup
import kotlin.system.measureTimeMillis

private val arangoDb: ArangoDB = ArangoDB.Builder().user("root").password("").host("localhost", 8529).build()
private val arangoDatabase: ArangoDatabase = arangoDb.db("kotlindev")

val kontainer = kontainer {

    singleton(SharedRepoClassLookup::class)
    singleton(Database::class)
    dynamic(EntityCache::class, NullEntityCache::class)

    instance(arangoDatabase)
    singleton(KarangoDriver::class)

    singleton(PersonsRepository::class)

}.useWith()

private val db = kontainer.get<Database>()
private val persons = db.getRepository<PersonsRepository>()

@Mutable
data class FrozenAddress(val city: String, val zip: String)

@Mutable
data class FrozenPerson(val name: String, val age: Int, val address: FrozenAddress)

//@Mutator
//data class FrozenCompany(val boss: FrozenPerson, val addresses: List<FrozenAddress>)
//
//val FrozenCompanyMutator.addresses
//    get() = getResult().addresses.mutator(
//        { modify(getResult()::addresses, it) },
//        { item, onModify -> item.mutator(onModify) }
//    )

@Mutable
data class FrozenCompany(
    val boss: FrozenPerson,
    val names: List<String>,
    val addresses: List<FrozenAddress>,
    val listOfLists: List<List<FrozenAddress>> = listOf(),
    val listOfListOfLists: List<List<List<FrozenAddress>>> = listOf(),
    val listOfListOfMaps: List<List<Map<String, FrozenAddress>>> = listOf(),
    val listOfMapOfLists: List<Map<String, List<FrozenAddress>>> = listOf(),
    val set: Set<FrozenAddress> = setOf(),
    val map: Map<String, FrozenAddress> = mapOf()
)

//@Mutator
//data class FrozenCompany(val boss: FrozenPerson, val addresses: List<List<FrozenAddress>>)

//val FrozenCompanyMutator.addresses
//    get() = getResult().addresses.mutator(
//        { modify(getResult()::addresses, it) },
//        { i1, on1 -> i1.mutator(on1) { i2, on2 -> i2.mutator(on2) } }
//    )

fun main() {

    // warm up
//    repeat(1000) { playWithMutator() }

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
        listOf("Google", "alphabet"),
        listOf(
            FrozenAddress("Berlin", "10117"),
            FrozenAddress("München", "80637")
        ),
        set = setOf(
            FrozenAddress("Berlin", "10117"),
            FrozenAddress("München", "80637")
        )
//        listOf(
//            listOf(
//                FrozenAddress("Berlin", "10117"),
//                FrozenAddress("München", "80637")
//            ),
//            listOf(
//                FrozenAddress("Chemnitz", "01234"),
//                FrozenAddress("Jena", "121212")
//            )
//        )
    )

    val mutation = company.mutate {

        boss.name.apply { toUpperCase() }
        boss.age *= 10

        boss.address.city = "Aue"
        boss.address.zip = "08280"

        addresses[0].city = "CHANGED"

        set.forEach {
            it.city.apply { toUpperCase() + "_aa" }
        }

//        it.addresses[0].push(FrozenAddress("Some city", "45454"))
//
//        it.addresses.forEach { addresses ->
//            addresses.forEach { address ->
//                address.city = address.city.toUpperCase()
//            }
//        }
    }

    println(company)
    println(mutation)

    println("////  list  ////")

    mutation.addresses.forEach {
        println(it)
        println(it.hashCode())
    }

    println("////  set  ////")

    mutation.set.forEach {
        println(it)
        println(it.hashCode())
    }
}

@Suppress("UNUSED_VARIABLE")
fun y() {

//    val addresses = db.collection(Address.)

    exampleReturningFromScalarLet()
//    exampleReturningFromIterableLet(db)

    persons.removeAll()

    val insertTime = measureTimeMillis {
        exampleInsertFromLet()
    }

    println("Insertion took $insertTime ms")

    for (y in 1..10) {
        val timeAll = measureTimeMillis {

            val result = persons.query {

                val str = LET("str", "Karsten")

                val names = LET("names") {
                    listOf("J.R.R.", "X.X.X.")
                }

                FOR(Persons) { person ->

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

fun exampleReturningFromScalarLet() {

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
    println("//  EXAMPLE: Returning a scalar value that was sent in via LET")

    val result = persons.query {
        val let = LET("let", "TEST")

        RETURN(let)
    }

    println("---------------------------------------------------------------------------------------------------------")
    result.forEach { println(it) }
    println("---------------------------------------------------------------------------------------------------------")
    println()
}

fun exampleReturningFromIterableLet() {

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
    println("//  EXAMPLE: Returning iterable values that where sent in via LET")

    val result = persons.query {
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


    val result = persons.query {
        val objs = LET("objs") { personInserts }

        FOR(objs) { o ->
            INSERT(o) INTO Persons
        }
    }

    println("Inserted " + result.stats.writesExecuted)

    println("/////////////////////////////////////////////////////////////////////////////////////////////////////////")
}


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
