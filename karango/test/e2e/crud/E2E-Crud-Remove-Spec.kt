package de.peekandpoke.karango.e2e.crud

import de.peekandpoke.karango.id
import de.peekandpoke.karango.testdomain.database
import de.peekandpoke.karango.testdomain.name
import de.peekandpoke.karango.testdomain.testPersons
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

@Suppress("ClassName")
class `E2E-Crud-Remove-Spec` : StringSpec({

    "Removing an entity via remove(entity) must work" {

        // get coll and clear all entries
        val coll = database.testPersons.apply {
            removeAll()
        }

        // create some entries
        val jonSaved = coll.save(JonBonJovi)
        val edgarSaved = coll.save(EdgarAllanPoe)
        val heinzSaved = coll.save(HeinzRudolfKunze)

        // remove on entry
        val removeResult = coll.remove(jonSaved)

        // reload all remaining
        val allReloaded = coll.findAll().toList().sortedBy { it.name }
        val (edgarReloaded, heinzReloaded) = allReloaded

        assertSoftly {

            // check the removed entity
            removeResult.count shouldBe 1

            // check reloaded
            allReloaded.size shouldBe 2

            edgarReloaded._id shouldBe edgarSaved.id
            heinzReloaded._id shouldBe heinzSaved.id
        }
    }

    "Removing an entity via remove(String) must work" {

        // get coll and clear all entries
        val coll = database.testPersons.apply {
            removeAll()
        }

        // create some entries
        val jonSaved = coll.save(JonBonJovi)
        val edgarSaved = coll.save(EdgarAllanPoe)
        val heinzSaved = coll.save(HeinzRudolfKunze)

        // remove on entry
        val removeResult = coll.remove(jonSaved._id!!)

        // reload all remaining
        val allReloaded = coll.findAll().toList().sortedBy { it.name }
        val (edgarReloaded, heinzReloaded) = allReloaded

        assertSoftly {

            // check the removed entity
            removeResult.count shouldBe 1

            // check reloaded
            allReloaded.size shouldBe 2

            edgarReloaded._id shouldBe edgarSaved.id
            heinzReloaded._id shouldBe heinzSaved.id
        }
    }

    "Trying to delete a non-existing" {

        // get coll and clear all entries
        val coll = database.testPersons.apply {
            removeAll()
        }

        val removeResult = coll.remove("abc123")

        assertSoftly {
            removeResult.count shouldBe 0
        }
    }
})
