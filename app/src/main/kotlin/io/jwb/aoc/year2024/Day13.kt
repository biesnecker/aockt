package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInput

class Day13(input: List<String>) {

    data class Input(val ax: Long, val ay: Long, val bx: Long, val by: Long, val px: Long, val py: Long) {

        fun solve(modifier: Long = 0): Long {
            val pxp = px + modifier
            val pyp = py + modifier
            val det = ax * by - ay * bx
            val a = pxp * by - pyp * bx
            val b = ax * pyp - ay * pxp
            if (a % det != 0L || b % det != 0L) {
                return 0
            }
            return (a / det) * 3 + (b / det)
        }
    }

    val data = input.chunked(4).map { lines ->
        val ax = lines[0].substringAfter("+").substringBefore(",").toLong()
        val ay = lines[0].substringAfterLast("+").toLong()
        val bx = lines[1].substringAfter("+").substringBefore(",").toLong()
        val by = lines[1].substringAfterLast("+").toLong()
        val px = lines[2].substringAfter("=").substringBefore(",").toLong()
        val py = lines[2].substringAfterLast("=").toLong()
        Input(ax, ay, bx, by, px, py)
    }

    fun partOne(): Long = data.sumOf { it.solve() }
    fun partTwo(): Long = data.sumOf { it.solve(10000000000000) }

}

fun main() {
    val solution = Day13(getInput(2024, 13))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}