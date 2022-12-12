package d12

import common.readInputFile

typealias Path = List<Point>

data class Point(
    val x : Int,
    val y : Int
) {
    fun up(): Point = Point(x, y + 1)
    fun down(): Point = Point(x, y - 1)
    fun right(): Point = Point(x + 1, y)
    fun left(): Point = Point(x - 1, y)
}


data class Grid(
    val map: List<List<Char>>,
    val start: Point,
    val end: Point
) {

    fun initPath(): Path = listOf(start)

    fun elevationAt(p: Point): Char {
        //note that y denotes the row since we store each row as a list
        return map[p.y][p.x]
    }

    fun walk(paths: List<Path>): List<Path> {

        if (paths.any { it.contains(end) }) {
            //we have traversed everything
            return paths
        } else {
            val paths1 = paths.flatMap { next(it) }
//            println(paths1)
            return walk(paths1)
        }
    }

    fun next(path: List<Point>): List<Path> {
        val weAreAt = path.last()
        val possibleNexts = possibleNexts(weAreAt)

        val pN = possibleNexts
            .filter { ! path.contains(it) } //also exclude a point that's already visited
            .map { path + it }
        return pN
    }

    private fun possibleNexts(at: Point): Set<Point> {

        val allNexts = setOf<Point>(at.up(), at.down(), at.left(), at.right()).filter { withinBounds(it) }
        return allNexts.filter { p -> (elevationAt(p) - elevationAt(at) <= 1) }.toSet()
    }

    private fun withinBounds(p:Point): Boolean {
        return p.x >= 0 && p.x <= map.first().size - 1  //x axis is within bounds
                && p.y >=0 && p.y <= map.size -1 //y axis is within bounds
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

            return Grid(mapWithoutMarkers, Point(startX, startY), Point(endX, endY))
        }
    }

}

fun main() {
    val lines = readInputFile("src/main/kotlin/d12/input.dat")
//    println(lines)
    val g = Grid.of(lines)
//    println(g)
    println(g.walk(listOf(g.initPath())).map { it.size }.sorted().first() - 1)

}
