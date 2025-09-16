package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.getInput

class Day14(input: List<String>) {

    data class Robot(val position: Coord, val velocity: Coord) {

        fun quadrant(midpoint: Coord): Int = when {
            position.x < midpoint.x && position.y < midpoint.y -> 1
            position.x > midpoint.x && position.y < midpoint.y -> 2
            position.x < midpoint.x && position.y > midpoint.y -> 3
            position.x > midpoint.x && position.y > midpoint.y -> 4
            else -> 0
        }

        fun move(moves: Int, area: Coord): Robot = copy(
            position = (position + (velocity * moves)).wrap(area)
        )

        fun Coord.wrap(other: Coord): Coord {
            val nextX = x % other.x
            val nextY = y % other.y
            return Coord(
                if (nextX < 0) nextX + other.x else nextX,
                if (nextY < 0) nextY + other.y else nextY
            )
        }
    }

    val data = input.map { line ->
        Robot(
            position = Coord(
                line.substringAfter("=").substringBefore(",").toInt(),
                line.substringAfter(",").substringBefore(" ").toInt()
            ),
            velocity = Coord(
                line.substringAfterLast("=").substringBefore(",").toInt(),
                line.substringAfterLast(",").toInt()
            )
        )
    }

    val area = Coord(101, 103)

    fun partOne(): Int = data
        .map { it.move(100, area) }
        .groupingBy { it.quadrant(area / 2) }
        .eachCount()
        .filterNot { it.key == 0 }
        .values
        .reduce(Int::times)

    fun partTwo(): Int {
        var moves = 0
        var robotsThisTurn = data
        do {
            moves++
            robotsThisTurn = robotsThisTurn.map { it.move(1, area) }
        } while (robotsThisTurn.distinctBy { it.position }.size != robotsThisTurn.size)
        return moves
    }
}

fun main() {
    val solution = Day14(getInput(2024, 14))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}