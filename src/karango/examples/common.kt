package de.peekandpoke.karango.examples

import de.peekandpoke.karango.Cursor


fun printDivider() =
    println("==========================================================================================================================")

fun <T> printQueryResult(result: Cursor<T>, output: (Int, T) -> String) {

    println()
    println("------------")
    println("-  AQL     -")
    println("------------")
    println(result.query.aql)

    println("------------")
    println("-  Vars    -")
    println("------------")
    println(result.query.vars)
    println()

    println("------------")
    println("-  Result  -")
    println("------------")
    println("(in ${result.timeMs} ms, execution time ${result.stats.executionTime} ms):")

    result.forEachIndexed { idx, it -> println(output(idx, it)) }

    println()
}

fun runDemo(builder: DemoRunner.Builder.() -> Unit) = DemoRunner.run(builder)

class DemoRunner private constructor(private val betweenSteps: BetweenSteps, private val steps: List<() -> Any>) {

    interface BetweenSteps {
        fun run()
    }

    class Noop : BetweenSteps {
        override fun run() {}
    }

    class HitEnter : BetweenSteps {
        override fun run() {

            println()
            println("Hit ENTER to continue...")
            println()

            readLine()
        }
    }

    class Builder internal constructor() {

        var betweenSteps: BetweenSteps = HitEnter()
        val steps = mutableListOf<() -> Any>()

        fun build() = DemoRunner(betweenSteps, steps.toList())

        fun noop() = apply { this.betweenSteps = Noop() }
        fun hitEnter() = apply { this.betweenSteps = HitEnter() }
        fun steps(vararg steps: () -> Any) = apply { this.steps.addAll(steps.toList()) }
    }

    companion object {
        fun build(builder: Builder.() -> Unit) = Builder().apply(builder).build()

        fun run(builder: Builder.() -> Unit) {
            build(builder).run()
        }
    }

    fun run() {
        steps.forEach { step ->
            step()
            betweenSteps.run()
        }
    }
} 

