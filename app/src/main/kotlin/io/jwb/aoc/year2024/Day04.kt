package io.jwb.aoc.year2024

import io.jwb.aoc.utils.Coord
import io.jwb.aoc.utils.Direction
import io.jwb.aoc.utils.Grid
import io.jwb.aoc.utils.getInput
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.withPermit


fun checkDirection(grid: Grid, loc: Coord, d: Direction): Boolean {
    val m = loc.moveInDirection(d)
    val a = m.moveInDirection(d)
    val s = a.moveInDirection(d)

    return grid.inBounds(m, a, s) && grid[m] == 'M' && grid[a] == 'A' && grid[s] == 'S'
}

fun checkAllDirections(grid: Grid, loc: Coord): Int {
    return Direction.entries.count {
        checkDirection(grid, loc, it)
    }
}

fun checkMas(grid: Grid, loc: Coord): Boolean {
    val ul = loc.northwest
    val ur = loc.northeast
    val ll = loc.southwest
    val lr = loc.southeast
    if (!grid.inBounds(ul, ur, ll, lr)) {
        return false
    }
    return when (Pair(grid[ul], grid[ur])) {
        Pair('M', 'M') -> grid[lr] == 'S' && grid[ll] == 'S'
        Pair('M', 'S') -> grid[lr] == 'S' && grid[ll] == 'M'
        Pair('S', 'M') -> grid[lr] == 'M' && grid[ll] == 'S'
        Pair('S', 'S') -> grid[lr] == 'M' && grid[ll] == 'M'
        else -> false
    }
}

suspend fun partOne(grid: Grid, sem: Semaphore): Int = coroutineScope {
    val jobs = grid.filter { it.value == 'X'}.map { async {
        sem.withPermit { checkAllDirections(grid, it.key)} }
    }
    jobs.awaitAll().sum()
}

suspend fun partTwo(grid: Grid, sem: Semaphore): Int = coroutineScope {
    val jobs = grid.filter { it.value == 'A' }.map { async {
        sem.withPermit { checkMas(grid, it.key ) }
    }}
    jobs.awaitAll().count { it }
}

fun main() {
    val grid = Grid(getInput(2024, 4))
    val cores = Runtime.getRuntime().availableProcessors()
    val limit = Semaphore(cores)
    runBlocking {
        val partOne = async { partOne(grid, limit) }
        val partTwo = async { partTwo(grid, limit) }
        println("Part 1: ${partOne.await()}")
        println("Part 2: ${partTwo.await()}")
    }
}
