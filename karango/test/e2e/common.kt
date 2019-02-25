package de.peekandpoke.karango.e2e

import de.peekandpoke.karango.Db

val db = Db.default(user = "root", pass = "root", host = "localhost", port = 8529, database = "kotlindev")

data class X(val name: String, val age: Int)

