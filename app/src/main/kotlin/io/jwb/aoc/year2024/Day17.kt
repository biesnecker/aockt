package io.jwb.aoc.year2024

import io.jwb.aoc.utils.getInput

class Day17(val input: List<String>) {

    private data class Computer(
        var registerA: Long,
        var registerB: Long,
        var registerC: Long,
        val program: List<Int>
    ) {
        private var ip: Int = 0
        private val output = mutableListOf<Long>()

        private fun Int.toComboOp(): Long = when (this) {
            in 0..3 -> toLong()
            4 -> registerA
            5 -> registerB
            6 -> registerC
            else -> throw IllegalArgumentException("Invalid operand: $this")
        }

        fun step(): Boolean {
            if (ip > program.lastIndex) {
                return false
            }

            val op = program[ip + 1]
            val combo = op.toComboOp()
            when (program[ip]) {
                0 -> {
                    registerA = registerA shr combo.toInt()
                    ip += 2
                }

                1 -> {
                    registerB = registerB xor op.toLong()
                    ip += 2
                }

                2 -> {
                    registerB = combo % 8
                    ip += 2
                }

                3 -> {
                    ip = if (registerA == 0L) {
                        ip + 2
                    } else {
                        op
                    }
                }

                4 -> {
                    registerB = registerB xor registerC
                    ip += 2
                }

                5 -> {
                    output.add(combo % 8)
                    ip += 2
                }

                6 -> {
                    registerB = registerA shr combo.toInt()
                    ip += 2
                }

                7 -> {
                    registerC = registerA shr combo.toInt()
                    ip += 2
                }
            }
            return true
        }

        fun run(): List<Long> {
            var running = true
            while (running) {
                running = step()
            }
            return output
        }

        companion object {
            fun of(inp: List<String>): Computer =
                Computer(
                    inp[0].substringAfterLast(" ").toLong(),
                    inp[1].substringAfterLast(" ").toLong(),
                    inp[2].substringAfterLast(" ").toLong(),
                    inp[4].substringAfterLast(" ").split(",").map { it.toInt() }
                )
        }
    }

    fun partOne(): String = Computer.of(input).run().joinToString(",")

    fun partTwo(): Long = Computer.of(input).program
        .reversed()
        .map { it.toLong() }
        .fold(listOf(0L)) { candidates, instruction ->
            candidates.flatMap { candidate ->
                val shifted = candidate shl 3
                (shifted..shifted + 8).mapNotNull { attempt ->
                    Computer.of(input).run {
                        registerA = attempt
                        attempt.takeIf { run().first() == instruction }
                    }
                }
            }
        }.first()
}

fun main() {
    val solution = Day17(getInput(2024, 17))

    println("Part 1: ${solution.partOne()}")
    println("Part 1: ${solution.partTwo()}")
}