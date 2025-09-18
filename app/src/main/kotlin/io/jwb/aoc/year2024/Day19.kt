package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInput

class Day19(input: List<String>) {

    val patterns = input.takeWhile { it.isNotEmpty() }.flatMap { it.split(", ") }

    val towels = input.dropWhile { it.isNotEmpty() }.dropWhile { it.isEmpty() }

    val memo = mutableMapOf<String, Long>()

    fun possible(towel: String): Long {
        if (towel == "") {
            return 1L
        }
        if (towel in memo) {
            return memo.getValue(towel)
        }

        val total = patterns
            .filter { towel.startsWith(it) }
            .map { towel.removePrefix(it) }
            .sumOf { possible(it) }
        memo[towel] = total
        return total
    }

    fun solve(): Pair<Int, Long> {
        var partOne = 0
        var partTwo = 0L
        
        for (t in towels) {
            val c = possible(t)
            if (c > 0) {
                partOne++
                partTwo += c
            }
        }

        return partOne to partTwo
    }
}

fun main() {
    val solution = Day19(getInput(2024, 19)).solve()
    println("Part 1: ${solution.first}")
    println("Part 1: ${solution.second}")
}