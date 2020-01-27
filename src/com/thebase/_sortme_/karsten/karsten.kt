package com.thebase._sortme_.karsten

//  STRINGS  ////////////////////////////////////////////////

fun String.camelCaseSplit() = "(?<=[a-z])(?=[A-Z])".toRegex().split(this)

fun String.camelCaseDivide(divider: String = " ") = camelCaseSplit().joinToString(divider)

//  LISTS  ////////////////////////////////////////////////

fun <E> List<E>.replace(old: E, new: E): List<E> = when (val idx = indexOf(old)) {
    -1 -> this
    else -> replaceAt(idx, new)
}

fun <E> List<E>.replaceAt(idx: Int, element: E) = toMutableList().apply { set(idx, element) }.toList()

fun <E> List<E>.remove(item: E) = when (val idx = indexOf(item)) {
    -1 -> this
    else -> removeAt(idx)
}

fun <E> List<E>.removeAt(idx: Int) = toMutableList().apply { removeAt(idx) }.toList()

fun <E> List<E>.addAt(idx: Int, it: E) = toMutableList().apply { add(idx, it) }.toList()

fun <E> List<E>.swapAt(idx1: Int, idx2: Int): List<E> {

    if (idx1 < 0 || idx1 >= size || idx2 < 0 || idx2 >= size) {
        return this
    }

    val mutable = toMutableList()

    val tmp = mutable[idx1]
    mutable[idx1] = mutable[idx2]
    mutable[idx2] = tmp

    return mutable.toList()
}
