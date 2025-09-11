package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.Direction
import io.jwb.aoc.utils.getInput

class Day06(input: List<String>) {

    val grid = input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c ->
            Pair(Coord(x, y), c == '.' || c == '^')
        }
    }.toMap()

    val startPos = input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c -> if (c == '^') Coord(x, y) else null }
    }.filterNotNull().first()

    fun traverse(): Int {
        val seen = mutableSetOf<Coord>()
        var loc = startPos
        var dir = Direction.NORTH

        while (grid[loc] != null) {
            seen += loc
            val nextLoc = loc.moveInDirection(dir)
            if (grid[nextLoc] == false) {
                dir = dir.turnRight()
            } else {
                loc = nextLoc
            }
        }
        return seen.size
    }

    fun partOne(): Int = traverse()

}


fun main() {
    val solution = Day06(getInput(2024,6))
    println("Part 1: ${solution.partOne()}")
}