@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

enum class PercentileMethod(val method: String) {
    RANK("rank"),
    INTERPOLATION("interpolation")
}

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
 * Return the cosine of value.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#cos
 */
fun COS(value: Expression<Number>) = AqlFunc.COS.numberCall(value)

/**
 * Return the angle converted from radians to degrees.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#degrees
 */
fun DEGREES(value: Expression<Number>) = AqlFunc.DEGREES.numberCall(value)

/**
 * Return Euler's constant (2.71828...) raised to the power of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#exp
 */
fun EXP(value: Expression<Number>) = AqlFunc.EXP.numberCall(value)

/**
 * Return 2 raised to the power of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#exp2
 */
fun EXP2(value: Expression<Number>) = AqlFunc.EXP2.numberCall(value)

/**
 * Return the integer closest but not greater than value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#floor
 */
fun FLOOR(value: Expression<Number>) = AqlFunc.FLOOR.numberCall(value)

/**
 * Return the natural logarithm of value. The base is Euler's constant (2.71828...).
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#log
 */
fun LOG(value: Expression<Number>) = AqlFunc.LOG.nullableNumberCall(value)

/**
 * Return the base 2 logarithm of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#log
 */
fun LOG2(value: Expression<Number>) = AqlFunc.LOG2.nullableNumberCall(value)

/**
 * Return the base 10 logarithm of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#log
 */
fun LOG10(value: Expression<Number>) = AqlFunc.LOG10.nullableNumberCall(value)

/**
 * Return the greatest element of anyArray. The array is not limited to numbers. Also see type and value order.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#min
 */
fun <T> MAX(numArray: Expression<List<T>>) = AqlFunc.MAX.nullableNumberCall(numArray)

/**
 * Return the median value of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#median
 */
fun MEDIAN(numArray: Expression<List<Number>>) = AqlFunc.MEDIAN.numberCall(numArray)

/**
 * Return the smallest element of anyArray. The array is not limited to numbers. Also see type and value order.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#min
 */
fun <T> MIN(numArray: Expression<List<T>>) = AqlFunc.MIN.nullableNumberCall(numArray)

/**
 * Return the nth percentile of the values in numArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#percentile
 */
fun PERCENTILE(numArray: Expression<List<Number?>>, n: Expression<Number>) = AqlFunc.PERCENTILE.numberCall(numArray, n)

/**
 * Return the nth percentile of the values in numArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#percentile
 */
fun PERCENTILE(numArray: Expression<List<Number?>>, n: Expression<Number>, method: PercentileMethod) = 
    AqlFunc.PERCENTILE.numberCall(numArray, n, method.method.aql)

/**
 * Returns pi.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#pi
 */
fun PI() = AqlFunc.PI.numberCall()

/**
 * Return the base to the exponent exp.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#pow
 */
fun POW(base: Expression<Number>, exp: Expression<Number>) = AqlFunc.POW.numberCall(base, exp)

/**
 * Return the angle converted from degrees to radians.
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#radians
 */
fun RADIANS(deg: Expression<Number>) = AqlFunc.RADIANS.numberCall(deg)

/**
 * Return a pseudo-random number between 0 and 1.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#rand
 */
fun RAND() = AqlFunc.RAND.numberCall()

/**
 * Return an array of numbers in the specified range, optionally with increments other than 1.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#range
 */
fun RANGE(start: Expression<Number>, stop: Expression<Number>) = AqlFunc.RANGE.arrayCall(typeRef<List<Number>>(), start, stop)

/**
 * Return the integer closest to value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#round
 */
fun ROUND(value: Expression<Number>) = AqlFunc.ROUND.numberCall(value)

/**
 * Return an array of numbers in the specified range, optionally with increments other than 1.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#range
 */
fun RANGE(start: Expression<Number>, stop: Expression<Number>, step: Expression<Number>) = 
    AqlFunc.RANGE.nullableArrayCall(typeRef<List<Number>?>(), start, stop, step)

/**
 * Return the sine of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#sin
 */
fun SIN(value: Expression<Number>) = AqlFunc.SIN.numberCall(value)
