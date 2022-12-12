package d12

import common.readInputFile

data class Point(
    val x : Int,
    val y : Int,
    val e : Char
)

data class Grid(
    val map: List<List<Point>>,
    val start: Point,
    val end: Point
) {

    fun tracePathToEnd(path: Map<Point, Point>, at:Point): List<Point> {
        val prev = path[at]

        return when (prev) {
            null -> listOf(at)
            else -> tracePathToEnd(path, prev) + at
        }
    }

    fun findPath(): List<Point> {

        //using bfs

        val queue = mutableListOf<Point>()
        queue.add(start)
        val visited = mutableSetOf<Point>()
        visited.add(start)
        val path = mutableMapOf<Point, Point>()

        while (queue.isNotEmpty()) {
            val at = queue.removeLast()

            if (at == end) break

            possibleNexts(at)
                .filter { ! visited.contains(it) }
                .forEach {
                    queue.add(0, it)
                    visited.add(it)
                    path[it] = at
                }
        }

        return tracePathToEnd(path, end)
    }

    private fun up(p: Point): Pair<Int,Int> = Pair(p.x, p.y-1)
    private fun down(p: Point): Pair<Int,Int> = Pair(p.x, p.y+1)
    private fun left(p: Point): Pair<Int,Int> = Pair(p.x-1, p.y)
    private fun right(p: Point): Pair<Int,Int> = Pair(p.x+1, p.y)

    private fun possibleNexts(at: Point): Set<Point> {

        return setOf(up(at), down(at), left(at), right(at)).mapNotNull { xy ->
            when {
                xy.first < 0 || xy.second < 0 -> null
                xy.first >= map.first().size || xy.second >= map.size -> null
                else -> map[xy.second][xy.first]
            }
        }.filter { it.e - at.e <= 1 }.toSet()
    }

    companion object {

        fun of(lines: List<String>): Grid {

            val map = lines.map { it.toCharArray().toList() }
            val startY = map.indexOfFirst { it.contains('S') }
            val startX = map[startY].indexOf('S')
            val endY = map.indexOfFirst { it.contains('E') }
            val endX = map[endY].indexOf('E')

            //we don't need the S and E markers since we have their position
            val mapWithoutMarkers = map.map { it -> it.map { it ->
                    when (it) {
                        'E' -> 'z'
                        'S' -> 'a'
                        else -> it
                    }
                }
            }

            val points = mapWithoutMarkers.mapIndexed { j, it -> it.mapIndexed {i, c ->  Point(i,j,c) } }

            return Grid(points, Point(startX, startY, 'a'), Point(endX, endY, 'z'))
        }

    }
}

fun solve1(grid: Grid) {
    println(grid.findPath().count() - 1)
}

fun solve2(grid: Grid) {

    val allAs = grid.map.flatMap { it.filter { p -> p.e == 'a' } }
    val allGrids = allAs.map { grid.copy(start = it) }
    val allDistances = allGrids.map { it.findPath().count() - 1 }
    println(allDistances.filter{it != 0}.min())
}


fun main() {
    val lines = readInputFile("src/main/kotlin/d12/input.dat")
    val grid = Grid.of(lines)

    solve1(grid)

    solve2(grid)

}

