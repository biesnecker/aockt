package io.jwb.aoc.utils

enum class Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    fun turnRight(): Direction {
        return when (this) {
            Direction.NORTH -> Direction.EAST
            Direction.NORTHEAST -> Direction.SOUTHEAST
            Direction.EAST -> Direction.SOUTH
            Direction.SOUTHEAST -> Direction.SOUTHWEST
            Direction.SOUTH -> Direction.WEST
            Direction.SOUTHWEST -> Direction.NORTHWEST
            Direction.WEST -> Direction.NORTH
            Direction.NORTHWEST -> Direction.NORTHEAST
        }
    }

    fun turnLeft(): Direction {
        return when (this) {
            Direction.NORTH -> Direction.WEST
            Direction.NORTHEAST -> Direction.NORTHWEST
            Direction.EAST -> Direction.NORTH
            Direction.SOUTHEAST -> Direction.NORTHEAST
            Direction.SOUTH -> Direction.EAST
            Direction.SOUTHWEST -> Direction.SOUTHEAST
            Direction.WEST -> Direction.SOUTH
            Direction.NORTHWEST -> Direction.SOUTHWEST
        }
    }
}
