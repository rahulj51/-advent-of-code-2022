package d14

import common.readInputFile
import kotlin.math.sign

data class Point (
    val x : Int,
    val y : Int
) {

    fun down() : Point  = this.copy(x, y+1)
    fun leftDown() : Point  = this.copy(x-1, y+1)
    fun rightDown() : Point  = this.copy(x+1, y+1)

    fun to(other: Point): List<Point> {

        val (start, end) = if (this.x < other.x || this.y < other.y) Pair(this, other) else Pair(other, this)
        val points = emptyList<Point>().toMutableList()
        for (x in start.x..end.x) {
            for (y in start.y..end.y ) {
                points.add(Point(x,y))
            }
        }
        return points.toList()
    }

    companion object {
        fun of(str: String): Point {
            return Point(str.substringBefore(",").trim().toInt(),
                str.substringAfter(",").trim().toInt())
        }
    }
}

data class Cave(
    val rocks: List<Point>
) {
    val sandSource = Point(500,1)
    var occupied = mutableListOf<Point>().apply { addAll(rocks) }

    fun outOfBounds(p: Point): Boolean {
        val minX = rocks.first().x
        val maxX = rocks.last().x
        return (p.x < minX || p.x > maxX)
    }

    fun dropSand(from: Point = sandSource): Point? {

        if (outOfBounds(from)) return null

        val possibles = listOf<Point>(from.down(), from.leftDown(), from.rightDown())
        if (occupied.containsAll(possibles)) {
            //can't go any further
            occupied.add(from)
            return from
        } else {
            val next = possibles.filterNot { occupied.contains(it) }.first()
            return dropSand(next)
        }
    }

    fun dropSand2(from: Point = sandSource): Point? {

        var curr = from
        var possibles = listOf<Point>(curr.down(), curr.leftDown(), curr.rightDown())
        while (! occupied.containsAll(possibles)) {
            //keep flowing down
            if (outOfBounds(curr)) {
                return null
            } else {
                curr = possibles.filterNot { occupied.contains(it) }.first()
                possibles = listOf<Point>(curr.down(), curr.leftDown(), curr.rightDown())
            }
        }
        occupied.add(curr)
        return curr
    }


    companion object {
        fun of(lines: List<String>): Cave {
            val points = lines.flatMap{ l ->
                l.split("->")
                    .map { p -> Point.of(p) }
                    .windowed(2).flatMap { s -> s.first().to(s.last()) }

            }.toSet().sortedBy { it.x }

            return Cave(points)

        }

    }
}

fun main() {
    val lines = readInputFile("src/main/kotlin/d14/input.dat")
    val cave = Cave.of(lines)
    println(cave)

    var sandPos = cave.dropSand2()
    var sandUnit = 1
    while (sandPos != null) {
        sandPos = cave.dropSand2()
        sandUnit += 1
        println(sandUnit)
    }

    println(sandUnit - 1)


//    val x = "498,4 -> 498,6 -> 496,6"
//    val z = x.split("->").map { p -> Point.of(p) }.windowed(2)
//    println (z)
//    println(z.flatMap { s -> s.first().to(s.last()) })

//    val start = Point(10,18)
//    val end = Point (10,18)
//    start.to(end)
}

