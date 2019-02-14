@file:Suppress("LocalVariableName")

package de.peekandpoke.karango.examples.movies_and_actors

import de.peekandpoke.karango.Db
import kotlin.system.exitProcess

/**
 * See https://docs.arangodb.com/3.4/Cookbook/Graph/ExampleActorsAndMovies.html
 */
fun main() {

    val db = Db.default(user = "root", pass = "root", host = "localhost", port = 8529, database = "kotlindev")

    val movies = db.collection(MovieCollection)
    movies.removeAll()

    val actors = db.collection(ActorCollection)
    actors.removeAll()

    val actsIn = db.edgeCollection(ActsIn.Collection)
    actsIn.removeAll()

    val TheMatrix = movies.save(Movie(_key = "TheMatrix", title = "The Matrix", released = 1999, tagline = "Welcome to the Real World"))._id
    val Keanu = actors.save(Actor(_key = "Keanu", name = "Keanu Reeves", born = 1964))._id
    val Carrie = actors.save(Actor(_key = "Carrie", name = "Carrie-Anne Moss", born = 1967))._id
    val Laurence = actors.save(Actor(_key = "Laurence", name = "Laurence Fishburne", born = 1961))._id
    val Hugo = actors.save(Actor(_key = "Hugo", name = "Hugo Weaving", born = 1960))._id
    val Emil = actors.save(Actor(_key = "Emil", name = "Emil Eifrem", born = 1978))._id

    actsIn.save(ActsIn(_from = Keanu, _to = TheMatrix, roles = listOf("Neo"), year = 1999))
    actsIn.save(ActsIn(_from = Carrie, _to = TheMatrix, roles = listOf("Trinity"), year = 1999))
    actsIn.save(ActsIn(_from = Laurence, _to = TheMatrix, roles = listOf("Morpheus"), year = 1999))
    actsIn.save(ActsIn(_from = Hugo, _to = TheMatrix, roles = listOf("Agent Smith"), year = 1999))
    actsIn.save(ActsIn(_from = Emil, _to = TheMatrix, roles = listOf("Emil"), year = 1999))

    val TheMatrixReloaded = movies.save(Movie(_key = "TheMatrixReloaded", title = "The Matrix Reloaded", released = 2003, tagline = "Free your mind"))._id
    actsIn.save(ActsIn(_from = Keanu, _to = TheMatrixReloaded, roles = listOf("Neo"), year = 2003))
    actsIn.save(ActsIn(_from = Carrie, _to = TheMatrixReloaded, roles = listOf("Trinity"), year = 2003))
    actsIn.save(ActsIn(_from = Laurence, _to = TheMatrixReloaded, roles = listOf("Morpheus"), year = 2003))
    actsIn.save(ActsIn(_from = Hugo, _to = TheMatrixReloaded, roles = listOf("Agent Smith"), year = 2003))

    val TheMatrixRevolutions = movies.save(
        Movie(
            _key = "TheMatrixRevolutions",
            title = "The Matrix Revolutions",
            released = 2003,
            tagline = "Everything that has a beginning has an end"
        )
    )._id
    actsIn.save(ActsIn(_from = Keanu, _to = TheMatrixRevolutions, roles = listOf("Neo"), year = 2003))
    actsIn.save(ActsIn(_from = Carrie, _to = TheMatrixRevolutions, roles = listOf("Trinity"), year = 2003))
    actsIn.save(ActsIn(_from = Laurence, _to = TheMatrixRevolutions, roles = listOf("Morpheus"), year = 2003))
    actsIn.save(ActsIn(_from = Hugo, _to = TheMatrixRevolutions, roles = listOf("Agent Smith"), year = 2003))

    val TheDevilsAdvocate =
        movies.save(Movie(_key = "TheDevilsAdvocate", title = "The Devil's Advocate", released = 1997, tagline = "Evil has its winning ways"))._id
    val Charlize = actors.save(Actor(_key = "Charlize", name = "Charlize Theron", born = 1975))._id
    val Al = actors.save(Actor(_key = "Al", name = "Al Pacino", born = 1940))._id
    actsIn.save(ActsIn(_from = Keanu, _to = TheDevilsAdvocate, roles = listOf("Kevin Lomax"), year = 1997))
    actsIn.save(ActsIn(_from = Charlize, _to = TheDevilsAdvocate, roles = listOf("Mary Ann Lomax"), year = 1997))
    actsIn.save(ActsIn(_from = Al, _to = TheDevilsAdvocate, roles = listOf("John Milton"), year = 1997))

    val AFewGoodMen = movies.save(
        Movie(
            _key = "AFewGoodMen",
            title = "A Few Good Men",
            released = 1992,
            tagline = "In the heart of the nation's capital, in a courthouse of the U.S. government, one man will stop at nothing to keep his honor, and one will stop at nothing to find the truth."
        )
    )._id
    val TomC = actors.save(Actor(_key = "TomC", name = "Tom Cruise", born = 1962))._id
    val JackN = actors.save(Actor(_key = "JackN", name = "Jack Nicholson", born = 1937))._id
    val DemiM = actors.save(Actor(_key = "DemiM", name = "Demi Moore", born = 1962))._id
    val KevinB = actors.save(Actor(_key = "KevinB", name = "Kevin Bacon", born = 1958))._id
    val KieferS = actors.save(Actor(_key = "KieferS", name = "Kiefer Sutherland", born = 1966))._id
    val NoahW = actors.save(Actor(_key = "NoahW", name = "Noah Wyle", born = 1971))._id
    val CubaG = actors.save(Actor(_key = "CubaG", name = "Cuba Gooding Jr.", born = 1968))._id
    val KevinP = actors.save(Actor(_key = "KevinP", name = "Kevin Pollak", born = 1957))._id
    val JTW = actors.save(Actor(_key = "JTW", name = "J.T. Walsh", born = 1943))._id
    val JamesM = actors.save(Actor(_key = "JamesM", name = "James Marshall", born = 1967))._id
    val ChristopherG = actors.save(Actor(_key = "ChristopherG", name = "Christopher Guest", born = 1948))._id
    actsIn.save(ActsIn(_from = TomC, _to = AFewGoodMen, roles = listOf("Lt. Daniel Kaffee"), year = 1992))
    actsIn.save(ActsIn(_from = JackN, _to = AFewGoodMen, roles = listOf("Col. Nathan R. Jessup"), year = 1992))
    actsIn.save(ActsIn(_from = DemiM, _to = AFewGoodMen, roles = listOf("Lt. Cdr. JoAnne Galloway"), year = 1992))
    actsIn.save(ActsIn(_from = KevinB, _to = AFewGoodMen, roles = listOf("Capt. Jack Ross"), year = 1992))
    actsIn.save(ActsIn(_from = KieferS, _to = AFewGoodMen, roles = listOf("Lt. Jonathan Kendrick"), year = 1992))
    actsIn.save(ActsIn(_from = NoahW, _to = AFewGoodMen, roles = listOf("Cpl. Jeffrey Barnes"), year = 1992))
    actsIn.save(ActsIn(_from = CubaG, _to = AFewGoodMen, roles = listOf("Cpl. Carl Hammaker"), year = 1992))
    actsIn.save(ActsIn(_from = KevinP, _to = AFewGoodMen, roles = listOf("Lt. Sam Weinberg"), year = 1992))
    actsIn.save(ActsIn(_from = JTW, _to = AFewGoodMen, roles = listOf("Lt. Col. Matthew Andrew Markinson"), year = 1992))
    actsIn.save(ActsIn(_from = JamesM, _to = AFewGoodMen, roles = listOf("Pfc. Louden Downey"), year = 1992))
    actsIn.save(ActsIn(_from = ChristopherG, _to = AFewGoodMen, roles = listOf("Dr. Stone"), year = 1992))

    val TopGun = movies.save(Movie(_key = "TopGun", title = "Top Gun", released = 1986, tagline = "I feel the need, the need for speed."))._id
    val KellyM = actors.save(Actor(_key = "KellyM", name = "Kelly McGillis", born = 1957))._id
    val ValK = actors.save(Actor(_key = "ValK", name = "Val Kilmer", born = 1959))._id
    val AnthonyE = actors.save(Actor(_key = "AnthonyE", name = "Anthony Edwards", born = 1962))._id
    val TomS = actors.save(Actor(_key = "TomS", name = "Tom Skerritt", born = 1933))._id
    val MegR = actors.save(Actor(_key = "MegR", name = "Meg Ryan", born = 1961))._id
    actsIn.save(ActsIn(_from = TomC, _to = TopGun, roles = listOf("Maverick"), year = 1986))
    actsIn.save(ActsIn(_from = KellyM, _to = TopGun, roles = listOf("Charlie"), year = 1986))
    actsIn.save(ActsIn(_from = ValK, _to = TopGun, roles = listOf("Iceman"), year = 1986))
    actsIn.save(ActsIn(_from = AnthonyE, _to = TopGun, roles = listOf("Goose"), year = 1986))
    actsIn.save(ActsIn(_from = TomS, _to = TopGun, roles = listOf("Viper"), year = 1986))
    actsIn.save(ActsIn(_from = MegR, _to = TopGun, roles = listOf("Carole"), year = 1986))

    val JerryMaguire = movies.save(Movie(_key = "JerryMaguire", title = "Jerry Maguire", released = 2000, tagline = "The rest of his life begins now."))._id
    val ReneeZ = actors.save(Actor(_key = "ReneeZ", name = "Renee Zellweger", born = 1969))._id
    val KellyP = actors.save(Actor(_key = "KellyP", name = "Kelly Preston", born = 1962))._id
    val JerryO = actors.save(Actor(_key = "JerryO", name = "Jerry O'Connell", born = 1974))._id
    val JayM = actors.save(Actor(_key = "JayM", name = "Jay Mohr", born = 1970))._id
    val BonnieH = actors.save(Actor(_key = "BonnieH", name = "Bonnie Hunt", born = 1961))._id
    val ReginaK = actors.save(Actor(_key = "ReginaK", name = "Regina King", born = 1971))._id
    val JonathanL = actors.save(Actor(_key = "JonathanL", name = "Jonathan Lipnicki", born = 1996))._id
    actsIn.save(ActsIn(_from = TomC, _to = JerryMaguire, roles = listOf("Jerry Maguire"), year = 2000))
    actsIn.save(ActsIn(_from = CubaG, _to = JerryMaguire, roles = listOf("Rod Tidwell"), year = 2000))
    actsIn.save(ActsIn(_from = ReneeZ, _to = JerryMaguire, roles = listOf("Dorothy Boyd"), year = 2000))
    actsIn.save(ActsIn(_from = KellyP, _to = JerryMaguire, roles = listOf("Avery Bishop"), year = 2000))
    actsIn.save(ActsIn(_from = JerryO, _to = JerryMaguire, roles = listOf("Frank Cushman"), year = 2000))
    actsIn.save(ActsIn(_from = JayM, _to = JerryMaguire, roles = listOf("Bob Sugar"), year = 2000))
    actsIn.save(ActsIn(_from = BonnieH, _to = JerryMaguire, roles = listOf("Laurel Boyd"), year = 2000))
    actsIn.save(ActsIn(_from = ReginaK, _to = JerryMaguire, roles = listOf("Marcee Tidwell"), year = 2000))
    actsIn.save(ActsIn(_from = JonathanL, _to = JerryMaguire, roles = listOf("Ray Boyd"), year = 2000))

    val StandByMe = movies.save(
        Movie(
            _key = "StandByMe",
            title = "Stand By Me",
            released = 1986,
            tagline = "For some, it's the last real taste of innocence, and the first real taste of life. But for everyone, it's the time that memories are made of."
        )
    )._id
    val RiverP = actors.save(Actor(_key = "RiverP", name = "River Phoenix", born = 1970))._id
    val CoreyF = actors.save(Actor(_key = "CoreyF", name = "Corey Feldman", born = 1971))._id
    val WilW = actors.save(Actor(_key = "WilW", name = "Wil Wheaton", born = 1972))._id
    val JohnC = actors.save(Actor(_key = "JohnC", name = "John Cusack", born = 1966))._id
    val MarshallB = actors.save(Actor(_key = "MarshallB", name = "Marshall Bell", born = 1942))._id
    actsIn.save(ActsIn(_from = WilW, _to = StandByMe, roles = listOf("Gordie Lachance"), year = 1986))
    actsIn.save(ActsIn(_from = RiverP, _to = StandByMe, roles = listOf("Chris Chambers"), year = 1986))
    actsIn.save(ActsIn(_from = JerryO, _to = StandByMe, roles = listOf("Vern Tessio"), year = 1986))
    actsIn.save(ActsIn(_from = CoreyF, _to = StandByMe, roles = listOf("Teddy Duchamp"), year = 1986))
    actsIn.save(ActsIn(_from = JohnC, _to = StandByMe, roles = listOf("Denny Lachance"), year = 1986))
    actsIn.save(ActsIn(_from = KieferS, _to = StandByMe, roles = listOf("Ace Merrill"), year = 1986))
    actsIn.save(ActsIn(_from = MarshallB, _to = StandByMe, roles = listOf("Mr. Lachance"), year = 1986))

    val AsGoodAsItGets = movies.save(
        Movie(
            _key = "AsGoodAsItGets",
            title = "As Good as It Gets",
            released = 1997,
            tagline = "A comedy from the heart that goes for the throat."
        )
    )._id
    val HelenH = actors.save(Actor(_key = "HelenH", name = "Helen Hunt", born = 1963))._id
    val GregK = actors.save(Actor(_key = "GregK", name = "Greg Kinnear", born = 1963))._id
    actsIn.save(ActsIn(_from = JackN, _to = AsGoodAsItGets, roles = listOf("Melvin Udall"), year = 1997))
    actsIn.save(ActsIn(_from = HelenH, _to = AsGoodAsItGets, roles = listOf("Carol Connelly"), year = 1997))
    actsIn.save(ActsIn(_from = GregK, _to = AsGoodAsItGets, roles = listOf("Simon Bishop"), year = 1997))
    actsIn.save(ActsIn(_from = CubaG, _to = AsGoodAsItGets, roles = listOf("Frank Sachs"), year = 1997))

    val WhatDreamsMayCome = movies.save(
        Movie(
            _key = "WhatDreamsMayCome",
            title = "What Dreams May Come",
            released = 1998,
            tagline = "After life there is more. The end is just the beginning."
        )
    )._id
    val AnnabellaS = actors.save(Actor(_key = "AnnabellaS", name = "Annabella Sciorra", born = 1960))._id
    val MaxS = actors.save(Actor(_key = "MaxS", name = "Max von Sydow", born = 1929))._id
    val WernerH = actors.save(Actor(_key = "WernerH", name = "Werner Herzog", born = 1942))._id
    val Robin = actors.save(Actor(_key = "Robin", name = "Robin Williams", born = 1951))._id
    actsIn.save(ActsIn(_from = Robin, _to = WhatDreamsMayCome, roles = listOf("Chris Nielsen"), year = 1998))
    actsIn.save(ActsIn(_from = CubaG, _to = WhatDreamsMayCome, roles = listOf("Albert Lewis"), year = 1998))
    actsIn.save(ActsIn(_from = AnnabellaS, _to = WhatDreamsMayCome, roles = listOf("Annie Collins-Nielsen"), year = 1998))
    actsIn.save(ActsIn(_from = MaxS, _to = WhatDreamsMayCome, roles = listOf("The Tracker"), year = 1998))
    actsIn.save(ActsIn(_from = WernerH, _to = WhatDreamsMayCome, roles = listOf("The Face"), year = 1998))

    val SnowFallingonCedars =
        movies.save(Movie(_key = "SnowFallingonCedars", title = "Snow Falling on Cedars", released = 1999, tagline = "First loves last. Forever."))._id
    val EthanH = actors.save(Actor(_key = "EthanH", name = "Ethan Hawke", born = 1970))._id
    val RickY = actors.save(Actor(_key = "RickY", name = "Rick Yune", born = 1971))._id
    val JamesC = actors.save(Actor(_key = "JamesC", name = "James Cromwell", born = 1940))._id
    actsIn.save(ActsIn(_from = EthanH, _to = SnowFallingonCedars, roles = listOf("Ishmael Chambers"), year = 1999))
    actsIn.save(ActsIn(_from = RickY, _to = SnowFallingonCedars, roles = listOf("Kazuo Miyamoto"), year = 1999))
    actsIn.save(ActsIn(_from = MaxS, _to = SnowFallingonCedars, roles = listOf("Nels Gudmundsson"), year = 1999))
    actsIn.save(ActsIn(_from = JamesC, _to = SnowFallingonCedars, roles = listOf("Judge Fielding"), year = 1999))

    val YouveGotMail =
        movies.save(Movie(_key = "YouveGotMail", title = "You've Got Mail", released = 1998, tagline = "At odds in life... in love on-line."))._id
    val ParkerP = actors.save(Actor(_key = "ParkerP", name = "Parker Posey", born = 1968))._id
    val DaveC = actors.save(Actor(_key = "DaveC", name = "Dave Chappelle", born = 1973))._id
    val SteveZ = actors.save(Actor(_key = "SteveZ", name = "Steve Zahn", born = 1967))._id
    val TomH = actors.save(Actor(_key = "TomH", name = "Tom Hanks", born = 1956))._id
    actsIn.save(ActsIn(_from = TomH, _to = YouveGotMail, roles = listOf("Joe Fox"), year = 1998))
    actsIn.save(ActsIn(_from = MegR, _to = YouveGotMail, roles = listOf("Kathleen Kelly"), year = 1998))
    actsIn.save(ActsIn(_from = GregK, _to = YouveGotMail, roles = listOf("Frank Navasky"), year = 1998))
    actsIn.save(ActsIn(_from = ParkerP, _to = YouveGotMail, roles = listOf("Patricia Eden"), year = 1998))
    actsIn.save(ActsIn(_from = DaveC, _to = YouveGotMail, roles = listOf("Kevin Jackson"), year = 1998))
    actsIn.save(ActsIn(_from = SteveZ, _to = YouveGotMail, roles = listOf("George Pappas"), year = 1998))

    val SleeplessInSeattle = movies.save(
        Movie(
            _key = "SleeplessInSeattle",
            title = "Sleepless in Seattle",
            released = 1993,
            tagline = "What if someone you never met, someone you never saw, someone you never knew was the only someone for you?"
        )
    )._id
    val RitaW = actors.save(Actor(_key = "RitaW", name = "Rita Wilson", born = 1956))._id
    val BillPull = actors.save(Actor(_key = "BillPull", name = "Bill Pullman", born = 1953))._id
    val VictorG = actors.save(Actor(_key = "VictorG", name = "Victor Garber", born = 1949))._id
    val RosieO = actors.save(Actor(_key = "RosieO", name = "Rosie O'Donnell", born = 1962))._id
    actsIn.save(ActsIn(_from = TomH, _to = SleeplessInSeattle, roles = listOf("Sam Baldwin"), year = 1993))
    actsIn.save(ActsIn(_from = MegR, _to = SleeplessInSeattle, roles = listOf("Annie Reed"), year = 1993))
    actsIn.save(ActsIn(_from = RitaW, _to = SleeplessInSeattle, roles = listOf("Suzy"), year = 1993))
    actsIn.save(ActsIn(_from = BillPull, _to = SleeplessInSeattle, roles = listOf("Walter"), year = 1993))
    actsIn.save(ActsIn(_from = VictorG, _to = SleeplessInSeattle, roles = listOf("Greg"), year = 1993))
    actsIn.save(ActsIn(_from = RosieO, _to = SleeplessInSeattle, roles = listOf("Becky"), year = 1993))

    val JoeVersustheVolcano = movies.save(
        Movie(
            _key = "JoeVersustheVolcano",
            title = "Joe Versus the Volcano",
            released = 1990,
            tagline = "A story of love, lava and burning desire."
        )
    )._id
    val Nathan = actors.save(Actor(_key = "Nathan", name = "Nathan Lane", born = 1956))._id
    actsIn.save(ActsIn(_from = TomH, _to = JoeVersustheVolcano, roles = listOf("Joe Banks"), year = 1990))
    actsIn.save(ActsIn(_from = MegR, _to = JoeVersustheVolcano, roles = listOf("DeDe', 'Angelica Graynamore', 'Patricia Graynamore"), year = 1990))
    actsIn.save(ActsIn(_from = Nathan, _to = JoeVersustheVolcano, roles = listOf("Baw"), year = 1990))

    val WhenHarryMetSally =
        movies.save(Movie(_key = "WhenHarryMetSally", title = "When Harry Met Sally", released = 1998, tagline = "At odds in life... in love on-line."))._id
    val BillyC = actors.save(Actor(_key = "BillyC", name = "Billy Crystal", born = 1948))._id
    val CarrieF = actors.save(Actor(_key = "CarrieF", name = "Carrie Fisher", born = 1956))._id
    val BrunoK = actors.save(Actor(_key = "BrunoK", name = "Bruno Kirby", born = 1949))._id
    actsIn.save(ActsIn(_from = BillyC, _to = WhenHarryMetSally, roles = listOf("Harry Burns"), year = 1998))
    actsIn.save(ActsIn(_from = MegR, _to = WhenHarryMetSally, roles = listOf("Sally Albright"), year = 1998))
    actsIn.save(ActsIn(_from = CarrieF, _to = WhenHarryMetSally, roles = listOf("Marie"), year = 1998))
    actsIn.save(ActsIn(_from = BrunoK, _to = WhenHarryMetSally, roles = listOf("Jess"), year = 1998))

    exitProcess(0)
}
