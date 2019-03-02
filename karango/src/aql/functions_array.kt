@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

/**
 * Add all elements of an array to another array. All values are added at the end of the array (right side).
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#append
 */
@KarangoFuncMarker
inline fun <reified T> APPEND(anyArray: Expression<List<T>>, values: Expression<List<T>>) = 
    AqlFunc.APPEND.arrayCall(type<List<T>>(), anyArray, values)

/**
 * Add all elements of an array to another array. All values are added at the end of the array (right side).
 *
 * Special function if the user wants to mix types:
 * 
 * Example:
 * 
 * APPEND(type<Any>(), ARRAY(1.aql), ARRAY("a".aql))
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#append
 */
@KarangoFuncMarker
fun <R, T1, T2> APPEND(type: TypeRef<R>, anyArray: Expression<List<T1>>, values: Expression<List<T2>>) =
    AqlFunc.APPEND.arrayCall(type.up(), anyArray, values)


/**
 * Return the population variance of the values in array.
 *
 * Alias for VARIANCE_POPULATION
 *
 * https://docs.arangodb.com/current/AQL/Functions/Array.html#append
 */
@KarangoFuncMarker
inline fun <reified T> APPEND(anyArray: Expression<List<T>>, values: Expression<List<T>>, unique: Expression<Boolean>) =
    AqlFunc.APPEND.arrayCall(type<List<T>>(), anyArray, values, unique)

/**
 * Add all elements of an array to another array. All values are added at the end of the array (right side).
 *
 * Special function if the user wants to mix types:
 *
 * Example:
 *
 * APPEND(type<Any>(), ARRAY(1.aql), ARRAY("a".aql), true)
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#append
 */
@KarangoFuncMarker
fun <R, T1, T2> APPEND(type: TypeRef<R>, anyArray: Expression<List<T1>>, values: Expression<List<T2>>, unique: Expression<Boolean>) =
    AqlFunc.APPEND.arrayCall(type.up(), anyArray, values, unique)

