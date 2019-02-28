@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

/**
 * Return the absolute part of value.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#abs
 */
fun ABS(value: Expression<Number>) = AqlFunc.ABS.numberCall(value)

/**
 * Return the arccosine of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#acos
 */
fun ACOS(value: Expression<Number>) = AqlFunc.ACOS.nullableNumberCall(value)

/**
 * Return the arcsine of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#asin
 */
fun ASIN(value: Expression<Number>) = AqlFunc.ASIN.nullableNumberCall(value)

/**
 * Return the arctangent of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#atan
 */
fun ATAN(value: Expression<Number>) = AqlFunc.ATAN.numberCall(value)

/**
 * Return the arctangent of the quotient of y and x.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#atan2
 */
fun ATAN2(x: Expression<Number>, y: Expression<Number>) = AqlFunc.ATAN2.numberCall(x, y)
