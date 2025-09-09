package io.jwb.aoc.utils

import java.io.File

fun getInput(year: Int, day: Int): List<String> {
    val filename = "input/$year/${day.toString().padStart(2, '0')}.txt"
    return File(filename).readLines()
}
