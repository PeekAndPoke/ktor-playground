@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql


/**
 * https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#tobool
 */
fun TO_BOOL(expr: Expression<*>) = AqlFunc.TO_BOOL.boolCall(expr)

/**
 * https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#tobool
 */
val <T> Expression<T>.TO_BOOL get() = TO_BOOL(this)

/**
 * https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#tostring
 */
fun TO_NUMBER(expr: Expression<*>) = AqlFunc.TO_NUMBER.numberCall(expr)

/**
 * https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#tostring
 */
val <T> Expression<T>.TO_NUMBER get() = TO_NUMBER(this)

/**
 * https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#tostring
 */
fun TO_STRING(expr: Expression<*>) = AqlFunc.TO_STRING.stringCall(expr)

/**
 * https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#tostring
 */
val <T> Expression<T>.TO_STRING get() = TO_STRING(this)

// TODO: figure out how "TO_ARRAY" really works

///**
// * https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#tostring
// */
//fun TO_ARRAY(expr: Expression<*>) = FuncCall.string(AqlFunc.TO_ARRAY, expr)
//
///**
// * https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#tostring
// */
//val <T> Expression<T>.TO_ARRAY get() = TO_ARRAY(this)

// TODO: TO_LIST is an alias of TO_ARRAY

