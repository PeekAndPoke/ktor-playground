package io.ultra.ktor_tools.semanticui

import kotlinx.html.*

@SemanticUiDslMarker val FlowContent.ui get() = SemanticUi(this, mutableSetOf("ui"))

@Suppress("FunctionName", "PropertyName")
class SemanticUi(private val parent: FlowContent, private val cssClasses: MutableSet<String>) {

    // switches

    @SemanticUiTagMarker infix fun H1(flow: FlowContent.() -> Unit) = renderH1(flow)

    @SemanticUiTagMarker infix fun H2(flow: FlowContent.() -> Unit) = renderH2(flow)

    @SemanticUiTagMarker infix fun H3(flow: FlowContent.() -> Unit) = renderH3(flow)

    @SemanticUiTagMarker infix fun H4(flow: FlowContent.() -> Unit) = renderH4(flow)

    @SemanticUiTagMarker infix fun H5(flow: FlowContent.() -> Unit) = renderH5(flow)

    @SemanticUiTagMarker infix fun H6(flow: FlowContent.() -> Unit) = renderH6(flow)

    @SemanticUiTagMarker infix fun A(flow: FlowContent.() -> Unit) = renderA(flow)

    @SemanticUiTagMarker infix fun Button(flow: FlowContent.() -> Unit) = renderButton(flow)

    @SemanticUiTagMarker infix fun Div(flow: FlowContent.() -> Unit) = renderDiv(flow)

    // misc

    @SemanticUiCssMarker val button get() = this + "button"
    @SemanticUiCssMarker val buttons get() = this + "buttons"
    @SemanticUiCssMarker val header get() = this + "header"
    @SemanticUiCssMarker val overlay get() = this + "overlay"
    @SemanticUiCssMarker val fixed get() = this + "fixed"
    @SemanticUiCssMarker val shrink get() = this + "shrink"
    @SemanticUiCssMarker val basic get() = this + "basic"
    @SemanticUiCssMarker val scale get() = this + "scale"
    @SemanticUiCssMarker val down get() = this + "down"
    @SemanticUiCssMarker val dividing get() = this + "dividing"
    @SemanticUiCssMarker val pointing get() = this + "pointing"
    @SemanticUiCssMarker val icon get() = this + "icon"
    @SemanticUiCssMarker val active get() = this + "active"
    @SemanticUiCssMarker val disabled get() = this + "disabled"
    @SemanticUiCssMarker val loading get() = this + "loading"
    @SemanticUiCssMarker val floated get() = this + "floated"
    @SemanticUiCssMarker val compact get() = this + "compact"
    @SemanticUiCssMarker val toggle get() = this + "toggle"
    @SemanticUiCssMarker val fluid get() = this + "fluid"
    @SemanticUiCssMarker val circular get() = this + "circular"

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

    @SemanticUiCssMarker val row get() = this + "row"
    @SemanticUiCssMarker val column get() = this + "column"
    @SemanticUiCssMarker val grid get() = this + "grid"
    @SemanticUiCssMarker val wide get() = this + "wide"
    @SemanticUiCssMarker val stackable get() = this + "stackable"
    @SemanticUiCssMarker val stretched get() = this + "stretched"

    // layout

    @SemanticUiCssMarker val aligned get() = this + "aligned"
    @SemanticUiCssMarker val centered get() = this + "centered"
    @SemanticUiCssMarker val divided get() = this + "divided"
    @SemanticUiCssMarker val celled get() = this + "celled"
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

    // size

    @SemanticUiCssMarker val mini get() = this + "mini"
    @SemanticUiCssMarker val tiny get() = this + "tiny"
    @SemanticUiCssMarker val small get() = this + "small"
    @SemanticUiCssMarker val medium get() = this + "medium"
    @SemanticUiCssMarker val large get() = this + "large"
    @SemanticUiCssMarker val big get() = this + "big"
    @SemanticUiCssMarker val huge get() = this + "huge"
    @SemanticUiCssMarker val massive get() = this + "massive"

    // emphasis

    @SemanticUiCssMarker val primary get() = this + "primary"
    @SemanticUiCssMarker val secondary get() = this + "secondary"
    @SemanticUiCssMarker val positive get() = this + "positive"
    @SemanticUiCssMarker val negative get() = this + "negative"

    // animations

    @SemanticUiCssMarker val animated get() = this + "animated"
    @SemanticUiCssMarker val fade get() = this + "fade"

    // content

    @SemanticUiCssMarker val content get() = this + "content"
    @SemanticUiCssMarker val visible get() = this + "visible"
    @SemanticUiCssMarker val hidden get() = this + "hidden"

    // labels

    @SemanticUiCssMarker val label get() = this + "label"
    @SemanticUiCssMarker val labeled get() = this + "labeled"

    // modules

    @SemanticUiCssMarker val menu get() = this + "menu"
    @SemanticUiCssMarker val sidebar get() = this + "sidebar"
    @SemanticUiCssMarker val item get() = this + "item"

    // brands

    @SemanticUiCssMarker val facebook get() = this + "facebook"
    @SemanticUiCssMarker val twitter get() = this + "twitter"
    @SemanticUiCssMarker val google_plus get() = this + "google plus"
    @SemanticUiCssMarker val linkedin get() = this + "linkedin"
    @SemanticUiCssMarker val instagram get() = this + "instagram"
    @SemanticUiCssMarker val youtube get() = this + "youtube"

    @SemanticUiCssMarker
    operator fun invoke(flow: FlowContent.() -> Unit) = renderDiv(flow)

    private operator fun plus(cls: String) = apply { cssClasses.add(cls) }

    private fun renderH1(flow: H1.() -> Unit) = parent.h1 { classes = cssClasses; apply(flow) }

    private fun renderH2(flow: H2.() -> Unit) = parent.h2 { classes = cssClasses; apply(flow) }

    private fun renderH3(flow: H3.() -> Unit) = parent.h3 { classes = cssClasses; apply(flow) }

    private fun renderH4(flow: H4.() -> Unit) = parent.h4 { classes = cssClasses; apply(flow) }

    private fun renderH5(flow: H5.() -> Unit) = parent.h5 { classes = cssClasses; apply(flow) }

    private fun renderH6(flow: H6.() -> Unit) = parent.h6 { classes = cssClasses; apply(flow) }

    private fun renderA(flow: A.() -> Unit) = parent.a { classes = cssClasses; apply(flow) }

    private fun renderButton(flow: BUTTON.() -> Unit) = parent.button { classes = cssClasses; apply(flow) }

    private fun renderDiv(flow: DIV.() -> Unit) = parent.div { classes = cssClasses; apply(flow) }
}
