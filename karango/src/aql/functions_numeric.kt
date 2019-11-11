@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.ultra.vault.kListType

enum class PercentileMethod(val method: String) {
    @KarangoInputMarker
    RANK("rank"),
    @KarangoInputMarker
    INTERPOLATION("interpolation")
}

/**
 * Return the absolute part of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#abs
 */
@KarangoFuncMarker
fun <T : Number> ABS(value: Expression<T>) = AqlFunc.ABS.numberCall(value)

/**
 * Return the arccosine of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#acos
 */
@KarangoFuncMarker
fun <T : Number> ACOS(value: Expression<T>) = AqlFunc.ACOS.nullableNumberCall(value)

/**
 * Return the arcsine of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#asin
 */
@KarangoFuncMarker
fun <T : Number> ASIN(value: Expression<T>) = AqlFunc.ASIN.nullableNumberCall(value)

/**
 * Return the arctangent of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#atan
 */
@KarangoFuncMarker
fun <T : Number> ATAN(value: Expression<T>) = AqlFunc.ATAN.numberCall(value)

/**
 * Return the arctangent of the quotient of y and x.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#atan2
 */
@KarangoFuncMarker
fun <T1 : Number, T2 : Number> ATAN2(x: Expression<T1>, y: Expression<T2>) = AqlFunc.ATAN2.numberCall(x, y)

/**
 * Return the average (arithmetic mean) of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#average
 */
@KarangoFuncMarker
fun <T : Number> AVERAGE(numArray: Expression<List<T>>) = AqlFunc.AVERAGE.numberCall(numArray)

/**
 * Return the average (arithmetic mean) of the values in array.
 *
 * Alias of AVERAGE
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#avg
 */
@KarangoFuncMarker
fun <T : Number> AVG(numArray: Expression<List<T>>) = AqlFunc.AVG.numberCall(numArray)

/**
 * Return the integer closest but not less than value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#ceil
 */
@KarangoFuncMarker
fun <T : Number> CEIL(value: Expression<T>) = AqlFunc.CEIL.numberCall(value)

/**
 * Return the cosine of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#cos
 */
@KarangoFuncMarker
fun <T : Number> COS(value: Expression<T>) = AqlFunc.COS.numberCall(value)

/**
 * Return the angle converted from radians to degrees.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#degrees
 */
@KarangoFuncMarker
fun <T : Number> DEGREES(value: Expression<T>) = AqlFunc.DEGREES.numberCall(value)

/**
 * Return Euler's constant (2.71828...) raised to the power of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#exp
 */
@KarangoFuncMarker
fun <T : Number> EXP(value: Expression<T>) = AqlFunc.EXP.numberCall(value)

/**
 * Return 2 raised to the power of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#exp2
 */
@KarangoFuncMarker
fun <T : Number> EXP2(value: Expression<T>) = AqlFunc.EXP2.numberCall(value)

/**
 * Return the integer closest but not greater than value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#floor
 */
@KarangoFuncMarker
fun <T : Number> FLOOR(value: Expression<T>) = AqlFunc.FLOOR.numberCall(value)

/**
 * Return the natural logarithm of value. The base is Euler's constant (2.71828...).
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#log
 */
@KarangoFuncMarker
fun <T : Number> LOG(value: Expression<T>) = AqlFunc.LOG.nullableNumberCall(value)

/**
 * Return the base 2 logarithm of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#log
 */
@KarangoFuncMarker
fun <T : Number> LOG2(value: Expression<T>) = AqlFunc.LOG2.nullableNumberCall(value)

/**
 * Return the base 10 logarithm of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#log
 */
@KarangoFuncMarker
fun <T : Number> LOG10(value: Expression<T>) = AqlFunc.LOG10.nullableNumberCall(value)

/**
 * Return the greatest element of anyArray. The array is not limited to numbers. Also see type and value order.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#min
 */
@KarangoFuncMarker
fun <T : Number> MAX(numArray: Expression<List<T>>) = AqlFunc.MAX.nullableNumberCall(numArray)

/**
 * Return the median value of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#median
 */
@KarangoFuncMarker
fun <T : Number> MEDIAN(numArray: Expression<List<T>>) = AqlFunc.MEDIAN.numberCall(numArray)

/**
 * Return the smallest element of anyArray. The array is not limited to numbers. Also see type and value order.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#min
 */
@KarangoFuncMarker
fun <T : Number> MIN(numArray: Expression<List<T>>) = AqlFunc.MIN.nullableNumberCall(numArray)

/**
 * Return the nth percentile of the values in numArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#percentile
 */
@KarangoFuncMarker
fun <T1 : Number, T2 : Number> PERCENTILE(numArray: Expression<List<T1>>, n: Expression<T2>) =
    AqlFunc.PERCENTILE.numberCall(numArray, n)

/**
 * Return the nth percentile of the values in numArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#percentile
 */
@KarangoFuncMarker
fun <T1 : Number?, T2 : Number> PERCENTILE(numArray: Expression<List<T1>>, n: Expression<T2>, method: PercentileMethod) =
    AqlFunc.PERCENTILE.numberCall(numArray, n, method.method.aql)

/**
 * Returns pi.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#pi
 */
@KarangoFuncMarker
fun PI() = AqlFunc.PI.numberCall()

/**
 * Return the base to the exponent exp.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#pow
 */
@KarangoFuncMarker
fun <T1 : Number, T2 : Number> POW(base: Expression<T1>, exp: Expression<T2>) = AqlFunc.POW.nullableNumberCall(base, exp)

/**
 * Return the angle converted from degrees to radians.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#radians
 */
@KarangoFuncMarker
fun <T : Number> RADIANS(deg: Expression<T>) = AqlFunc.RADIANS.numberCall(deg)

/**
 * Return a pseudo-random number between 0 and 1.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#rand
 */
@KarangoFuncMarker
fun RAND() = AqlFunc.RAND.numberCall()

/**
 * Return an array of numbers in the specified range, optionally with increments other than 1.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#range
 */
@KarangoFuncMarker
fun <T1 : Number, T2 : Number> RANGE(start: Expression<T1>, stop: Expression<T2>) =
    AqlFunc.RANGE.arrayCall(kListType<Number>(), start, stop)

/**
 * Return an array of numbers in the specified range, optionally with increments other than 1.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#range
 */
@KarangoFuncMarker
fun <T1 : Number, T2 : Number, T3 : Number> RANGE(start: Expression<T1>, stop: Expression<T2>, step: Expression<T3>) =
    AqlFunc.RANGE.nullableArrayCall(kListType<Number>().nullable, start, stop, step)

/**
 * Return the integer closest to value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#round
 */
@KarangoFuncMarker
fun <T : Number> ROUND(value: Expression<T>) = AqlFunc.ROUND.numberCall(value)

/**
 * Return the sine of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#sin
 */
@KarangoFuncMarker
fun <T : Number> SIN(value: Expression<T>) = AqlFunc.SIN.numberCall(value)

/**
 * Return the square root of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#sqrt
 */
@KarangoFuncMarker
fun <T : Number> SQRT(value: Expression<T>) = AqlFunc.SQRT.nullableNumberCall(value)

/**
 * Return the population standard deviation of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#stddevpopulation
 */
@KarangoFuncMarker
fun <T : Number> STDDEV_POPULATION(value: Expression<List<T>>) = AqlFunc.STDDEV_POPULATION.nullableNumberCall(value)

/**
 * Return the sample standard deviation of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#stddevsample
 */
@KarangoFuncMarker
fun <T : Number> STDDEV_SAMPLE(value: Expression<List<T>>) = AqlFunc.STDDEV_SAMPLE.nullableNumberCall(value)

/**
 * Return the population standard deviation of the values in array.
 *
 * Alias of STDDEV_POPULATION
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#stddev
 */
@KarangoFuncMarker
fun <T : Number> STDDEV(value: Expression<List<T>>) = AqlFunc.STDDEV.nullableNumberCall(value)

/**
 * Return the sum of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#stddevsample
 */
@KarangoFuncMarker
fun <T : Number> SUM(numArray: Expression<List<T>>) = AqlFunc.SUM.numberCall(numArray)

/**
 * Return the tangent of value.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#tan
 */
@KarangoFuncMarker
fun <T : Number> TAN(value: Expression<T>) = AqlFunc.TAN.numberCall(value)

/**
 * Return the population variance of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#variancepopulation
 */
@KarangoFuncMarker
fun <T : Number> VARIANCE_POPULATION(value: Expression<List<T>>) = AqlFunc.VARIANCE_POPULATION.nullableNumberCall(value)

/**
 * Return the sample variance of the values in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#variancesample
 */
@KarangoFuncMarker
fun <T : Number> VARIANCE_SAMPLE(value: Expression<List<T>>) = AqlFunc.VARIANCE_SAMPLE.nullableNumberCall(value)

/**
 * Return the population variance of the values in array.
 *
 * Alias for VARIANCE_POPULATION
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#variance
 */
@KarangoFuncMarker
fun <T : Number> VARIANCE(value: Expression<List<T>>) = AqlFunc.VARIANCE.nullableNumberCall(value)
