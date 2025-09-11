package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.Direction
import io.jwb.aoc.utils.getInput

class Day06(input: List<String>) {

    val grid = input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c ->
            Pair(Coord(x, y), c == '.' || c == '^')
        }
    }.toMap().toMutableMap()

    val startPos = input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c -> if (c == '^') Coord(x, y) else null }
    }.filterNotNull().first()

    fun traverse(g: Map<Coord, Boolean>): Pair<Set<Coord>, Boolean> {
        val seen = mutableSetOf<Pair<Coord, Direction>>()
        var loc = startPos
        var dir = Direction.NORTH

        while (g[loc] != null && (loc to dir) !in seen) {
            seen += loc to dir
            val nextLoc = loc.moveInDirection(dir)
            if (g[nextLoc] == false) {
                dir = dir.turnRight()
            } else {
                loc = nextLoc
            }
        }
        return seen.map { it.first }.toSet() to (g[loc] != null)
    }

    fun partOne(): Int = traverse(grid).first.size

    fun partTwo(): Int =
        traverse(grid)
            .first
            .filterNot { it == startPos }
            .count { candidate ->
                traverse(grid.apply { put(candidate, false) }).also { grid[candidate] = true }.second
            }

}


fun main() {
    val solution = Day06(getInput(2024, 6))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}