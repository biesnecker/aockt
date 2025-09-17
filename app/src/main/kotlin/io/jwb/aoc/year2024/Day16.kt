package io.jwb.aoc.year2024

import io.jwb.aoc.utils.*

class Day16(input: List<String>) {

    val grid = input.map { it.toCharArray() }

    val start = grid.findFirst('S')
    val end = grid.findFirst('E')

    data class QItem(val pos: Coord, val dir: Direction, val cost: Int, val path: List<Coord>)

    private fun List<CharArray>.valid(pos: Coord): Boolean = this.inBounds(pos) && this[pos] != '#'

    fun solve(): Pair<Int, Int> {
        var best: Int = Int.MAX_VALUE
        val bestSquare = mutableMapOf<Pair<Coord, Direction>, Int>()
        var bestPaths = mutableSetOf<Coord>()

        val better = { pos: Coord, dir: Direction, cost: Int ->
            // If the square has never been explored, of its lowest seen cost is higher than
            // the proposed cost, then it might be a better option to explore that square.
            bestSquare.getOrDefault(pos to dir, Int.MAX_VALUE) >= cost
        }

        val s = mutableListOf(QItem(start, Direction.EAST, 0, emptyList()))
        while (s.isNotEmpty()) {
            val it = s.removeLast()
            if (it.pos == end) {
                if (it.cost < best) {
                    best = it.cost
                    bestPaths = it.path.toMutableSet()
                } else if (it.cost == best) {
                    it.path.forEach { item -> bestPaths.add(item) }
                }
                continue
            } else if (it.cost >= best) {
                continue
            }

            val ltd = it.dir.turnLeft()
            val lt = it.pos + ltd
            if (grid.valid(lt) && better(it.pos, ltd, it.cost + 1000)) {
                s.add(QItem(it.pos, ltd, it.cost + 1000, it.path))
                bestSquare[it.pos to ltd] = it.cost + 1000
            }

            val rtd = it.dir.turnRight()
            val rt = it.pos + rtd
            if (grid.valid(rt) && better(it.pos, rtd, it.cost + 1000)) {
                s.add(QItem(it.pos, rtd, it.cost + 1000, it.path))
                bestSquare[it.pos to rtd] = it.cost + 1000
            }

            val forward = it.pos + it.dir
            if (grid.valid(forward) && better(forward, it.dir, it.cost + 1)) {
                val np = it.path.toMutableList()
                np += forward
                s.add(QItem(forward, it.dir, it.cost + 1, np))
                bestSquare[forward to it.dir] = it.cost + 1
            }
        }

        return best to (bestPaths + start).size
    }

}

fun main() {
    val solution = Day16(getInput(2024, 16)).solve()
    println("Part 1: ${solution.first}")
    println("Part 2: ${solution.second}")
}