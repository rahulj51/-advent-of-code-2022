package d3

import java.io.File


fun main() {
    val l = readInputFile("src/main/kotlin/d3/input.dat")
    val compartments = l.chunked(3)
    val common = compartments.map {
        it.fold(it.first().toSet()){matching, item -> item.toSet().intersect(matching) }
    }.flatMap { it }

    val summ = common.map { it ->
        when {
            alphabets.contains(it) -> alphabets.indexOf(it) + 1
            else -> alphabets.indexOf(it.lowercaseChar()) + 27
        }
    }.sum()

    println(summ)
}
