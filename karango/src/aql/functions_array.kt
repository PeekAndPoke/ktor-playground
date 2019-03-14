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

/**
 * Return whether search is contained in array.
 *
 * Alias of POSITION
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#contains_array
 *
 * To get the position of the occurrence use CONTAINS_ARRAY_IDX()
 */
@KarangoFuncMarker
fun <T> CONTAINS_ARRAY(anyArray: Expression<List<T>>, search: Expression<T>) = AqlFunc.CONTAINS_ARRAY.boolCall(anyArray, search)

/**
 * Return the position of the match (starting with 0) otherwise -1.
 *
 * Alias of POSITION
 * 
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#contains_array
 *
 * To get the position of the occurrence use CONTAINS_ARRAY_IDX()
 */
@KarangoFuncMarker
fun <T> CONTAINS_ARRAY_IDX(anyArray: Expression<List<T>>, search: Expression<T>) = AqlFunc.CONTAINS_ARRAY.numberCall(anyArray, search, true.aql)

/**
 * Determine the number of elements in an array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#count
 */
@KarangoFuncMarker
fun <T> COUNT(anyArray: Expression<List<T>>) = AqlFunc.COUNT.numberCall(anyArray)

/**
 * Determine the number of distinct elements in an array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#count
 */
@KarangoFuncMarker
fun <T> COUNT_DISTINCT(anyArray: Expression<List<T>>) = AqlFunc.COUNT_DISTINCT.numberCall(anyArray)

/**
 * Determine the number of distinct elements in an array.
 *
 * Alias of COUNT_DISTINCT
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#count
 */
@KarangoFuncMarker
fun <T> COUNT_UNIQUE(anyArray: Expression<List<T>>) = AqlFunc.COUNT_UNIQUE.numberCall(anyArray)

/**
 * Get the first element of an array. It is the same as anyArray[0].
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#first
 */
@KarangoFuncMarker
fun <T> FIRST(anyArray: Expression<List<T>>) = AqlFunc.FIRST.nullableCall<T>(anyArray.getType().down(), anyArray)

/**
 * Turn an array of arrays into a flat array. All array elements in array will be expanded in the result array. 
 * Non-array elements are added as they are. The function will recurse into sub-arrays up to the specified depth. 
 * Duplicates will not be removed.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#flatten
 */
@KarangoFuncMarker
fun <T> FLATTEN(anyArray: Expression<List<T>>) = AqlFunc.FLATTEN.arrayCall(type<List<Any>>(), anyArray)

/**
 * Turn an array of arrays into a flat array. All array elements in array will be expanded in the result array.
 * Non-array elements are added as they are. The function will recurse into sub-arrays up to the specified depth.
 * Duplicates will not be removed.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#flatten
 */
@KarangoFuncMarker
fun <T> FLATTEN(anyArray: Expression<List<T>>, depth: Expression<Number>) = AqlFunc.FLATTEN.arrayCall(type<List<Any>>(), anyArray, depth)

/**
 * Return the intersection of all arrays specified. The result is an array of values that occur in all arguments.
 */
@KarangoFuncMarker
inline fun <reified T : Any> INTERSECTION(array1: Expression<out List<T>>, array2: Expression<out List<T>>, vararg arrayN: Expression<out List<T>>)
        = AqlFunc.INTERSECTION.arrayCall(type<List<T>>(), array1, array2, *arrayN)

/**
 * Determine the number of elements in an array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#length
 */
@KarangoFuncMarker
fun <T> LENGTH(anyArray: Expression<List<T>>) = AqlFunc.LENGTH.numberCall(anyArray)

/**
 * Return an array with its elements reversed.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#reverse
 */
@KarangoFuncMarker
fun <T> REVERSE(anyArray: Expression<List<T>>) = AqlFunc.REVERSE.arrayCall(anyArray.getType(), anyArray)


/**
 * Return whether search is contained in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#position
 *
 * To get the position of the occurrence use CONTAINS_ARRAY_IDX()
 */
@KarangoFuncMarker
fun <T> POSITION(anyArray: Expression<List<T>>, search: Expression<T>) = AqlFunc.POSITION.boolCall(anyArray, search)

/**
 * Return the position of the match (starting with 0) otherwise -1.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#position
 *
 * To get the position of the occurrence use CONTAINS_ARRAY_IDX()
 */
@KarangoFuncMarker
fun <T> POSITION_IDX(anyArray: Expression<List<T>>, search: Expression<T>) = AqlFunc.POSITION.numberCall(anyArray, search, true.aql)
