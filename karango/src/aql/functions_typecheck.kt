@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

/**
 * Checks whether a value is a null value
 *
 * See https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#type-check-functions
 */
@KarangoFuncMarker
fun <T> IS_NULL(expr: Expression<T>) = AqlFunc.IS_NULL.boolCall(expr)

/**
 * Checks whether a value is a boolean value
 *
 * See https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#type-check-functions
 */
@KarangoFuncMarker
fun <T> IS_BOOL(expr: Expression<T>) = AqlFunc.IS_BOOL.boolCall(expr)

/**
 * Checks whether a value is a number value
 *
 * See https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#type-check-functions
 */
@KarangoFuncMarker
fun <T> IS_NUMBER(expr: Expression<T>) = AqlFunc.IS_NUMBER.boolCall(expr)

/**
 * Checks whether a value is a string value
 *
 * See https://docs.arangodb.com/current/AQL/Functions/TypeCast.html#type-check-functions
 */
@KarangoFuncMarker
fun <T> IS_STRING(expr: Expression<T>) = AqlFunc.IS_STRING.boolCall(expr)
