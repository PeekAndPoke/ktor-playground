package de.peekandpoke.karango.examples.game_of_thrones

import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.query.AND
import de.peekandpoke.karango.query.EQ

val db = Db.default(user = "root", pass = "root", host = "localhost", port = 8529, database = "kotlindev")

val characters = db.collection(CharacterCollection)

fun main() {

    clearData()

    installData()

    queryBaratheons()

    queryStarks()

    queryBranStarkV1()

    queryBranStarkV2()

    queryThreeCharactersByIdV1()
}

fun clearData() {

    println("==========================================================================================================================")
    println("Clear all existing characters")

    characters.removeAll()
}

fun installData() {
    println("==========================================================================================================================")
    println("Inserting all characters using a LET statement")

    db.query {

        val data = LET("data") {
            listOf(
                Character(name = "Robert", surname = "Baratheon", alive = false, traits = listOf("A", "H", "C")),
                Character(name = "Jaime", surname = "Lannister", alive = true, age = 36, traits = listOf("A", "F", "B")),
                Character(name = "Catelyn", surname = "Stark", alive = false, age = 40, traits = listOf("D", "H", "C")),
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
            INSERT(d) INTO (CharacterCollection)
        }
    }
}

private fun <T> printQueryResult(result: Cursor<T>, output: (Int, T) -> String) {

    println()
    println("+-----------+")
    println("| AQL query |")
    println("+-----------+")
    println(result.query.aql)

    println("+------------+")
    println("| Query vars |")
    println("+------------+")
    println(result.query.vars)

    println()
    println("And we found (in ${result.timeMs} ms, execution time ${result.stats.executionTime} ms):")

    result.forEachIndexed { idx, it -> println(output(idx, it)) }

    println()
}

private fun printCharacter(idx: Int, it : Character) =
    "  ${idx + 1}. ${it.name} ${it.surname}; age ${it.age}; ${if (it.alive) "alive" else "gone"}"

fun queryBaratheons() {
    println("==========================================================================================================================")
    println("Querying all Baratheons using our collection class")

    val result = characters.query { c ->
        FILTER { c.surname EQ "Baratheon" }
    }

    printQueryResult(result, ::printCharacter)

}

fun queryStarks() {
    println("==========================================================================================================================")
    println("Querying all Starks with an explicit query on the db object")

    val result = db.query {
        FOR(CharacterCollection) { c ->
            FILTER { c.surname EQ "Stark" }
            RETURN(c)
        }
    }

    printQueryResult(result, ::printCharacter)
}

fun queryBranStarkV1() {

    // to do what we want to do, we need the ID of Bran Stark
    val bransId = characters.queryOne { t -> FILTER { (t.name EQ "Bran") AND (t.surname EQ "Stark") } }!!._id

    println("==========================================================================================================================")
    println("Query Bran Stark by ID with an explicit query on the db object")

    val result = db.query {
        RETURN<Character>(bransId)
    }

    printQueryResult(result, ::printCharacter)
}

fun queryBranStarkV2() {

    // to do what we want to do, we need the ID of Bran Stark
    val bransId = characters.queryOne { t -> FILTER { (t.name EQ "Bran") AND (t.surname EQ "Stark") } }!!._id

    println("==========================================================================================================================")
    println("Query Bran Stark by ID using our collection class")

    val result = characters.queryByKey(bransId)!!

    printCharacter(1, result)
    println()
}

fun queryThreeCharactersByIdV1() {

    // to do what we want to do, we need the ID of Bran Stark
    val bransId = characters.queryOne { t -> FILTER { (t.name EQ "Bran") AND (t.surname EQ "Stark") } }!!._id
    val aryasId = characters.queryOne { t -> FILTER { (t.name EQ "Arya") AND (t.surname EQ "Stark") } }!!._id
    val tyrionsId = characters.queryOne { t -> FILTER { (t.name EQ "Tyrion") AND (t.surname EQ "Lannister") } }!!._id

    println("==========================================================================================================================")
    println("Query Arya, Bran and Tyrion at once by their IDs using the db object")

    val result = db.query {
        RETURN<Character>(aryasId, bransId, tyrionsId)
    }

    printQueryResult(result, ::printCharacter)
}
