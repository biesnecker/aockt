package io.jwb.aoc.year2019

import io.jwb.aoc.utils.getInput
import kotlin.math.absoluteValue

class Day22(input: List<String>) {

    sealed class Command {
        object DealNewStack : Command()
        data class DealWithIncrement(val increment: Int) : Command()
        data class Cut(val by: Int) : Command()
    }

    val commands = buildList {
        input.forEach { s ->
            if (s.startsWith("deal into")) {
                this.add(Command.DealNewStack)
            } else if (s.startsWith("deal with")) {
                val incr = s.substringAfterLast(' ').toInt()
                this.add(Command.DealWithIncrement(incr))
            } else {
                val by = s.substringAfterLast(' ').toInt()
                this.add(Command.Cut(by))
            }
        }
    }

    fun shuffle(cards: List<Int>, cmd: Command): List<Int> {
        return when (cmd) {
            is Command.DealNewStack -> cards.reversed()
            is Command.Cut -> {
                val amt = cmd.by.absoluteValue
                when {
                    cmd.by == 0 -> cards
                    cmd.by < 0 -> cards.takeLast(amt) + cards.dropLast(amt)

                    else -> cards.drop(amt) + cards.take(amt)
                }
            }

            is Command.DealWithIncrement -> {
                val newCards = cards.toMutableList()
                var idx = 0
                cards.forEach { card ->
                    newCards[idx] = card
                    idx += cmd.increment
                    idx %= cards.size
                }
                newCards
            }
        }

    }

    fun partOne(): Int {
        var cards = (0..10006).toList()
        for (c in commands) {
            cards = shuffle(cards, c)
        }
        return cards.indexOf(2019)
    }

    fun partTwo(): Int = 0
}

fun main() {
    val solution = Day22(getInput(2019, 22))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}