package io.jwb.aoc.year2019

import io.jwb.aoc.utils.*

class Day18() {

    class Maze(
        private val starts: Set<Coord>,
        private val keys: Map<Coord, Char>,
        private val doors: Map<Coord, Char>,
        private val open: Set<Coord>
    ) {

        fun minimumSteps(
            from: Set<Coord> = starts,
            keysHeld: Set<Char> = mutableSetOf(),
            seen: MutableMap<Pair<Set<Coord>, Set<Char>>, Int> = mutableMapOf()
        ): Int {
            val state = Pair(from, keysHeld)

            if (state in seen) return seen.getValue(state)

            val answer = findReachableFromPoints(from, keysHeld).map { entry ->
                val (at, dist, cause) = entry.value
                dist + minimumSteps((from - cause) + at, keysHeld + entry.key, seen)
            }.minOrNull() ?: 0
            seen[state] = answer
            return answer
        }

        fun findReachableFromPoints(from: Set<Coord>, keysHeld: Set<Char>): Map<Char, Triple<Coord, Int, Coord>> =
            from.flatMap { pos ->
                findReachableKeys(pos, keysHeld).map { entry ->
                    entry.key to Triple(entry.value.first, entry.value.second, pos)
                }
            }.toMap()


        fun findReachableKeys(from: Coord, keysHeld: Set<Char>): Map<Char, Pair<Coord, Int>> {
            val q = ArrayDeque<Coord>().apply { add(from) }
            val distance = mutableMapOf(from to 0)
            val keyDistance = mutableMapOf<Char, Pair<Coord, Int>>()

            while (q.isNotEmpty()) {
                val pos = q.removeFirst()
                pos.cardinalNeighbors.filter { it in open }.filterNot { it in distance }.forEach { npos ->
                    val ndist = distance.getValue(pos) + 1
                    distance[npos] = ndist
                    val door = doors[npos]
                    val key = keys[npos]
                    if (door == null || door.lowercaseChar() in keysHeld) {
                        if (key != null && key !in keysHeld) {
                            keyDistance[key] = Pair(npos, ndist)
                        } else {
                            q.addLast(npos)
                        }
                    }
                }
            }

            return keyDistance
        }

        companion object {
            fun from(input: List<String>): Maze {
                val starts = mutableSetOf<Coord>()
                val keys = mutableMapOf<Coord, Char>()
                val doors = mutableMapOf<Coord, Char>()
                val open = mutableSetOf<Coord>()

                input.forEachCoord { loc, c ->
                    if (c == '@') starts += loc
                    if (c != '#') open += loc
                    if (c in 'a'..'z') keys[loc] = c
                    if (c in 'A'..'Z') doors[loc] = c
                }

                return Maze(starts, keys, doors, open)
            }
        }
    }


    fun solve(input: List<String>): Int = Maze.from(input).minimumSteps()

}

fun transformInput(input: List<String>): List<String> {
    val ca = input.toCharArrayGrid()
    val start = ca.findFirst('@')

    return ca.apply {
        set(start, '#')
        start.diagonalNeighbors.forEach { set(it, '@') }
        start.cardinalNeighbors.forEach { set(it, '#') }
    }.toStringGrid()
}

fun main() {
    val input = getInput(2019, 18)
    val solution = Day18()
    println("Part 1: ${solution.solve(input)}")
    println("Part 2: ${solution.solve(transformInput(input))}")
}