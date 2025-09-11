package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInput
import kotlin.collections.map

class Day07(input: List<String>) {

    val problems = input.map { s ->
        val parts = s.split("""\D+""".toRegex())
        parts[0].toLong() to parts.subList(1, parts.size).map { it.toLong() }
    }

    val operations = listOf<(Long, Long) -> Long>(
        { a, b -> a + b },
        { a, b -> a * b }
    )

    val moreOperations = operations + { a, b -> "$a$b".toLong()}

    fun evaluate(target: Long, current: Long, remaining: List<Long>, ops: List<(Long, Long) -> Long>): Boolean {
        if (remaining.isEmpty()) {
            return target == current
        }
        if (current > target) {
            return false
        }
        return ops.any { op ->
            evaluate(target, op(current, remaining[0]), remaining.subList(1, remaining.size), ops)
        }
    }

    fun partOne(): Long {
        return problems.map { (k, v) ->
            k to evaluate(k, v[0], v.subList(1, v.size), operations)
        }.filter { it.second }.sumOf { it.first }
    }

    fun partTwo(): Long {
        return problems.map { (k, v) ->
            k to evaluate(k, v[0], v.subList(1, v.size), moreOperations)
        }.filter { it.second }.sumOf { it.first }
    }
}

fun main() {
    val solution = Day07(getInput(2024, 7))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}