package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.Direction
import io.jwb.aoc.utils.getInput
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class Day04(input: List<String>) {
    val grid = input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c -> Coord(x, y) to c }
    }.toMap()

    val sem = Semaphore(Runtime.getRuntime().availableProcessors())

    fun checkDirection(loc: Coord, d: Direction): Boolean {
        val m = loc.moveInDirection(d)
        val a = m.moveInDirection(d)
        val s = a.moveInDirection(d)
        return grid[m] == 'M' && grid[a] == 'A' && grid[s] == 'S'
    }

    fun checkAllDirections(loc: Coord): Int = Direction.entries.count { checkDirection(loc, it) }

    fun checkMas(loc: Coord): Boolean {
        val ul = loc.northwest
        val ur = loc.northeast
        val ll = loc.southwest
        val lr = loc.southeast
        return when (Pair(grid[ul], grid[ur])) {
            Pair('M', 'M') -> grid[lr] == 'S' && grid[ll] == 'S'
            Pair('M', 'S') -> grid[lr] == 'S' && grid[ll] == 'M'
            Pair('S', 'M') -> grid[lr] == 'M' && grid[ll] == 'S'
            Pair('S', 'S') -> grid[lr] == 'M' && grid[ll] == 'M'
            else -> false
        }
    }

    suspend fun partOne(): Int = coroutineScope {
        grid
            .filter { it.value == 'X' }
            .map { (loc, _) -> async { sem.withPermit { checkAllDirections(loc) } } }
            .awaitAll()
            .sum()
    }

    suspend fun partTwo(): Int = coroutineScope {
        grid
            .filter { it.value == 'A' }
            .map { (loc, _) -> async { sem.withPermit { checkMas(loc) } } }
            .awaitAll()
            .count { it }
    }
}

fun main() {
    runBlocking {
        val solution = Day04(getInput(2024, 4))
        val partOne = async { solution.partOne() }
        val partTwo = async { solution.partTwo() }
        println("Part 1: ${partOne.await()}")
        println("Part 2: ${partTwo.await()}")
    }
}
