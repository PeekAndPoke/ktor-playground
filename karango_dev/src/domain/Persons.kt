package de.peekandpoke.karango_dev.domain

import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.type

val Persons = PersonsCollection()

class PersonsCollection : EntityCollection<Person>("persons", type())
