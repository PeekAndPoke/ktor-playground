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

/**
 * Return the average (arithmetic mean) of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#average
 */
fun AVERAGE(numArray: Expression<List<Number>>) = AqlFunc.AVERAGE.numberCall(numArray)

/**
 * Return the average (arithmetic mean) of the values in array.
 *
 * Alias of AVERAGE
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#avg
 */
fun AVG(numArray: Expression<List<Number>>) = AqlFunc.AVG.numberCall(numArray)

/**
 * Return the integer closest but not less than value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#ceil
 */
fun CEIL(value: Expression<Number>) = AqlFunc.CEIL.numberCall(value)

/**
 * Return the integer closest but not greater than value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#floor
 */
fun FLOOR(value: Expression<Number>) = AqlFunc.FLOOR.numberCall(value)

/**
 * Return the median value of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#median
 */
fun MEDIAN(numArray: Expression<List<Number>>) = AqlFunc.MEDIAN.numberCall(numArray)
