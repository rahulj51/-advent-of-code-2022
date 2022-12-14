package d14

import common.readInputFile

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
    var occupied: MutableSet<Point>
) {
    val sandSource = Point(500,0)
    val minX = occupied.minBy { it.x }.x
    val maxX = occupied.maxBy { it.x }.x
    val maxY = occupied.maxBy { it.y }.y

    fun outOfBoundsP1(p: Point): Boolean {
        return (p.x < minX || p.x > maxX || p.y > maxY)
    }

    fun outOfBoundsP2(p: Point): Boolean {
        return (p.y + 1 == maxY + 2)
    }

    fun uptoTop(): Boolean {
        val xxx = listOf<Point>(sandSource, sandSource.leftDown(), sandSource.rightDown(), sandSource.down())
        return occupied.containsAll(xxx)
    }

    fun dropSandP2(from: Point = sandSource): Point? {

        if (uptoTop()) return null

        val possibles = listOf<Point>(from.down(), from.leftDown(), from.rightDown())
        if (occupied.containsAll(possibles) || outOfBoundsP2(from)) {
            //can't go any further
            occupied.add(from)
            return from
        } else {
            val next = possibles.filterNot { occupied.contains(it) }.first()
            return dropSandP2(next)
        }
    }

    fun dropSand(from: Point = sandSource): Point? {

        if (outOfBoundsP1(from)) return null

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



    companion object {
        fun of(lines: List<String>): Cave {
            val points = lines.flatMap{ l ->
                l.split("->")
                    .map { p -> Point.of(p) }
                    .windowed(2).flatMap { s -> s.first().to(s.last()) }

            }.toMutableSet()

            return Cave(points)

        }

    }
}

fun solve1(lines: List<String>) {
    val cave = Cave.of(lines)
    var sandPos = cave.dropSand()
    var sandUnit = 1

    while (sandPos != null) {
        sandPos = cave.dropSand()
        sandUnit += 1
    }

    println(sandUnit - 1)
}

fun solve2(lines: List<String>) {
    val cave = Cave.of(lines)
    var sandPos = cave.dropSandP2()
    var sandUnit = 1

    while (sandPos != null) {
        sandPos = cave.dropSandP2()
        sandUnit += 1
    }

    println(sandUnit - 1)
}

fun main() {
    val lines = readInputFile("src/main/kotlin/d14/input.dat")
    solve1(lines)
    solve2(lines)
}

