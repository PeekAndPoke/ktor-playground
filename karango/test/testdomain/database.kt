package de.peekandpoke.karango.testdomain

import de.peekandpoke.karango.Db

val database = Db.default(user = "root", pass = "", host = "127.0.0.1", port = 8529, database = "_system") {
    addEntityCollection { db -> TestPersonsCollection(db) }
}

val Db.testPersons get() = database.getEntityCollection<TestPersonsCollection>()
