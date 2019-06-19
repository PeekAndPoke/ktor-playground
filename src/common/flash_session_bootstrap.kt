@file:Suppress("unused")

package de.peekandpoke.common

const val SUCCESS = "success"
const val INFO = "info"
const val WARNING = "warning"
const val DANGER = "danger"
const val PRIMARY = "primary"
const val SECONDARY = "secondary"
const val DARK = "dark"
const val LIGHT = "light"

fun FlashSession.success(message: String) = add(message, SUCCESS)

fun FlashSession.info(message: String) = add(message, INFO)

fun FlashSession.warning(message: String) = add(message, WARNING)

fun FlashSession.danger(message: String) = add(message, DANGER)

fun FlashSession.primary(message: String) = add(message, PRIMARY)

fun FlashSession.secondary(message: String) = add(message, SECONDARY)

fun FlashSession.dark(message: String) = add(message, DARK)

fun FlashSession.light(message: String) = add(message, LIGHT)

