package io.jwb.aoc.utils

import kotlinx.collections.immutable.persistentMapOf

class Grid(input: List<String>): AbstractMap<Coord, Char>() {
    init {
        require(input.isNotEmpty() && input[0].isNotEmpty())
    }
    val height = input.size
    val width = input[0].length

    val data = persistentMapOf<Coord, Char>().builder().apply {
        for ((y, line) in input.withIndex()) {
            for ((x, c) in line.trim().withIndex()) {
                put(Coord(x, y), c)
            }
        }
    }.build()

    fun inBounds(vararg cs: Coord): Boolean {
        return cs.all { c -> c.inBounds(width, height) }
    }

    override val entries: Set<Map.Entry<Coord, Char>>
        get() = data.entries

    override fun get(key: Coord): Char? {
        return data[key]
    }
}