package io.jwb.aoc.utils

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CoordTest {

    val start = Coord(1, 1)

    @Test
    fun `equality works`() {
        val a = Coord(0, 0)
        val b = Coord(0, 0)
        val c = Coord(1, 0)
        assertEquals(a, b)
        assertNotEquals(a, c)
    }

    @Test
    fun `neighbors makes sense`() {
        val n = start.allNeighbors

        assertEquals(8, n.size)
        listOf(
            start.north,
            start.south,
            start.east,
            start.west,
            start.northeast,
            start.northwest,
            start.southeast,
            start.southwest
        ).forEach { assertContains(n, it) }
    }
}