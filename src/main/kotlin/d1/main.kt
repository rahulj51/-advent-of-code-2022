package d1

import java.io.File
import java.nio.file.Paths

fun readFile(f: String): String {
    return File(f).readText()
}
fun readInputFile(): String {
    return readFile("src/main/kotlin/d1/input.dat")
}

fun parseToCaloriesPerElf(input: String): List<Int> =
    input
        .split("\n\n")
        .map { it.lines().sumOf(String::toInt) }


fun p1() {
    val caloriesPerElf = parseToCaloriesPerElf(readInputFile())
    println(caloriesPerElf.sortedDescending().first())
}

fun p2() {
    val caloriesPerElf = parseToCaloriesPerElf(readInputFile())
    println(caloriesPerElf.sortedDescending().take(3).sum())
}


fun main() {
    p1()
    p2()
}