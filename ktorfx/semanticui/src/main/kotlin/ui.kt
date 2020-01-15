package de.peekandpoke.ktorfx.semanticui

import kotlinx.html.*

@SemanticUiDslMarker val FlowContent.ui get() = SemanticUi(this, mutableListOf("ui"))

@SemanticUiDslMarker val FlowContent.noui get() = SemanticUi(this, mutableListOf(""))

@Suppress("EnumEntryName")
enum class SemanticColor {
    none,
    red,
    orange,
    yellow,
    olive,
    green,
    teal,
    blue,
    violet,
    purple,
    pink,
    brown,
    grey,
    black,
}

@Suppress("FunctionName", "PropertyName")
class SemanticUi(private val parent: FlowContent, private val cssClasses: MutableList<String>) {

    // switches

    @SemanticUiTagMarker infix fun H1(flow: H1.() -> Unit) = renderH1(flow)

    @SemanticUiTagMarker infix fun H2(flow: H2.() -> Unit) = renderH2(flow)

    @SemanticUiTagMarker infix fun H3(flow: H3.() -> Unit) = renderH3(flow)

    @SemanticUiTagMarker infix fun H4(flow: H4.() -> Unit) = renderH4(flow)

    @SemanticUiTagMarker infix fun H5(flow: H5.() -> Unit) = renderH5(flow)

    @SemanticUiTagMarker infix fun H6(flow: H6.() -> Unit) = renderH6(flow)

    @SemanticUiTagMarker infix fun A(flow: A.() -> Unit) = renderA(flow)

    @SemanticUiTagMarker infix fun Button(flow: BUTTON.() -> Unit) = renderButton(flow)

    @SemanticUiTagMarker infix fun Div(flow: DIV.() -> Unit) = renderDiv(flow)

    @SemanticUiTagMarker infix fun Form(flow: FORM.() -> Unit) = renderForm(flow)

    @SemanticUiTagMarker infix fun Label(flow: LABEL.() -> Unit) = renderLabel(flow)

    @SemanticUiTagMarker infix fun P(flow: P.() -> Unit) = renderP(flow)

    @SemanticUiTagMarker infix fun Submit(flow: BUTTON.() -> Unit) = renderSubmitButton(flow)

    @SemanticUiTagMarker infix fun Table(flow: TABLE.() -> Unit) = renderTable(flow)

    // dynamic class

    @SemanticUiCssMarker fun with(vararg cls: String) = this + cls

    @SemanticUiCssMarker fun with(vararg cls: String, flow: FlowContent.() -> Unit) = (this + cls).renderDiv(flow)

    // conditional classes

    @SemanticUiConditionalMarker fun given(condition: Boolean, action: SemanticUi.() -> SemanticUi) = when (condition) {
        false -> this
        else -> this.action()
    }

    @SemanticUiConditionalMarker val then get() = this

    // misc

    @SemanticUiCssMarker val active get() = this + "active"
    @SemanticUiCssMarker val basic get() = this + "basic"
    @SemanticUiCssMarker val button get() = this + "button"
    @SemanticUiCssMarker val buttons get() = this + "buttons"
    @SemanticUiCssMarker val circular get() = this + "circular"
    @SemanticUiCssMarker val compact get() = this + "compact"
    @SemanticUiCssMarker val disabled get() = this + "disabled"
    @SemanticUiCssMarker val divider get() = this + "divider"
    @SemanticUiCssMarker val dividing get() = this + "dividing"
    @SemanticUiCssMarker val down get() = this + "down"
    @SemanticUiCssMarker val floated get() = this + "floated"
    @SemanticUiCssMarker val fluid get() = this + "fluid"
    @SemanticUiCssMarker val fixed get() = this + "fixed"
    @SemanticUiCssMarker val header get() = this + "header"
    @SemanticUiCssMarker val icon get() = this + "icon"
    @SemanticUiCssMarker val list get() = this + "list"
    @SemanticUiCssMarker val loading get() = this + "loading"
    @SemanticUiCssMarker val message get() = this + "message"
    @SemanticUiCssMarker val overlay get() = this + "overlay"
    @SemanticUiCssMarker val pointing get() = this + "pointing"
    @SemanticUiCssMarker val scale get() = this + "scale"
    @SemanticUiCssMarker val shrink get() = this + "shrink"
    @SemanticUiCssMarker val toggle get() = this + "toggle"
    @SemanticUiCssMarker val styled get() = this + "styled"
    @SemanticUiCssMarker val accordion get() = this + "accordion"
    @SemanticUiCssMarker val title get() = this + "title"
    @SemanticUiCssMarker val transition get() = this + "transition"
    @SemanticUiCssMarker val relaxed get() = this + "relaxed"
    @SemanticUiCssMarker val attached get() = this + "attached"

    // display sizes

    @SemanticUiCssMarker val computer get() = this + "computer"
    @SemanticUiCssMarker val tablet get() = this + "tablet"

    // form

    @SemanticUiCssMarker val form get() = this + "form"
    @SemanticUiCssMarker val field get() = this + "field"
    @SemanticUiCssMarker val fields get() = this + "fields"
    @SemanticUiCssMarker val error get() = this + "error"

    // table

    @SemanticUiCssMarker val table get() = this + "table"

    // container

    @SemanticUiCssMarker val container get() = this + "container"
    @SemanticUiCssMarker val segment get() = this + "segment"
    @SemanticUiCssMarker val segments get() = this + "segments"
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
    @SemanticUiCssMarker val thirteen get() = this + "thirteen"
    @SemanticUiCssMarker val fourteen get() = this + "fourteen"
    @SemanticUiCssMarker val fifteen get() = this + "fifteen"
    @SemanticUiCssMarker val sixteen get() = this + "sixteen"

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
    @SemanticUiCssMarker val center get() = this + "center"
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
    @SemanticUiCssMarker val warning get() = this + "warning"

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
    @SemanticUiCssMarker val items get() = this + "items"

    // brands

    @SemanticUiCssMarker val facebook get() = this + "facebook"
    @SemanticUiCssMarker val twitter get() = this + "twitter"
    @SemanticUiCssMarker val google_plus get() = this + "google plus"
    @SemanticUiCssMarker val linkedin get() = this + "linkedin"
    @SemanticUiCssMarker val instagram get() = this + "instagram"
    @SemanticUiCssMarker val youtube get() = this + "youtube"

    @SemanticUiCssMarker
    operator fun invoke(flow: DIV.() -> Unit) = renderDiv(flow)

    private operator fun plus(cls: String) = apply { cssClasses.add(cls) }

    private operator fun plus(classes: Array<out String>) = apply { cssClasses.addAll(classes) }

    private fun renderH1(flow: H1.() -> Unit) = parent.h1(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderH2(flow: H2.() -> Unit) = parent.h2(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderH3(flow: H3.() -> Unit) = parent.h3(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderH4(flow: H4.() -> Unit) = parent.h4(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderH5(flow: H5.() -> Unit) = parent.h5(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderH6(flow: H6.() -> Unit) = parent.h6(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderA(flow: A.() -> Unit) = parent.a(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderButton(flow: BUTTON.() -> Unit) = parent.button(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderSubmitButton(flow: BUTTON.() -> Unit) =
        parent.button(type = ButtonType.submit, classes = cssClasses.joinToString(" "), block = flow)

    private fun renderDiv(flow: DIV.() -> Unit) = parent.div(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderForm(flow: FORM.() -> Unit) = parent.form(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderLabel(flow: LABEL.() -> Unit) = parent.label(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderP(flow: P.() -> Unit) = parent.p(classes = cssClasses.joinToString(" "), block = flow)

    private fun renderTable(flow: TABLE.() -> Unit) = parent.table(classes = cssClasses.joinToString(" "), block = flow)
}
