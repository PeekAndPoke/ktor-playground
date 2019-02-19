@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql


infix fun Expression<String>.CONTAINS(value: String): FunctionCall<Boolean> = 
    BoolFuncCall(AqlFunc.CONTAINS, listOf(this, ValueExpr(this, value)))

infix fun Expression<String>.CONTAINS(value: Expression<String>): FunctionCall<Boolean> =
    BoolFuncCall(AqlFunc.CONTAINS, listOf(this, value))
