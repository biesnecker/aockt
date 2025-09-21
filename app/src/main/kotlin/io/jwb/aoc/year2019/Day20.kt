package io.jwb.aoc.year2019

import io.jwb.aoc.utils.*

class Day20(input: List<String>) {

    val grid = input.findAll('.').toSet()

    val outwardX = buildSet {
        add(2)
        add(input.maxOfOrNull { it.length - 3 } ?: 0)
    }
    val outwardY = setOf(2, input.size - 3)

    val portals = buildMap {
        val letters = 'A'..'Z'

        for ((loc, c) in input.findAll { it.isLetter() }) {
            when {
                // Vertical
                input.getOrNull(loc.south) in letters -> {
                    val label = "$c${input[loc.south]}"
                    val lst = this.getOrPut(label) { mutableListOf() }
                    if (loc.north in grid) {
                        lst.add(loc.north)
                    } else {
                        lst.add(loc.south.south)
                    }
                }

                // Horizontal
                input.getOrNull(loc.east) in letters -> {
                    var label = "$c${input[loc.east]}"
                    val lst = this.getOrPut(label) { mutableListOf() }
                    if (loc.west in grid) {
                        lst.add(loc.west)
                    } else {
                        lst.add(loc.east.east)
                    }
                }
            }
        }
    }.mapValues { it.value.toList() }

    val start = portals["AA"]!!.first()
    val end = portals["ZZ"]!!.first()
    val exits = buildMap {
        portals.filter { it.key != "AA" && it.key != "ZZ" }.forEach {
            require(it.value.size == 2)
            this[it.value[0]] = Pair(it.key, it.value[1])
            this[it.value[1]] = Pair(it.key, it.value[0])
        }
    }

    fun Coord.neighborsWithPortals(): Sequence<Coord> = sequence {
        val c = this@neighborsWithPortals
        for (n in c.cardinalNeighbors.filter { it in grid }) {
            yield(n)
        }
        exits[c]?.let { yield(it.second) }
    }

    fun partOne(): Int {
        val seen = mutableSetOf(start)
        val q = ArrayDeque<Pair<Coord, Int>>().apply { add(start to 0) }

        while (q.isNotEmpty()) {
            val (pos, steps) = q.removeFirst()
            if (pos == end) {
                return steps
            }
            for (npos in pos.neighborsWithPortals().filterNot { it in seen }) {
                seen += npos
                q.add(npos to (steps + 1))
            }
        }
        throw IllegalStateException("No exit found")
    }

    fun Pair<Coord, Int>.neighborsWithPortalsAndLevels(): Sequence<Pair<Coord, Int>> = sequence {
        val (c, level) = this@neighborsWithPortalsAndLevels
        for (next in c.cardinalNeighbors) {
            if (next in grid) {
                yield(next to level)
            }
        }
        exits[c]?.let {
            val nextLevel = level + if (c.x in outwardX || c.y in outwardY) -1 else 1
            if (nextLevel >= 0 && nextLevel <= exits.size / 2) {
                yield(it.second to nextLevel)
            }
        }
    }

    fun partTwo(): Int {
        val seen = mutableSetOf(start to 0)
        val q = ArrayDeque<Triple<Coord, Int, Int>>().apply { add(Triple(start, 0, 0)) }

        while (q.isNotEmpty()) {
            val (pos, level, steps) = q.removeFirst()
            if (pos == end && level == 0) {
                return steps
            }

            for (next in (pos to level).neighborsWithPortalsAndLevels().filterNot { it in seen }) {
                seen += next
                q.add(Triple(next.first, next.second, steps + 1))
            }
        }
        throw IllegalStateException("No exit found")
    }
}

fun main() {
    val solution = Day20(getInput(2019, 20))

    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}