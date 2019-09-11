package de.peekandpoke.karango_dev.domain

import de.peekandpoke.karango.Db
import de.peekandpoke.karango.DbEntityCollection
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.type

val Persons = EntityCollection<Person>("persons", type())

class PersonsCollection(db: Db) : DbEntityCollection<Person>(db, Persons)
