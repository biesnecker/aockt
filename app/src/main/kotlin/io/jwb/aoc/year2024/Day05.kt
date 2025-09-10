package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInput
import kotlin.text.Regex

fun partOne(reqs: Map<Int, Set<Int>>, orders: List<List<Int>>): Int {
    var res = 0
    for (o in orders) {
        val seen = mutableSetOf<Int>()
        val all = o.toSet()
        var good = true
        for (item in o) {
            val needed = reqs.getOrDefault(item, setOf())
            val expected = needed.intersect(all)
            if (!seen.containsAll(expected)) {
                good = false
                break
            }
            seen.add(item)
        }
        if (good) {
            val middleIdx = o.size / 2
            res += o[middleIdx]
        }
    }
    return res
}

fun main() {
    val input = getInput(2024, 5)
    val sepIdx = input.indexOfFirst { it.isEmpty() }
    val r = Regex("""(\d+)\|(\d+)""")
    val reqs = mutableMapOf<Int, MutableSet<Int>>()
    for (i in 0 until sepIdx) {
        val mr = r.find(input[i].trim())!!
        val (left, right) = mr.destructured
        val leftAsInt = left.toInt()
        val rightAsInt = right.toInt()
        reqs.getOrPut(rightAsInt) { mutableSetOf() }.add(leftAsInt)
    }

    val orders = mutableListOf<List<Int>>()
    for (i in sepIdx + 1 until input.size) {
        val o = input[i].trim().split(",").map { it.toInt() }
        orders.add(o)
    }

    println("Part 1: ${partOne(reqs, orders)}")
}