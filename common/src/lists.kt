package de.peekandpoke.ultra.common

fun <T> MutableList<T>.push(vararg element: T) : MutableList<T> = apply { addAll(element) }

fun <T> MutableList<T>.pop() : T = removeAt(size - 1)

fun <T> MutableList<T>.unshift(vararg element: T) : MutableList<T> = apply { addAll(0, element.toList()) }

fun <T> MutableList<T>.shift() : T = removeAt(0)
