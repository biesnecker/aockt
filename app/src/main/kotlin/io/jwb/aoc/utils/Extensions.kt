package io.jwb.aoc.utils

fun <T> List<T>.midpoint(): T =
    this[lastIndex / 2]

fun <T> List<T>.pairwise(): Sequence<Pair<T, T>> = sequence {
    for (i in indices) {
        for (j in i + 1 until size) {
            yield(this@pairwise[i] to this@pairwise[j])
        }
    }
}

fun List<String>.chunkByEmptyString(): Sequence<List<String>> = sequence {
    var i = 0
    while (i < this@chunkByEmptyString.size) {
        val sub = this@chunkByEmptyString.takeWhile { it.isNotEmpty() }
        yield(sub)
        i += sub.size + 1
    }
}

fun <T : Comparable<T>> Pair<T, T>.minMax(): Pair<T, T> =
    if (first <= second) this else second to first