package io.jwb.aoc.year2024

import io.jwb.aoc.utils.buildGridNotNull
import io.jwb.aoc.utils.findFirst
import io.jwb.aoc.utils.getInput

class Day20(input: List<String>) {
    val grid = input.buildGridNotNull { c -> c.takeIf { it in "SE." } }.map { it.first }.toSet()

    val start = input.findFirst('S')
    val end = input.findFirst('E')

    val path = buildList {
        add(start)
        while (last() != end) {
            add(
                last()
                    .cardinalNeighbors
                    .filter { it in grid }
                    .first { it != getOrNull(lastIndex - 1) }
            )
        }
    }

    fun solve(goal: Int, cheatTime: Int): Int = path.indices.sumOf { start ->
        (start + goal..path.lastIndex).count { end ->
            val dist = path[start].distanceTo(path[end])
            dist <= cheatTime && dist <= end - start - goal
        }
    }

    fun partOne(): Int = solve(100, 2)

    fun partTwo(): Int = solve(100, 20)
}

fun main() {
    val solution = Day20(getInput(2024, 20))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")

}