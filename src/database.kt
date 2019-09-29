package de.peekandpoke

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase

// TODO: put this into the container
//       the connection details must be read from the configuration files
val arangoDb: ArangoDB = ArangoDB.Builder()
    .user("root")
    .password("")
    .host("localhost", 8529)
    .build()

val arangoDatabase: ArangoDatabase = arangoDb.db("kotlindev")
