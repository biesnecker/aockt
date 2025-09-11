package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.getInput
import io.jwb.aoc.utils.pairwise

class Day08(input: List<String>) {

    val stations = input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c -> if (c == '.') null else Pair(c, Coord(x, y)) }
    }.filterNotNull().groupBy({ it.first }, { it.second }).values.filter { it.size > 1 }

    val width = input[0].trim().length
    val height = input.size

    fun findAntinodes(f: (Coord, Coord, Coord) -> Set<Coord>): Int {
        return stations
            .flatMap { it.pairwise().flatMap { (a, b) -> f(a, b, a - b) } }
            .filter { it.inBounds(width, height) }
            .toSet().size
    }

    fun partOne(): Int = findAntinodes { a, b, diff ->
        when {
            a.y > b.y -> setOf(a - diff, b + diff)
            else -> setOf(a + diff, b - diff)
        }
    }

    fun partTwo(): Int = findAntinodes { a, _, diff ->
        val x = generateSequence(a) { it + diff }
            .takeWhile { it.inBounds(width, height) }
            .toSet()
        val y = generateSequence(a) { it - diff }
            .takeWhile { it.inBounds(width, height) }
            .toSet()
        x + y
    }
}

fun main() {
    val input = getInput(2024, 8)
    val solution = Day08(input)

    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}