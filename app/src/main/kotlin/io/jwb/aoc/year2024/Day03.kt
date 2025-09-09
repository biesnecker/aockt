package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInputAsSingleString

fun calcSum(input: String): Int {
    var total = 0
    for (match in Regex("""mul\((\d+),(\d+)\)""").findAll(input)) {
        val (a, b) = match.destructured
        total += a.toInt() * b.toInt()
    }
    return total
}

fun partOne(input: String) = calcSum(input)

fun partTwo(input: String): Int {
    var remaining = input
    var enabled = true
    var total = 0
    while (!remaining.isEmpty()) {
        if (enabled) {
            val chunk = remaining.substringBefore("don't()")
            total += calcSum(chunk)
            remaining = remaining.substringAfter("don't()", "")
            enabled = false
        } else {
            remaining = remaining.substringAfter("do()", "")
            enabled = true
        }
    }

    return total
}

fun main() {
    val input = getInputAsSingleString(2024, 3)
    println("Part 1: ${partOne(input)}")
    println("Part 2: ${partTwo(input)}")
}
