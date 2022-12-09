package d9

data class Rope (
    val knots: List<Point>
) {

    fun move(d: DIRECTION) : Rope {
        val newHPos = knots.first().moveLaterally(d)

        val newRope = knots.drop(1).fold(listOf<Point>(newHPos).toMutableList()) { acc, it ->
            acc.add(moveTail(acc.last(), it))
            acc
        }

        return Rope(newRope)
    }

    fun moveTail(head:Point, tail:Point): Point {
        var newTail = tail

        //t should move to be adjacent if not already adjacent
        if (! tail.isAdjacentTo(head)) {
            //move
            newTail = if (tail.onSameAxis(head)) tail.moveLaterallyAdjacentTo(head) else tail.moveDiagonallyAdjacentTo(head)
        }

        return newTail

    }

}


fun move(mv: String, current: Rope): List<Rope> {

    val direction = DIRECTION.valueOf(mv.substringBefore(" ").trim())
    val times = mv.substringAfter(" ").trim().toInt()

    //move N steps
    val allSteps = (1..times).fold(listOf(current).toMutableList()) { acc, it ->
        acc.add(acc.last().move(direction))
        acc
    }

    return allSteps
}


fun solve(moves: List<String>, knots:Int = 2) {
    val ropeKnots = (1..knots).map { Point(0,0) }
    val initialRopePosition = Rope(ropeKnots)

    val allSteps = moves
        .fold(listOf(initialRopePosition).toMutableList()){acc, it ->
            acc.addAll(move(it, acc.last())); acc
        }

    println(allSteps.map { it.knots.last() }.toSet().count())
}

fun solveP2(moves: List<String>) {
    solve(moves, 10)
}


fun solveP1(moves: List<String>) {
    solve(moves, 2)
}

fun main() {
    val moves = common.readInputFile("src/main/kotlin/d9/input.dat")
    solveP1(moves)
    solveP2(moves)
}

