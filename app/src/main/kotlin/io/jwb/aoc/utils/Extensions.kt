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

fun List<String>.toCharArrayGrid(): List<CharArray> = map { it.toCharArray() }

@JvmName("debugPrintListString")
fun List<String>.debugPrint() = this.forEach { println(it) }

@JvmName("debugPrintListCharArray")
fun List<CharArray>.debugPrint() = this.forEach { print(it.toString()) }

fun List<CharArray>.toStringGrid(): List<String> = map { it.joinToString("") }

fun List<String>.forEachCoord(f: (Coord, Char) -> Unit) {
    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, c -> f(Coord(x, y), c) }
    }
}

fun <T> List<String>.buildGrid(f: (Int, Int, Char) -> T): Sequence<Pair<Coord, T>> = sequence {
    this@buildGrid.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c ->
            yield(Pair(Coord(x, y), f(x, y, c)))
        }
    }
}

fun <T> List<String>.buildGrid(f: (Char) -> T): Sequence<Pair<Coord, T>> = sequence {
    this@buildGrid.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c ->
            yield(Pair(Coord(x, y), f(c)))
        }
    }
}

fun <T> List<String>.buildGridNotNull(f: (Int, Int, Char) -> T?): Sequence<Pair<Coord, T>> = sequence {
    this@buildGridNotNull.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c ->
            val foo = f(x, y, c)
            if (foo != null) {
                yield(Pair(Coord(x, y), foo))
            }
        }
    }
}

fun <T> List<String>.buildGridNotNull(f: (Char) -> T?): Sequence<Pair<Coord, T>> = sequence {
    this@buildGridNotNull.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c ->
            val foo = f(c)
            if (foo != null) {
                yield(Pair(Coord(x, y), foo))
            }
        }
    }
}

fun List<String>.getOrNull(loc: Coord): Char? = this.getOrNull(loc.y)?.getOrNull(loc.x)

@JvmName("getListString")
operator fun List<String>.get(loc: Coord): Char = this[loc.y][loc.x]

@JvmName("getListCharArray")
operator fun List<CharArray>.get(loc: Coord): Char = this[loc.y][loc.x]

operator fun List<CharArray>.set(loc: Coord, item: Char) {
    this[loc.y][loc.x] = item
}

fun List<CharArray>.inBounds(pos: Coord): Boolean = pos.inBounds(this[0].size, this.size)

@JvmName("findFirstListCharArray")
fun List<CharArray>.findFirst(item: Char): Coord = this.findAll(item).first()

@JvmName("findFirstListCharArray")
fun List<CharArray>.findFirst(predicate: (Char) -> Boolean): Pair<Coord, Char> = this.findAll(predicate).first()

@JvmName("findFirstListString")
fun List<String>.findFirst(item: Char): Coord = this.findAll(item).first()

@JvmName("findFirstListString")
fun List<String>.findFirst(predicate: (Char) -> Boolean): Pair<Coord, Char> = this.findAll(predicate).first()

@JvmName("findAllListString")
fun List<String>.findAll(item: Char): Sequence<Coord> = this.findAll { it == item }.map { it.first }

@JvmName("findAllListString")
fun List<String>.findAll(predicate: (Char) -> Boolean): Sequence<Pair<Coord, Char>> = sequence {
    this@findAll.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (predicate(c)) {
                yield(Pair(Coord(x, y), c))
            }
        }
    }
}

@JvmName("findAllListCharArray")
fun List<CharArray>.findAll(predicate: (Char) -> Boolean): Sequence<Pair<Coord, Char>> = sequence {
    this@findAll.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (predicate(c)) {
                yield(Pair(Coord(x, y), c))
            }
        }
    }
}

@JvmName("findAllListCharArray")
fun List<CharArray>.findAll(item: Char): Sequence<Coord> = this.findAll { it == item }.map { it.first }

fun <T : Comparable<T>> Pair<T, T>.minMax(): Pair<T, T> =
    if (first <= second) this else second to first