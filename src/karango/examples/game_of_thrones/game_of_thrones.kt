package de.peekandpoke.karango.examples.game_of_thrones

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.examples.printDivider
import de.peekandpoke.karango.examples.printQueryResult
import de.peekandpoke.karango.examples.runDemo
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.kontainer.kontainer
import de.peekandpoke.ultra.vault.*

private val arangoDb: ArangoDB = ArangoDB.Builder().user("root").password("").host("localhost", 8529).build()
private val arangoDatabase: ArangoDatabase = arangoDb.db("kotlindev")

val kontainer = kontainer {

    singleton(SharedRepoClassLookup::class)
    singleton(Database::class)
    dynamic(EntityCache::class, NullEntityCache::class)

    instance(arangoDatabase)
    singleton(KarangoDriver::class)

    singleton(CharactersRepository::class)
    singleton(ActorsRepository::class)

}.useWith()

private val db = kontainer.get<Database>()

private val characters = db.characters
private val actors = db.actors

fun main() {

    runDemo {

        //        noop()

        steps(
            ::intro,
            ::clearData,
            ::installData,
            ::findBaratheons,
            ::findStarks,
            ::findBranStarkV1,
            ::findBranStarkV2,
            ::findThreeCharactersByIds,
            ::updateNedStarksAliveness
        )
    }
}

fun intro() {
    printDivider()
    println(
        """
        Welcome to the Karango - Game of Thrones Demo !

        For details see https://docs.arangodb.com/3.4/AQL/Tutorial/CRUD.html
        """.trimIndent()
    )
    printDivider()
    println()
}

fun clearData() {

    println("==========================================================================================================================")
    println("Clear all existing characters")

    characters.removeAll()

    println("Clear all existing actors")

    actors.removeAll()
}

fun installData() {
    println("==========================================================================================================================")
    println("Inserting all characters using a LET statement")

    val markAddy = actors.save(Actor("Mark", "Addy", 50))
    val seanBean = actors.save(Actor("Sean", "Bean", 55))

    println(seanBean)

    characters.query {

        val data = LET("data") {
            listOf(
                Character(name = "Robert", surname = "Baratheon", alive = false, traits = listOf("A", "H", "C"), actor = markAddy.asRef),
                Character(name = "Jaime", surname = "Lannister", alive = true, age = 36, traits = listOf("A", "F", "B"), actor = markAddy.asRef),
                Character(name = "Eddard", surname = "Stark", alive = true, age = 47, traits = listOf("D", "H", "C"), actor = seanBean.asRef),
                Character(name = "Catelyn", surname = "Stark", alive = true, age = 40, traits = listOf("D", "H", "C")),
                Character(name = "Cersei", surname = "Lannister", alive = true, age = 36, traits = listOf("H", "E", "F")),
                Character(name = "Daenerys", surname = "Targaryen", alive = true, age = 16, traits = listOf("D", "H", "C")),
                Character(name = "Jorah", surname = "Mormont", alive = false, traits = listOf("A", "B", "C", "F")),
                Character(name = "Petyr", surname = "Baelish", alive = false, traits = listOf("E", "G", "F")),
                Character(name = "Viserys", surname = "Targaryen", alive = false, traits = listOf("O", "L", "N")),
                Character(name = "Jon", surname = "Snow", alive = true, age = 16, traits = listOf("A", "B", "C", "F")),
                Character(name = "Sansa", surname = "Stark", alive = true, age = 13, traits = listOf("D", "I", "J")),
                Character(name = "Arya", surname = "Stark", alive = true, age = 11, traits = listOf("C", "K", "L")),
                Character(name = "Robb", surname = "Stark", alive = false, traits = listOf("A", "B", "C", "K")),
                Character(name = "Theon", surname = "Greyjoy", alive = true, age = 16, traits = listOf("E", "R", "K")),
                Character(name = "Bran", surname = "Stark", alive = true, age = 10, traits = listOf("L", "J")),
                Character(name = "Joffrey", surname = "Baratheon", alive = false, age = 19, traits = listOf("I", "L", "O")),
                Character(name = "Sandor", surname = "Clegane", alive = true, traits = listOf("A", "P", "K", "F")),
                Character(name = "Tyrion", surname = "Lannister", alive = true, age = 32, traits = listOf("F", "K", "M", "N")),
                Character(name = "Khal", surname = "Drogo", alive = false, traits = listOf("A", "C", "O", "P")),
                Character(name = "Tywin", surname = "Lannister", alive = false, traits = listOf("O", "M", "H", "F")),
                Character(name = "Davos", surname = "Seaworth", alive = true, age = 49, traits = listOf("C", "K", "P", "F")),
                Character(name = "Samwell", surname = "Tarly", alive = true, age = 17, traits = listOf("C", "L", "I")),
                Character(name = "Stannis", surname = "Baratheon", alive = false, traits = listOf("H", "O", "P", "M")),
                Character(name = "Melisandre", alive = true, traits = listOf("G", "E", "H")),
                Character(name = "Margaery", surname = "Tyrell", alive = false, traits = listOf("M", "D", "B")),
                Character(name = "Jeor", surname = "Mormont", alive = false, traits = listOf("C", "H", "M", "P")),
                Character(name = "Bronn", alive = true, traits = listOf("K", "E", "C")),
                Character(name = "Varys", alive = true, traits = listOf("M", "F", "N", "E")),
                Character(name = "Shae", alive = false, traits = listOf("M", "D", "G")),
                Character(name = "Talisa", surname = "Maegyr", alive = false, traits = listOf("D", "C", "B")),
                Character(name = "Gendry", alive = false, traits = listOf("K", "C", "A")),
                Character(name = "Ygritte", alive = false, traits = listOf("A", "P", "K")),
                Character(name = "Tormund", surname = "Giantsbane", alive = true, traits = listOf("C", "P", "A", "I")),
                Character(name = "Gilly", alive = true, traits = listOf("L", "J")),
                Character(name = "Brienne", surname = "Tarth", alive = true, age = 32, traits = listOf("P", "C", "A", "K")),
                Character(name = "Ramsay", surname = "Bolton", alive = true, traits = listOf("E", "O", "G", "A")),
                Character(name = "Ellaria", surname = "Sand", alive = true, traits = listOf("P", "O", "A", "E")),
                Character(name = "Daario", surname = "Naharis", alive = true, traits = listOf("K", "P", "A")),
                Character(name = "Missandei", alive = true, traits = listOf("D", "L", "C", "M")),
                Character(name = "Tommen", surname = "Baratheon", alive = true, traits = listOf("I", "L", "B")),
                Character(name = "Jaqen", surname = "H'ghar", alive = true, traits = listOf("H", "F", "K")),
                Character(name = "Roose", surname = "Bolton", alive = true, traits = listOf("H", "E", "F", "A")),
                Character(name = "The High Sparrow", alive = true, traits = listOf("H", "M", "F", "O"))
            )
        }

        FOR(data) { d ->
            INSERT(d) INTO (Characters)
        }
    }
}

private fun printCharacter(idx: Int, it: Character) =
    "  ${idx + 1}. ${it.name} ${it.surname} - age ${it.age} - ${if (it.alive) "alive" else "gone"} - actor: ${it.actor}"

private fun printCharacter(idx: Int, it: Stored<Character>) = printCharacter(idx, it.value)

fun findBaratheons() {
    println("==========================================================================================================================")
    println("Find all Baratheons using our collection class")

    val result = characters.find {
        FOR(Characters) { c ->
            FILTER(c.surname EQ "Baratheon")
            RETURN(c)
        }
    }

    printQueryResult(result, ::printCharacter)
}

fun findStarks() {
    println("==========================================================================================================================")
    println("Find all Starks with an explicit query on the db object")

    val result = characters.find {
        FOR(Characters) { c ->
            FILTER(c.surname EQ "Stark")
            RETURN(c)
        }
    }

    printQueryResult(result, ::printCharacter)
}

fun findBranStarkV1() {

    // to do what we want to do, we need the ID of Bran Stark
    val bransId = characters.findFirstByNameAndSurname("Bran", "Stark")!!._id

    println("==========================================================================================================================")
    println("Find Bran Stark by ID with an explicit query on the db object")

    val result = characters.find {
        RETURN(
            DOCUMENT(bransId)
        )
    }

    printQueryResult(result, ::printCharacter)
}

fun findBranStarkV2() {

    // to do what we want to do, we need the ID of Bran Stark
    val bransId = characters.findFirstByNameAndSurname("Bran", "Stark")!!._id

    println("==========================================================================================================================")
    println("Find Bran Stark by ID using our collection class")

    val result = characters.findById(bransId)!!

    println()
    println(printCharacter(0, result))
    println()
}

fun findThreeCharactersByIds() {

    // to do what we want to do, we need the ID of Bran Stark
    val bransId = characters.findFirstByNameAndSurname("Bran", "Stark")!!._id
    val aryasId = characters.findFirstByNameAndSurname("Arya", "Stark")!!._id
    val tyrionsId = characters.findFirstByNameAndSurname("Tyrion", "Lannister")!!._id

    println("==========================================================================================================================")
    println("Find Arya, Bran and Tyrion at once by their IDs using our collection class")

    val result = characters.findByIds(aryasId, bransId, tyrionsId)

    printQueryResult(result, ::printCharacter)
}

fun updateNedStarksAliveness() {

//    val ned = characters.findFirstByNameAndSurname("Eddard", "Stark")!!
//
//    println("==========================================================================================================================")
//    println("Spoiler Alert! Ned died... So we need to update his aliveness using the collection object")

//    val result = characters.update(ned) { t ->
//        //        t.alive with false
////        "hair" with "black"
//    }
//
//    printQueryResult(result) { i, x -> "$i. $x" }
}
