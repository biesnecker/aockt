package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInputAsSingleString
import java.util.PriorityQueue

class Day09(input: String) {
    val disk = input
        .windowed(2, 2, true)
        .withIndex()
        .flatMap { (index, value) ->
            List(value.first().digitToInt()) { index.toLong() } +
                    List(value.getOrElse(1) { '0' }.digitToInt()) { null }
        }

    val blocks = buildList {
        var start = -1
        var prev: Long? = -1L
        disk.forEachIndexed { idx, v ->
            if (prev == -1L) {
                start = idx
                prev = v
            } else if (prev != v) {
                add(Block(start, idx - start, prev))
                start = idx
                prev = v
            }
        }
        if (start != -1) {
            add(Block(start, disk.size - start, prev))
        }
    }

    data class Block(val start: Int, val length: Int, val id: Long? = null) {
        fun checksum(index: Int = start): Long = (0 until length).sumOf { (index + it) * (id ?: 0L) }
    }

    fun MutableMap<Int, PriorityQueue<Int>>.findSpace(block: Block): Int =
        (block.length..9).mapNotNull { trySize ->
            if (this[trySize]?.isNotEmpty() == true) {
                val x = trySize to this.getValue(trySize).first()
                x
            } else {
                null
            }
        }.sortedBy { it.second }.filter { it.second < block.start }.firstNotNullOfOrNull { (size, start) ->
            this[size]?.poll()
            if (size != block.length) {
                val diff = size - block.length
                computeIfAbsent(diff) { _ -> PriorityQueue() }.add(start + block.length)
            }
            start
        } ?: block.start

    fun partOne(): Long {
        val empties = disk.indices.filter { disk[it] == null }.toMutableList()
        return disk.withIndex().reversed().sumOf { (index, value) ->
            if (value != null) {
                value * (empties.removeFirstOrNull() ?: index)
            } else {
                empties.removeLastOrNull()
                0
            }
        }
    }

    fun partTwo(): Long {
        val freeSpace: MutableMap<Int, PriorityQueue<Int>> = blocks
            .filter { it.id == null }
            .groupBy({ it.length }, { it.start })
            .mapValues { (_, v) -> PriorityQueue(v) }
            .toMutableMap()
        return blocks.filterNot { it.id == null }.reversed().sumOf { block ->
            block.checksum(freeSpace.findSpace(block))
        }
    }
}

fun main() {
    val solution = Day09(getInputAsSingleString(2024, 9))
    println("Part 1: ${solution.partOne()}")
    println("Part 2: ${solution.partTwo()}")
}