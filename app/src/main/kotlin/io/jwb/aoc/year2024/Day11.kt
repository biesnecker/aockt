package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInputAsSingleString

class Day11(input: String) {

    val nums = input.trim().split(' ').map { it.toLong() }

    val memo = mutableMapOf<Pair<Long, Int>, Long>()

    fun solve(num: Long, stepsLeft: Int): Long {
        if (stepsLeft == 0) {
            return 1
        }
        val mk = Pair(num, stepsLeft)
        if (mk in memo) {
            return memo[mk]!!
        }
        val res =
            if (num == 0L) {
                solve(1, stepsLeft - 1)
            } else {
                val s = num.toString()
                if (s.length % 2 == 0) {
                    val first = s.take(s.length / 2).toLong()
                    val second = s.substring(s.length / 2).toLong()
                    solve(first, stepsLeft - 1) + solve(second, stepsLeft - 1)
                } else {
                    solve(num * 2024, stepsLeft - 1)
                }
            }
        memo[mk] = res
        return res
    }

    fun partOne(): Long = nums.sumOf { solve(it, 25) }

    fun partTwo(): Long = nums.sumOf { solve(it, 75) }
}

fun main() {
    val solution = Day11(getInputAsSingleString(2024, 11))
    println("Part 1: ${solution.partOne()}")
    println("Part 1: ${solution.partTwo()}")
}