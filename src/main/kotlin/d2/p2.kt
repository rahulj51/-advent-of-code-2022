package d2

import java.io.File

val lookup2 = hashMapOf<String, String>("A" to "R",
    "X" to "X",
    "B" to "P",
    "Y" to "Y",
    "C" to "S",
    "Z" to "Z"
)

fun translate2(pair: List<String>): List<String> {
    return pair.map { lookup2.get(it)!! }
}


fun score2(round: List<String>): Int {
    val elveShape = round.first()

    val checkRules = rules.filterKeys { it.contains(elveShape) }

    val myShape = when (round.last()) {
        "X" -> checkRules.filterValues { it == elveShape }.keys.first().filter { it != elveShape }.first()   //lose so pick the one where elve wins
        "Y" -> elveShape
        "Z" -> checkRules.filterValues { it != elveShape  }.keys.first().filter { it != elveShape }.first()   //win so pick the other value
        else -> "D"
    }

    val shapeScore = shapes.indexOf(myShape)

    val outcomeScore = when {
        elveShape == myShape -> 3
        elveShape == rules.get(setOf(elveShape, myShape)) -> 0
        myShape   == rules.get(setOf(elveShape, myShape)) -> 6
        else -> 0
    }

    return shapeScore + outcomeScore

}

fun main() {
    val l = readInputFile()
        .map { it.split("\\s".toRegex()) } //split on whitespasce for each row
        .map { translate2(it) } //translate to RPS
    val finalScore = l.map { score2(it) }.sum()
    println(finalScore)

}

//https://todd.ginsberg.com/post/advent-of-code/2022/day2/ for easy solution