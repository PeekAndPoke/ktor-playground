package de.peekandpoke.jointhebase.fixtures

interface FixtureLoader {
    fun load()
}

class FixtureInstaller(private val loaders: List<FixtureLoader>) {

    fun install(): List<FixtureLoader> {
        return loaders.map { it.apply { load() } }
    }
}
