@file:Suppress("FunctionName")

package de.peekandpoke.karango.query

import de.peekandpoke.karango.Expression
import de.peekandpoke.karango.typeRef


infix fun Expression<String>.CONTAINS(value: String): FunctionCall<Boolean> =
    FunctionCallImpl(typeRef(), AqlFunc.CONTAINS, listOf(this, ValueExpression(this, value)))

infix fun Expression<String>.CONTAINS(value: Expression<String>): FunctionCall<Boolean> =
    FunctionCallImpl(typeRef(), AqlFunc.CONTAINS, listOf(this, value))
