package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.getInput

class Day10(input: List<String>) {

    val grid = input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c ->
            Coord(x, y) to c.digitToInt()
        }
    }.toMap()

    val zeros = grid.filter { it.value == 0 }.map { it.key }.toList()

    fun findPaths(loc: Coord, unique: Boolean = false): Int {
        var res = 0
        val seen = mutableSetOf<Coord>(loc)
        val q = ArrayDeque<Coord>(listOf(loc))

        while (q.isNotEmpty()) {
            val pos = q.removeFirst()
            val posVal = grid.get(pos)
            require(posVal != null)
            if (posVal == 9) {
                res++
                continue
            }
            pos.cardinalNeighbors.forEach { nextPos ->
                if ((unique || nextPos !in seen) && grid[nextPos] == posVal + 1) {
                    q.addLast(nextPos)
                    if (!unique) {
                        seen += nextPos
                    }
                }
            }

        }
        return res
    }

    fun partOne(): Int = zeros.sumOf(::findPaths)
    fun partTwo(): Int = zeros.sumOf { findPaths(it, true) }
}

fun main() {
    val solution = Day10(getInput(2024, 10))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}