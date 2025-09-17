package io.jwb.aoc.year2024

import io.jwb.aoc.utils.*

class Day15(input: List<String>) {

    private fun Char.toDirection(): Direction = when (this) {
        '^' -> Direction.NORTH
        '<' -> Direction.WEST
        '>' -> Direction.EAST
        'v' -> Direction.SOUTH
        else -> throw IllegalArgumentException("Invalid direction: $this")
    }

    private fun Coord.boxScore(): Int = y * 100 + x

    val grid = input.takeWhile { it.isNotEmpty() }.map { it.toCharArray() }

    private fun CharArray.remap(): CharArray =
        joinToString("") {
            when (it) {
                '#' -> "##"
                'O' -> "[]"
                '.' -> ".."
                '@' -> "@."
                else -> throw IllegalArgumentException("Invalid $it")
            }
        }.toCharArray()

    val grid2 = input.takeWhile { it.isNotEmpty() }.map { it.toCharArray().remap() }

    val moves = input
        .dropWhile { it.isNotEmpty() }
        .dropWhile { it.isEmpty() }
        .flatMap { s -> s.map { it.toDirection() } }

    fun List<CharArray>.push(pos: Coord, dir: Direction): List<Pair<Coord, Coord>>? {
        val safe = mutableListOf<Pair<Coord, Coord>>()
        val q = ArrayDeque(listOf(pos))
        val seen = mutableSetOf<Coord>()

        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            if (cur !in seen) {
                seen += cur
                if (dir in setOf(Direction.NORTH, Direction.SOUTH)) {
                    when (get(cur)) {
                        ']' -> q.add(cur + Direction.WEST)
                        '[' -> q.add(cur + Direction.EAST)
                    }
                }
                val next = cur + dir
                when (get(next)) {
                    '#' -> return null
                    in "[O]" -> q.add(next)
                }
                safe.add(cur to next)
            }
        }

        return safe.reversed()
    }

    private fun List<CharArray>.makeMoves(): List<CharArray> {
        val start = findFirst('@')
        var pos = start
        moves.forEach { move ->
            val next = pos + move
            when (this[next]) {
                in "[O]" -> {
                    push(next, move)?.let { newMoves ->
                        newMoves.forEach { (from, to) ->
                            this[to] = this[from]
                            this[from] = '.'
                        }
                        pos = next
                    }
                }

                !in "#" -> {
                    pos = next
                }
            }
        }
        return this
    }

    fun partOne(): Int = grid.makeMoves().findAll('O').sumOf { it.boxScore() }

    fun partTwo(): Int = grid2.makeMoves().findAll('[').sumOf { it.boxScore() }
}

fun main() {
    val solution = Day15(getInput(2024, 15))

    println("Part 1: ${solution.partOne()}")

    println("Part 2: ${solution.partTwo()}")
}