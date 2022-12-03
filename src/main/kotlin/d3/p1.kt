package d3

import java.io.File

fun readFile(f: String): List<String> {
    return File(f).readLines()
}

fun readInputFile(): List<String> {
    return readFile("src/main/kotlin/d3/input.dat")
}

val alphabets = ('a'..'z')

fun main() {
    val l = readInputFile()
    val compartments = l.map { listOf(it.take(it.length / 2), it.drop(it.length/2)) }
    val common = compartments.map { it.first().toSet().intersect(it.last().toSet())  }.flatMap { it }

    val summ = common.map { it ->
        when {
            alphabets.contains(it) -> alphabets.indexOf(it) + 1
            else -> alphabets.indexOf(it.lowercaseChar()) + 27
        }
    }.sum()

    println(summ)
}
