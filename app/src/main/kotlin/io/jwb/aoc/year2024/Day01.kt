package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInput
import kotlin.math.abs

fun solveDay01(input: List<String>): Pair<Int, Int> {
    var left = mutableListOf<Int>()
    var right = mutableListOf<Int>()

    for (line in input) {
        val (l, r) = line.trim().split("\\s+".toRegex())
        left += l.toInt()
        right += r.toInt()
    }

    left.sort()
    right.sort()

    // Part 1
    var diff = 0
    for (i in left.indices) {
        diff += abs(left[i] - right[i])
    }

    // Part 2
    val rightFreq = right.groupingBy { it }.eachCount()
    var score = 0
    for (l in left) {
        score += l * rightFreq.getOrDefault(l, 0)
    }

    return Pair(diff, score)
}

fun main() {
    val input = getInput(2024, 1)
    val (part1, part2) = solveDay01(input)
    println("Part 1: $part1")
    println("Part 2: $part2")
}
