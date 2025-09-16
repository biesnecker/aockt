package io.jwb.aoc.utils

import kotlin.math.abs

data class Coord(val x: Int, val y: Int) : Comparable<Coord> {

    override fun compareTo(other: Coord): Int {
        // Primary comparison by x, then by y
        return compareValuesBy(this, other, Coord::x, Coord::y)
    }

    operator fun plus(other: Coord): Coord = Coord(x + other.x, y + other.y)

    operator fun minus(other: Coord): Coord = Coord(x - other.x, y - other.y)

    operator fun div(by: Int): Coord = Coord(x / by, y / by)

    operator fun times(by: Int): Coord = Coord(x * by, y * by)

    fun move(dx: Int, dy: Int) = Coord(x + dx, y + dy)

    fun moveInDirection(direction: Direction, distance: Int = 1): Coord {
        return when (direction) {
            Direction.NORTH -> move(0, -distance)
            Direction.NORTHEAST -> move(distance, -distance)
            Direction.EAST -> move(distance, 0)
            Direction.SOUTHEAST -> move(distance, distance)
            Direction.SOUTH -> move(0, distance)
            Direction.SOUTHWEST -> move(-distance, distance)
            Direction.WEST -> move(-distance, 0)
            Direction.NORTHWEST -> move(-distance, -distance)
        }
    }

    fun moveInDirections(vararg directions: Direction): Coord {
        var loc = this
        directions.forEach {
            loc = loc.moveInDirection(it)
        }
        return loc
    }

    val allNeighbors: List<Coord>
        get() =
            listOf(
                move(-1, -1),
                move(0, -1),
                move(1, -1),
                move(-1, 0),
                move(1, 0),
                move(-1, 1),
                move(0, 1),
                move(1, 1)
            )

    val cardinalNeighbors: List<Coord>
        get() = listOf(move(0, -1), move(-1, 0), move(1, 0), move(0, 1))

    val diagonalNeighbors: List<Coord>
        get() = listOf(move(-1, -1), move(1, -1), move(-1, 1), move(1, 1))

    val north: Coord
        get() = moveInDirection(Direction.NORTH)

    val south: Coord
        get() = moveInDirection(Direction.SOUTH)

    val west: Coord
        get() = moveInDirection(Direction.WEST)

    val east: Coord
        get() = moveInDirection(Direction.EAST)

    val northeast: Coord
        get() = moveInDirection(Direction.NORTHEAST)

    val southeast: Coord
        get() = moveInDirection(Direction.SOUTHEAST)

    val southwest: Coord
        get() = moveInDirection(Direction.SOUTHWEST)

    val northwest: Coord
        get() = moveInDirection(Direction.NORTHWEST)

    fun manhattanDistance(other: Coord): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    fun inBounds(width: Int, height: Int): Boolean {
        return x in 0..<width && y in 0..<height
    }
}
