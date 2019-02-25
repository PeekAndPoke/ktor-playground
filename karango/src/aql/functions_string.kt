@file:Suppress("FunctionName")

package de.peekandpoke.karango.aql

import de.peekandpoke.karango.aql.AqlFunc.CONTAINS
import de.peekandpoke.karango.aql.FuncCall.Companion.bool


infix fun Expression<String>.CONTAINS(value: String) = bool(CONTAINS, listOf(this, ValueExpression(this, value)))

infix fun Expression<String>.CONTAINS(value: Expression<String>) = bool(CONTAINS, listOf(this, value))
