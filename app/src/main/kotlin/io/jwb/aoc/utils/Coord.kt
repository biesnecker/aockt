package io.jwb.aoc.utils

import kotlin.math.abs

data class Coord(val x: Int, val y: Int) : Comparable<Coord> {

    override fun compareTo(other: Coord): Int {
        // Primary comparison by x, then by y
        return compareValuesBy(this, other, Coord::x, Coord::y)
    }

    // Manhattan distance between two coordinates
    fun distanceTo(other: Coord): Int = abs(x - other.x) + abs(y - other.y)

    operator fun plus(other: Coord): Coord = Coord(x + other.x, y + other.y)

    operator fun plus(dir: Direction): Coord = moveInDirection(dir)

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
        get() = Direction.entries.map { this + it }

    val cardinalNeighbors: List<Coord>
        get() = listOf(north, south, east, west)

    val diagonalNeighbors: List<Coord>
        get() = listOf(northeast, southeast, southwest, northwest)

    val north: Coord
        get() = this + Direction.NORTH

    val south: Coord
        get() = this + Direction.SOUTH

    val west: Coord
        get() = this + Direction.WEST

    val east: Coord
        get() = this + Direction.EAST

    val northeast: Coord
        get() = this + Direction.NORTHEAST

    val southeast: Coord
        get() = this + Direction.SOUTHEAST

    val southwest: Coord
        get() = this + Direction.SOUTHWEST

    val northwest: Coord
        get() = this + Direction.NORTHWEST

    fun manhattanDistance(other: Coord): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    fun inBounds(width: Int, height: Int): Boolean {
        return x in 0..<width && y in 0..<height
    }
}
