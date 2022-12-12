package d12

import common.readInputFile
import java.io.File

data class Point(
    val x : Int,
    val y : Int,
    val e : Char
)
fun Point.isValid(other: Point) = this.e + 1 >= other.e

sealed class Direction(val x: Int, val y: Int) {
    object Up : Direction(0, -1)
    object Down : Direction(0, 1)
    object Left : Direction(-1, 0)
    object Right : Direction(1, 0)
}

val directions = listOf(Direction.Up, Direction.Down, Direction.Left, Direction.Right)

data class Grid(
    val map: List<List<Point>>,
    val start: Point,
    val end: Point
) {

    fun Map<Point, Point>.regeneratePath(tail: Point): List<Point> {
        return when (val next = this[tail]) {
            null -> return listOf<Point>() + tail
            else -> regeneratePath(next) + tail
        }
    }
    
    fun bfs() : List<Point> {

        val rows = map.count()
        val cols = map.first().count()

        val path = mutableMapOf<Point, Point>()
        val queue = mutableListOf<Point>()
        val visited = mutableMapOf<Point, Boolean>()
        queue.add(start)
        visited[start] = true

        while (queue.isNotEmpty()) {
            val at = queue.removeLast()

            //have we reached the end
            if (at == end) break

            directions.mapNotNull { direction ->
                val newX = at.x + direction.x
                val newY = at.y + direction.y
                when {
                    newX < 0 || newY < 0 -> null
                    newY >= rows || newX >= cols -> null
                    else -> map[newY][newX]
                }
            }.filter { !visited.getOrDefault(it, false) }
                .filter { at.isValid(it)}
                .forEach { it ->
                    queue.add(0, it)
                    path[it] = at
                    visited[it] = true
                }
        }

        return path.regeneratePath(end)
    }



    companion object {
        fun of(lines: List<String>): Grid {

            val map = lines.map { it.toCharArray().toList() }
            val startY = map.indexOfFirst { it.contains('S') }
            val startX = map[startY].indexOf('S')
            val endY = map.indexOfFirst { it.contains('E') }
            val endX = map[endY].indexOf('E')

            //we don't need the S and E markers since we have their position
            val mapWithoutMarkers = map.map { it ->
                when {
                    it.contains('S') -> it.takeWhile { it != 'S' } + 'a' + it.takeLastWhile { it != 'S' }
                    it.contains('E') -> it.takeWhile { it != 'E' } + 'z' + it.takeLastWhile { it != 'E' }
                    else -> it
                }
            }

            val points = mapWithoutMarkers.mapIndexed { j, it -> it.mapIndexed {i, c ->  Point(i,j,c) } }

            return Grid(points, Point(startX, startY, 'a'), Point(endX, endY, 'z'))
        }

        fun parseInput(input: () -> List<String>): Grid {

            val map = input().map { it.toCharArray().toList() }
            val startY = map.indexOfFirst { it.contains('S') }
            val startX = map[startY].indexOf('S')
            val endY = map.indexOfFirst { it.contains('E') }
            val endX = map[endY].indexOf('E')


            val x = input().mapIndexed { y, row ->
                row.mapIndexed { x, char ->
                    when (char) {
                        'S' -> Point(x = x, y = y, 'a')
                        'E' -> Point(x = x, y = y, e = 'z')
                        else -> Point(x = x, y = y, e = char)
                    }
                }
            }

            return Grid(x, Point(startX, startY, 'a'), Point(endX, endY, 'z'))
        }
    }

}

fun main() {
//    val lines = readInputFile("src/main/kotlin/d12/input.dat.smol")
    val input = File("src/main/kotlin/d12/input.dat").readLines()
    val g = Grid.parseInput { input }
    println(g.bfs().count() - 1)

    //part2
    println(g.map.flatMap { it.filter { node -> node.e == 'a' } }
        .map { g.copy(start = it).bfs().count() - 1 }
        .filter { it != 0 }.minOf { it })

}