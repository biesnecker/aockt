package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.Direction
import io.jwb.aoc.utils.getInput

class Day12(input: List<String>) {

    data class Region(val area: Int, val perimeter: Int, val sides: Int)

    private fun Coord.corners(): Int =
        listOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH)
            .zipWithNext()
            .map { (first, second) ->
                listOf(
                    grid[this],
                    grid[this.moveInDirection(first)],
                    grid[this.moveInDirection(second)],
                    grid[this.moveInDirections(first, second)]
                )
            }.count { (target, side1, side2, corner) ->
                (target != side1 && target != side2) ||
                        (side1 == target && side2 == target && corner != target)
            }

    val grid = input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c -> Coord(x, y) to c }
    }.toMap()

    fun flood(start: Coord, seen: MutableSet<Coord>): Region? {
        if (start in seen) {
            return null
        }
        val target = grid[start]
        require(target != null)
        val q = ArrayDeque(listOf(start))
        var area = 0
        var perimeter = 0
        var corners = 0

        while (q.isNotEmpty()) {
            val loc = q.removeFirst()
            if (grid[loc] == target && loc !in seen) {
                seen += loc
                area++
                corners += loc.corners()
                loc.cardinalNeighbors.forEach { n ->
                    if (grid[n] == target) {
                        q.addLast(n)
                    } else {
                        perimeter++
                    }
                }
            }
        }
        return Region(area, perimeter, corners)
    }

    fun findRegions(): List<Region> = mutableSetOf<Coord>().run {
        grid.mapNotNull { (k) -> flood(k, this) }
    }


    fun partOne(): Int = findRegions().sumOf { it.area * it.perimeter }

    fun partTwo(): Int = findRegions().sumOf { it.area * it.sides }
}

fun main() {
    val solution = Day12(getInput(2024, 12))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}