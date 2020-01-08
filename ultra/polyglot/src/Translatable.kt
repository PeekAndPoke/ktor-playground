package de.peekandpoke.ultra.polyglot

data class Translatable(
    val key: String,
    val replacements: List<Pair<String, String?>>,
    val untranslated: Boolean = false
) {
    companion object {
        fun untranslated(text: String) = Translatable(text, emptyList(), true)
    }
}
