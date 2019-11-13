@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.ultra.common.kListType
import de.peekandpoke.ultra.common.unList

/**
 * Add all elements of an array to another array. All values are added at the end of the array (right side).
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#append
 */
@KarangoFuncMarker
inline fun <reified T> APPEND(anyArray: Expression<out List<T>>, values: Expression<out List<T>>) =
    AqlFunc.APPEND.arrayCall(kListType<T>(), anyArray, values)

/**
 * Return the population variance of the values in array.
 *
 * Alias for VARIANCE_POPULATION
 *
 * https://docs.arangodb.com/current/AQL/Functions/Array.html#append
 */
@KarangoFuncMarker
inline fun <reified T> APPEND(anyArray: Expression<out List<T>>, values: Expression<out List<T>>, unique: Expression<Boolean>) =
    AqlFunc.APPEND.arrayCall(kListType<T>(), anyArray, values, unique)

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
fun <T> CONTAINS_ARRAY(anyArray: Expression<List<T>>, search: Expression<T>) =
    AqlFunc.CONTAINS_ARRAY.boolCall(anyArray, search)

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
fun <T> CONTAINS_ARRAY_IDX(anyArray: Expression<List<T>>, search: Expression<T>) =
    AqlFunc.CONTAINS_ARRAY.numberCall(anyArray, search, true.aql)

/**
 * Determine the number of elements in an array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#count
 */
@KarangoFuncMarker
fun <T> COUNT(anyArray: Expression<List<T>>) =
    AqlFunc.COUNT.numberCall(anyArray)

/**
 * Determine the number of distinct elements in an array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#count
 */
@KarangoFuncMarker
fun <T> COUNT_DISTINCT(anyArray: Expression<List<T>>) =
    AqlFunc.COUNT_DISTINCT.numberCall(anyArray)

/**
 * Determine the number of distinct elements in an array.
 *
 * Alias of COUNT_DISTINCT
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#count
 */
@KarangoFuncMarker
fun <T> COUNT_UNIQUE(anyArray: Expression<List<T>>) =
    AqlFunc.COUNT_UNIQUE.numberCall(anyArray)

/**
 * Get the first element of an array. It is the same as anyArray[0].
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#first
 */
@KarangoFuncMarker
fun <T> FIRST(anyArray: Expression<List<T>>) =
    AqlFunc.FIRST.nullableCall(anyArray.getType().unList.nullable, anyArray)

/**
 * Turn an array of arrays into a flat array. All array elements in array will be expanded in the result array.
 * Non-array elements are added as they are. The function will recurse into sub-arrays up to the specified depth.
 * Duplicates will not be removed.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#flatten
 */
@KarangoFuncMarker
fun <T> FLATTEN(anyArray: Expression<List<T>>) =
    AqlFunc.FLATTEN.arrayCall(kListType<Any?>(), anyArray)

/**
 * Turn an array of arrays into a flat array. All array elements in array will be expanded in the result array.
 * Non-array elements are added as they are. The function will recurse into sub-arrays up to the specified depth.
 * Duplicates will not be removed.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#flatten
 */
@KarangoFuncMarker
fun <T, N : Number> FLATTEN(anyArray: Expression<List<T>>, depth: Expression<N>) =
    AqlFunc.FLATTEN.arrayCall(kListType<Any?>(), anyArray, depth)

/**
 * Return the intersection of all arrays specified. The result is an array of values that occur in all arguments.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#intersection
 */
@KarangoFuncMarker
inline fun <reified T : Any> INTERSECTION(array1: Expression<out List<T>>, array2: Expression<out List<T>>, vararg arrayN: Expression<out List<T>>) =
    AqlFunc.INTERSECTION.arrayCall(kListType<T>(), array1, array2, *arrayN)

/**
 * Get the last element of an array. It is the same as anyArray[-1].
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#last
 */
@KarangoFuncMarker
fun <T> LAST(anyArray: Expression<List<T>>) =
    AqlFunc.LAST.nullableCall(anyArray.getType().unList.nullable, anyArray)

/**
 * Determine the number of elements in an array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#length
 */
@KarangoFuncMarker
fun <T> LENGTH(anyArray: Expression<List<T>>) =
    AqlFunc.LENGTH.numberCall(anyArray)

/**
 * Return the difference of all arrays specified.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#minus
 */
@KarangoFuncMarker
inline fun <reified T : Any> MINUS(array1: Expression<out List<T>>, array2: Expression<out List<T>>, vararg arrayN: Expression<out List<T>>) =
    AqlFunc.MINUS.arrayCall(kListType<T>(), array1, array2, *arrayN)

/**
 * Get the element of an array at a given position. It is the same as anyArray[position] for positive positions, but does not support negative positions.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#nth
 */
@KarangoFuncMarker
fun <T, N : Number> NTH(anyArray: Expression<List<T>>, position: Expression<N>) =
    AqlFunc.NTH.nullableCall(anyArray.getType().unList.nullable, anyArray, position)

/**
 * Return the values that occur only once across all arrays specified.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#outersection
 */
@KarangoFuncMarker
inline fun <reified T : Any> OUTERSECTION(array1: Expression<out List<T>>, array2: Expression<out List<T>>, vararg arrayN: Expression<out List<T>>) =
    AqlFunc.OUTERSECTION.arrayCall(kListType<T>(), array1, array2, *arrayN)

/**
 * Remove the last element of array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#pop
 */
@KarangoFuncMarker
fun <T> POP(anyArray: Expression<List<T>>) =
    AqlFunc.POP.arrayCall(anyArray.getType(), anyArray)

/**
 * Return whether search is contained in array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#position
 *
 * To get the position of the occurrence use CONTAINS_ARRAY_IDX()
 */
@KarangoFuncMarker
fun <T> POSITION(anyArray: Expression<List<T>>, search: Expression<T>) =
    AqlFunc.POSITION.boolCall(anyArray, search)

/**
 * Return the position of the match (starting with 0) otherwise -1.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#position
 *
 * To get the position of the occurrence use CONTAINS_ARRAY_IDX()
 */
@KarangoFuncMarker
fun <T> POSITION_IDX(anyArray: Expression<List<T>>, search: Expression<T>) =
    AqlFunc.POSITION.numberCall(anyArray, search, true.aql)

/**
 * Append value to anyArray (right side).
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#push
 */
@KarangoFuncMarker
inline fun <reified T> PUSH(anyArray: Expression<out List<T>>, value: Expression<out T>) =
    AqlFunc.PUSH.arrayCall(kListType<T>(), anyArray, value)

/**
 * Remove the last element of array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#pop
 */
@KarangoFuncMarker
inline fun <reified T> PUSH(anyArray: Expression<out List<T>>, value: Expression<out T>, unique: Expression<Boolean>) =
    AqlFunc.PUSH.arrayCall(kListType<T>(), anyArray, value, unique)

/**
 * Remove the element at position from the anyArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#remove_nth
 */
@KarangoFuncMarker
fun <T, N : Number> REMOVE_NTH(anyArray: Expression<List<T>>, position: Expression<N>) =
    AqlFunc.REMOVE_NTH.arrayCall(anyArray.getType(), anyArray, position)

/**
 * Remove the element at value from the anyArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#remove_value
 */
@KarangoFuncMarker
fun <T> REMOVE_VALUE(anyArray: Expression<List<T>>, value: Expression<T>) =
    AqlFunc.REMOVE_VALUE.arrayCall(anyArray.getType(), anyArray, value)

/**
 * Remove the element at value from the anyArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#remove_value
 */
@KarangoFuncMarker
fun <T, N : Number> REMOVE_VALUE(anyArray: Expression<List<T>>, value: Expression<T>, limit: Expression<N>) =
    AqlFunc.REMOVE_VALUE.arrayCall(anyArray.getType(), anyArray, value, limit)

/**
 * Remove all occurrences of any of the values from anyArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#remove_values
 */
@KarangoFuncMarker
inline fun <reified T> REMOVE_VALUES(anyArray: Expression<out List<T>>, values: Expression<out List<T>>) =
    AqlFunc.REMOVE_VALUES.arrayCall(kListType<T>(), anyArray, values)

/**
 * Return an array with its elements reversed.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#reverse
 */
@KarangoFuncMarker
fun <T> REVERSE(anyArray: Expression<List<T>>) =
    AqlFunc.REVERSE.arrayCall(anyArray.getType(), anyArray)

/**
 * Remove the first element of array.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#shift
 */
@KarangoFuncMarker
fun <T> SHIFT(anyArray: Expression<List<T>>) =
    AqlFunc.SHIFT.arrayCall(anyArray.getType(), anyArray)

/**
 * Extract a slice of anyArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#slice
 */
@KarangoFuncMarker
fun <T, N : Number> SLICE(anyArray: Expression<List<T>>, start: Expression<N>) =
    AqlFunc.SLICE.arrayCall(anyArray.getType(), anyArray, start)

/**
 * Extract a slice of anyArray.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#slice
 */
@KarangoFuncMarker
fun <T, NS : Number, NL : Number> SLICE(anyArray: Expression<List<T>>, start: Expression<NS>, length: Expression<NL>) =
    AqlFunc.SLICE.arrayCall(anyArray.getType(), anyArray, start, length)

/**
 * Sort all elements in anyArray. The function will use the default comparison order for AQL value types.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#sorted
 */
@KarangoFuncMarker
fun <T> SORTED(anyArray: Expression<List<T>>) =
    AqlFunc.SORTED.arrayCall(anyArray.getType(), anyArray)

/**
 * Sort all elements in anyArray. The function will use the default comparison order for AQL value types.
 * Additionally, the values in the result array will be made unique.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#sorted
 */
@KarangoFuncMarker
fun <T> SORTED_UNIQUE(anyArray: Expression<List<T>>) =
    AqlFunc.SORTED_UNIQUE.arrayCall(anyArray.getType(), anyArray)

/**
 * Return the union of all arrays specified.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#union
 */
@KarangoFuncMarker
inline fun <reified T : Any> UNION(array1: Expression<out List<T>>, array2: Expression<out List<T>>, vararg arrayN: Expression<out List<T>>) =
    AqlFunc.UNION.arrayCall(kListType<T>(), array1, array2, *arrayN)

/**
 * Return the union of distinct values of all arrays specified.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#union_destinct
 */
@KarangoFuncMarker
inline fun <reified T : Any> UNION_DISTINCT(array1: Expression<out List<T>>, array2: Expression<out List<T>>, vararg arrayN: Expression<out List<T>>) =
    AqlFunc.UNION_DISTINCT.arrayCall(kListType<T>(), array1, array2, *arrayN)

/**
 * Return all unique elements in anyArray. To determine uniqueness, the function will use the comparison order.
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#unique
 */
@KarangoFuncMarker
fun <T> UNIQUE(anyArray: Expression<List<T>>) =
    AqlFunc.UNIQUE.arrayCall(anyArray.getType(), anyArray)

/**
 * Prepend value to anyArray (left side).
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#unshift
 */
@KarangoFuncMarker
inline fun <reified T> UNSHIFT(anyArray: Expression<out List<T>>, value: Expression<out T>) =
    AqlFunc.UNSHIFT.arrayCall(kListType<T>(), anyArray, value)

/**
 * Prepend value to anyArray (left side).
 *
 * See https://docs.arangodb.com/current/AQL/Functions/Array.html#unshift
 */
@KarangoFuncMarker
inline fun <reified T> UNSHIFT(anyArray: Expression<out List<T>>, value: Expression<out T>, unique: Expression<Boolean>) =
    AqlFunc.UNSHIFT.arrayCall(kListType<T>(), anyArray, value, unique)
