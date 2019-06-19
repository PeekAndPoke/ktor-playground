@file:Suppress("FunctionName")

package de.peekandpoke.common

import kotlinx.html.*

// Containers

fun FlowContent.container(block: DIV.() -> Unit) = div(classes = "container") { block() }

fun FlowContent.container_fluid(block: DIV.() -> Unit) = div(classes = "container-fluid") { block() }

// Grid-System

fun FlowContent.row(block: DIV.() -> Unit = {}) = div(classes = "row") { block() }

fun FlowContent.col_md_3(block: DIV.() -> Unit = {}) = div(classes = "col-md-3") { block() }

// forms

fun FlowContent.form_group(block: DIV.() -> Unit = {}) = div(classes = "form-group") { block() }

fun FlowContent.submit(block: BUTTON.() -> Unit = {}) = button(classes = "form-control") { block() }

