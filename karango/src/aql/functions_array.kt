@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

/**
 * Return the population variance of the values in array.
 *
 * Alias for VARIANCE_POPULATION
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#variance
 */
@KarangoFuncMarker
inline fun <reified T> APPEND(anyArray: Expression<List<T>>, values: Expression<List<T>>) = 
    AqlFunc.APPEND.arrayCall(typeRef<List<T>>(), anyArray, values)


/**
 * Return the population variance of the values in array.
 *
 * Alias for VARIANCE_POPULATION
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Numeric.html#variance
 */
@KarangoFuncMarker
inline fun <reified T> APPEND(anyArray: Expression<List<T>>, values: Expression<List<T>>, unique: Expression<Boolean>) =
    AqlFunc.APPEND.arrayCall(typeRef<List<T>>(), anyArray, values, unique)

