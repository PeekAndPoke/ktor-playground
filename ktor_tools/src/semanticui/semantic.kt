package io.ultra.ktor_tools.semanticui

import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div

@DslMarker
annotation class SemanticUiDslMarker

@DslMarker
annotation class SemanticUiCssMarker


@SemanticUiDslMarker
val FlowContent.ui
    get() = SemanticUi(this)

class SemanticUi(private val parent: FlowContent, private val cssClasses: MutableSet<String> = mutableSetOf("ui")) {

    // container

    @SemanticUiCssMarker val container get() = this + "container"

    // numbers

    @SemanticUiCssMarker val one get() = this + "one"
    @SemanticUiCssMarker val two get() = this + "two"
    @SemanticUiCssMarker val three get() = this + "three"
    @SemanticUiCssMarker val four get() = this + "four"
    @SemanticUiCssMarker val five get() = this + "five"
    @SemanticUiCssMarker val six get() = this + "six"

    // grid

    @SemanticUiCssMarker val column get() = this + "column"
    @SemanticUiCssMarker val grid get() = this + "grid"
    @SemanticUiCssMarker val stackable get() = this + "stackable"

    // layout

    @SemanticUiCssMarker val aligned get() = this + "aligned"
    @SemanticUiCssMarker val centered get() = this + "centered"
    @SemanticUiCssMarker val divided get() = this + "divided"
    @SemanticUiCssMarker val middle get() = this + "middle"
    @SemanticUiCssMarker val padded get() = this + "padded"

    // colors

    @SemanticUiCssMarker val color get() = this + "color"
    @SemanticUiCssMarker val red get() = this + "red"
    @SemanticUiCssMarker val orange get() = this + "orange"
    @SemanticUiCssMarker val yellow get() = this + "yellow"
    @SemanticUiCssMarker val olive get() = this + "olive"
    @SemanticUiCssMarker val green get() = this + "green"
    @SemanticUiCssMarker val teal get() = this + "teal"
    @SemanticUiCssMarker val blue get() = this + "blue"
    @SemanticUiCssMarker val violet get() = this + "violet"
    @SemanticUiCssMarker val purple get() = this + "purple"
    @SemanticUiCssMarker val pink get() = this + "pink"
    @SemanticUiCssMarker val brown get() = this + "brown"
    @SemanticUiCssMarker val grey get() = this + "grey"
    @SemanticUiCssMarker val black get() = this + "black"

    @SemanticUiCssMarker
    operator fun invoke(inner: FlowContent.() -> Unit) {

        parent.div {
            classes = cssClasses
            apply(inner)
        }
    }

    private operator fun plus(cls: String) = apply {
        cssClasses.add(cls)
    }
}

