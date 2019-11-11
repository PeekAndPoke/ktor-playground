package de.peekandpoke.karango_dev.domain

import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.ultra.vault.kType

val Persons = EntityCollection<Person>("persons", kType())

class PersonsRepository(driver: KarangoDriver) : EntityRepository<Person>(driver, Persons)
