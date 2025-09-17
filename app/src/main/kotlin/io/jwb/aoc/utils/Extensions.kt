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

operator fun List<CharArray>.get(loc: Coord): Char = this[loc.y][loc.x]

operator fun List<CharArray>.set(loc: Coord, item: Char) {
    this[loc.y][loc.x] = item
}

fun List<CharArray>.findFirst(item: Char): Coord = this.findAll(item).first()

fun List<CharArray>.inBounds(pos: Coord): Boolean = pos.inBounds(this[0].size, this.size)


fun List<CharArray>.findAll(item: Char): Sequence<Coord> = sequence {
    this@findAll.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (c == item) {
                yield(Coord(x, y))
            }
        }
    }
}

fun <T : Comparable<T>> Pair<T, T>.minMax(): Pair<T, T> =
    if (first <= second) this else second to first