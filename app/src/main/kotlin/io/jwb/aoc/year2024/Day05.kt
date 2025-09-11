package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInput
import io.jwb.aoc.utils.midpoint

class Day05(input: List<String>) {

    private val rules: Set<String> = input.takeWhile { it.isNotEmpty() }.toSet()
    private val updates: List<List<String>> = input.dropWhile { it.isNotEmpty() }.drop(1).map { it.split(",") }

    private val mappings: List<Pair<List<String>, List<String>>> = updates.map {
        it to it.sortedWith { a, b ->
            when {
                "$a|$b" in rules -> -1
                "$b|$a" in rules -> 1
                else -> 0
            }
        }
    }

    fun partOne(): Int {
        return mappings.filter { it.first == it.second }.sumOf { it.second.midpoint().toInt() }
    }

    fun partTwo(): Int {
        return mappings.filter { it.first != it.second }.sumOf { it.second.midpoint().toInt() }
    }
}

fun main() {
    val input = getInput(2024, 5)
    val solution = Day05(input)
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}