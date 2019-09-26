package de.peekandpoke.karango_dev.domain

import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.vault.type

val Persons = EntityCollection<Person>("persons", type())

class PersonsRepository(driver: KarangoDriver) : EntityRepository<Person>(driver, Persons)
