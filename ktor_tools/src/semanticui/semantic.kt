package io.ultra.ktor_tools.semanticui

import kotlinx.html.FlowContent
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div

@DslMarker
annotation class SemanticUiDslMarker

@DslMarker
annotation class SemanticUiCssMarker

@SemanticUiDslMarker val FlowContent.ui get() = SemanticUiDiv(this, mutableSetOf("ui"))

abstract class SemanticUi(val parent: FlowContent, val cssClasses: MutableSet<String>) {

    // switches

    @SemanticUiCssMarker val button get() : SemanticUiButton {
        cssClasses.add("button")

        return SemanticUiButton(parent, cssClasses)
    }


    @SemanticUiCssMarker val overlay get() = this + "overlay"
    @SemanticUiCssMarker val fixed get() = this + "fixed"
    @SemanticUiCssMarker val shrink get() = this + "shrink"
    @SemanticUiCssMarker val basic get() = this + "basic"
    @SemanticUiCssMarker val scale get() = this + "scale"
    @SemanticUiCssMarker val down get() = this + "down"

    // container

    @SemanticUiCssMarker val container get() = this + "container"
    @SemanticUiCssMarker val segment get() = this + "segment"
    @SemanticUiCssMarker val pusher get() = this + "pusher"

    // numbers

    @SemanticUiCssMarker val one get() = this + "one"
    @SemanticUiCssMarker val two get() = this + "two"
    @SemanticUiCssMarker val three get() = this + "three"
    @SemanticUiCssMarker val four get() = this + "four"
    @SemanticUiCssMarker val five get() = this + "five"
    @SemanticUiCssMarker val six get() = this + "six"
    @SemanticUiCssMarker val seven get() = this + "seven"
    @SemanticUiCssMarker val eight get() = this + "eight"
    @SemanticUiCssMarker val nine get() = this + "nine"
    @SemanticUiCssMarker val ten get() = this + "ten"
    @SemanticUiCssMarker val eleven get() = this + "eleven"
    @SemanticUiCssMarker val twelve get() = this + "twelve"

    // positions

    @SemanticUiCssMarker val left get() = this + "left"
    @SemanticUiCssMarker val right get() = this + "right"

    // directions

    @SemanticUiCssMarker val horizontal get() = this + "horizontal"
    @SemanticUiCssMarker val vertical get() = this + "vertical"

    // grid

    @SemanticUiCssMarker val column get() = this + "column"
    @SemanticUiCssMarker val grid get() = this + "grid"
    @SemanticUiCssMarker val wide get() = this + "wide"
    @SemanticUiCssMarker val stackable get() = this + "stackable"
    @SemanticUiCssMarker val stretched get() = this + "stretched"

    // layout

    @SemanticUiCssMarker val aligned get() = this + "aligned"
    @SemanticUiCssMarker val centered get() = this + "centered"
    @SemanticUiCssMarker val divided get() = this + "divided"
    @SemanticUiCssMarker val middle get() = this + "middle"
    @SemanticUiCssMarker val padded get() = this + "padded"

    // colors

    @SemanticUiCssMarker val color get() = this + "color"
    @SemanticUiCssMarker val inverted get() = this + "inverted"

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

    // emphasis

    @SemanticUiCssMarker val primary get() = this + "primary"
    @SemanticUiCssMarker val secondary get() = this + "secondary"

    // animations

    @SemanticUiCssMarker val animated get() = this + "animated"
    @SemanticUiCssMarker val fade get() = this + "fade"

    // content

    @SemanticUiCssMarker val content get() = this + "content"
    @SemanticUiCssMarker val visible get() = this + "visible"
    @SemanticUiCssMarker val hidden get() = this + "hidden"

    // labels

    @SemanticUiCssMarker val labeled get() = this + "labeled"

    // modules

    @SemanticUiCssMarker val menu get() = this + "menu"
    @SemanticUiCssMarker val sidebar get() = this + "sidebar"
    @SemanticUiCssMarker val item get() = this + "item"


    @SemanticUiCssMarker
    abstract operator fun invoke(inner: FlowContent.() -> Unit)

    private operator fun plus(cls: String) = apply {
        cssClasses.add(cls)
    }
}

class SemanticUiDiv(parent: FlowContent, cssClasses: MutableSet<String>) : SemanticUi(parent, cssClasses) {

    @SemanticUiCssMarker
    override operator fun invoke(inner: FlowContent.() -> Unit) {

        parent.div {
            classes = cssClasses
            apply(inner)
        }
    }
}

class SemanticUiButton(parent: FlowContent, cssClasses: MutableSet<String>) : SemanticUi(parent, cssClasses) {

    @SemanticUiCssMarker
    override operator fun invoke(inner: FlowContent.() -> Unit) {

        parent.button {
            classes = cssClasses
            apply(inner)
        }
    }
}

