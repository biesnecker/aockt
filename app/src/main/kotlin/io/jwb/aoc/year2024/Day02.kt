package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInputAsDelimitedListOfInts
import kotlin.math.*

fun check(nums: List<Int>): Boolean {
    val dir = (nums[1] - nums[0]).sign
    if (dir == 0) return false
    for (i in 1 until nums.size) {
        val diff = nums[i] - nums[i - 1]
        if (abs(diff) < 1 || abs(diff) > 3 || diff.sign != dir) {
            return false
        }
    }
    return true
}

fun checkAll(nums: List<Int>): Boolean {
    for (i in 0 until nums.size) {
        var candidate = mutableListOf<Int>()
        for (j in 0 until nums.size) {
            if (i != j) {
                candidate.add(nums[j])
            }
        }
        if (check(candidate)) {
            return true
        }
    }
    return false
}

fun main() {
    val input = getInputAsDelimitedListOfInts(2024, 2)

    var partOne = 0
    var partTwo = 0

    for (nums in input) {
        if (check(nums)) {
            partOne++
            partTwo++
        } else if (checkAll(nums)) {
            partTwo++
        }
    }

    println("Part 1: $partOne")
    println("Part 2: $partTwo")
}
