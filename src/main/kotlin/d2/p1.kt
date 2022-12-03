package d2

import java.io.File

val shapes = listOf("", "R", "P", "S")

//hashhamp of ABC ->
val lookup = hashMapOf<String, String>("A" to "R",
    "X" to "R",
    "B" to "P",
    "Y" to "P",
    "C" to "S",
    "Z" to "S"
)

val rules = hashMapOf<Set<String>, String>(setOf<String>("R","P") to "P",
    setOf<String>("R", "S") to "R",
    setOf<String>("P", "S") to "S"
)


fun translate(pair: List<String>): List<String> {
    return pair.map { lookup.get(it)!! }
}

fun score(round: List<String>): Int {
    val elveShape = round.first()
    val myShape = round.last()
    val shapeScore = shapes.indexOf(myShape)

    val outcomeScore = when {
        elveShape == myShape -> 3
        elveShape == rules.get(round.toSet()) -> 0
        myShape   == rules.get(round.toSet()) -> 6
        else -> 0
    }

    return shapeScore + outcomeScore

}

fun readFile(f: String): List<String> {
    return File(f).readLines()
}

fun readInputFile(): List<String> {
    return readFile("src/main/kotlin/d2/input.dat")
}

fun main() {
    val l = readInputFile()
        .map { it.split("\\s".toRegex()) } //split on whitespasce for each row
        .map { translate(it) } //translate to RPS

    val finalScore = l.map { score(it) }.sum()
    print(finalScore)

}