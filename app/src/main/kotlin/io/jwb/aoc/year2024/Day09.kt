package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInputAsSingleString


class Day09(input: String) {
    val disk = input
        .windowed(2, 2, true)
        .withIndex()
        .flatMap { (index, value) -> List(value.first().digitToInt()) { index.toLong() } +
                List(value.getOrElse(1) { '0' }.digitToInt()) { null }
    }

    fun partOne(): Long {
        val empties = disk.indices.filter { disk[it] == null }.toMutableList()
        return disk.withIndex().reversed().sumOf { (index, value) ->
            if (value != null) {
                value * (empties.removeFirstOrNull() ?: index)
            } else {
                empties.removeLastOrNull()
                0
            }
        }
    }
}

fun main() {
    val solution = Day09(getInputAsSingleString(2024, 9))
    println("Part 1: ${solution.partOne()}")
}