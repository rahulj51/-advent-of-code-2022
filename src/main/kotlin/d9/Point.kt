package d9

enum class DIRECTION {
    U, D, L, R
}

data class Point(
    val x : Int,
    val y : Int
) {

    fun adjacentInSameAxis(): List<Point> =
        listOf(
            Point(x,y),  //overlap is also considered adjacent
            Point(this.x + 1, this.y),
            Point(this.x - 1, this.y),
            Point(this.x, this.y + 1),
            Point(this.x, this.y - 1),
        )

    fun adjacentDiagonally(): List<Point> =
        listOf(
            //4 diagonals
            Point(this.x - 1 , this.y - 1),
            Point(this.x - 1 , this.y + 1),
            Point(this.x + 1 , this.y - 1),
            Point(this.x + 1 , this.y + 1)
        )

    fun isAdjacentTo(p:Point) = adjacentInSameAxis().contains(p) || adjacentDiagonally().contains(p)

    fun onSameAxis(p:Point) = this.x == p.x || this.y == p.y

    //one step
    fun up(): Point = Point(x, y+1)
    fun down(): Point = Point(x, y-1)
    fun right(): Point = Point(x+1, y)
    fun left(): Point = Point(x-1, y)

    fun moveLaterally(d: DIRECTION): Point {
        return when (d) {
            DIRECTION.R -> this.right()
            DIRECTION.L -> this.left()
            DIRECTION.U -> this.up()
            DIRECTION.D -> this.down()
        }
    }

    fun moveDiagonallyAdjacentTo(p: Point): Point {
        return this.adjacentDiagonally().filter { it.isAdjacentTo(p) }.first()
    }

    fun moveLaterallyAdjacentTo(p: Point): Point {
        return this.adjacentInSameAxis().filter { it.isAdjacentTo(p) }.first()
    }

}