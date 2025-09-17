package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.getInput

class Day18(input: List<String>) {

    val falling = input.map { s ->
        val x = s.substringBefore(',').toInt()
        val y = s.substringAfter(',').toInt()
        Coord(x, y)
    }

    fun navigate(count: Int): Int? {
        val grid = falling.take(count).toSet()
        val target = Coord(70, 70)
        val width = 71
        val height = 71

        val start = Coord(0, 0)
        val visited = mutableSetOf(start)
        val q = ArrayDeque(listOf(start to 0))
        while (q.isNotEmpty()) {
            val (pos, steps) = q.removeFirst()
            if (pos == target) {
                return steps
            }
            for (neighbor in pos.cardinalNeighbors.filter { c ->
                c.inBounds(
                    width,
                    height
                ) && c !in visited && c !in grid
            }) {
                visited += neighbor
                q.addLast(neighbor to (steps + 1))
            }
        }
        return null
    }

    val partOne = navigate(1024)

    fun partTwo(): Coord {
        var i = 1024
        var j = falling.lastIndex
        while (i < j) {
            val mid = (i + j) / 2
            val ans = navigate(mid)
            if (ans == null) {
                j = mid - 1
            } else {
                i = mid
            }
        }
        return falling[i]
    }
}

fun main() {
    val solution = Day18(getInput(2024, 18))

    println("Part 1: ${solution.partOne}")
    println("Part 2: ${solution.partTwo()}")
}