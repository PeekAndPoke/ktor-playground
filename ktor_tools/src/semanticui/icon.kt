package io.ultra.ktor_tools.semanticui

import kotlinx.html.FlowContent
import kotlinx.html.i

@SemanticUiDslMarker val FlowContent.icon get() = SemanticIcon(this)

@Suppress("PropertyName")
class SemanticIcon(private val parent: FlowContent, private val cssClasses: MutableSet<String> = mutableSetOf()) {

    // Styling ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker val outline get() = plus("outline")

    // Accessibility ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker val american_sign_language_interpreting get() = plus("american sign language interpreting")

    @SemanticUiCssMarker val assistive_listening_systems get() = plus("assistive listening systems")

    @SemanticUiCssMarker val audio_description get() = plus("audio description")

    @SemanticUiCssMarker val blind get() = plus("blind")

    @SemanticUiCssMarker val braille get() = plus("braille")

    @SemanticUiCssMarker val closed_captioning get() = plus("closed captioning")

    @SemanticUiCssMarker val deaf get() = plus("deaf")

    @SemanticUiCssMarker val low_vision get() = plus("low vision")

    @SemanticUiCssMarker val phone_volume get() = plus("phone volume")

    @SemanticUiCssMarker val question_circle get() = plus("question circle")

    @SemanticUiCssMarker val sign_language get() = plus("sign language")

    @SemanticUiCssMarker val tty get() = plus("tty")

    @SemanticUiCssMarker val universal_access get() = plus("universal access")

    @SemanticUiCssMarker val wheelchair get() = plus("wheelchair")


    @SemanticUiCssMarker
    operator fun invoke() = parent.i(classes = "${cssClasses.joinToString(" ")} icon")

    private fun plus(cls: String) = apply { cssClasses.add(cls) }
}
