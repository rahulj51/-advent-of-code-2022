package d8

typealias FinderFunction<T> = (Int, List<Int>) -> T
typealias MergeFunction<T> = (T, T) -> T
typealias Point = Pair<Int, Int>


fun isVisible(loc: Int, row: List<Int>): Boolean {
    val treeHeight = row[loc]

    return row.subList(0, loc).all{ it < treeHeight} ||
            row.subList(loc+1, row.size).all{ it < treeHeight }
}

fun scenicScore(loc:Int, row: List<Int>): Int {

    val treeHeight = row[loc]

    //ugly but works. need to see better alternative
    var beforeVisible = row.subList(0, loc).reversed().indexOfFirst { it >= treeHeight } + 1
    beforeVisible = if (beforeVisible == 0) row.subList(0, loc).size  else beforeVisible
    var afterVisible = row.subList(loc+1, row.size).indexOfFirst { it >= treeHeight } + 1
    afterVisible = if (afterVisible == 0) row.subList(loc+1, row.size).size else afterVisible

    return beforeVisible * afterVisible
}

private fun<T> solve(map: List<List<Int>>,
                     finderFunction: FinderFunction<T>,
                     mergeFunction: MergeFunction<T>) : Map<Point, T> {

    val startx = 0
    val starty = 0
    val endx = map.size - 1
    val endy = map.first().size - 1

    val rowWiseScore = (startx..endx).map { x ->
        (starty..endy).fold(mutableMapOf<Point, T>()) { acc, y ->
            acc.put(Pair(x, y), finderFunction(y, map[x]))
            acc
        }
    }.fold(mutableMapOf<Point, T>()) { acc, it -> acc.putAll(it); acc }

    val colWiseScore = (startx..endx).map { x ->
        (starty..endy).fold(mutableMapOf<Point, T>()) { acc, y ->
            acc.put(Pair(x, y), finderFunction(x, map.map { it[y] }))
            acc
        }
    }.fold(mutableMapOf<Point, T>()) { acc, it -> acc.putAll(it); acc }

    //merge the maps
    return (rowWiseScore.keys + colWiseScore.keys).associateWith {
        mergeFunction(rowWiseScore[it]!!, colWiseScore[it]!!)
    }

}

fun main() {
    val lines = common.readInputFile("src/main/kotlin/d8/input.dat")

    val map = lines.map { it.map { c -> c.digitToInt() }}

    //first
    println(solve(map, ::isVisible, {v1, v2 -> v1 || v2}).filter { it.value == true }.size)

    //second
    println(solve(map, ::scenicScore, {v1, v2 -> v1 * v2}).maxBy { it.value}.value)
}









