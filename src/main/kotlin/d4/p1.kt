package d4
import common.*
import kotlin.math.*

fun main() {

    val l = readInputFile("src/main/kotlin/d4/input.dat")

    val y = l
        .map { it.split(",") }
        .map { it -> it.map { it ->
        val range_limits = it.split("-")
        (range_limits.first().toInt()..range_limits.last().toInt())

    } }

    val z = y.map {
        val overlap = min(it.first().last, it.last().last) - max(it.first().start, it.last().start) + 1
        it.first().count() == overlap || it.last().count() == overlap
    }

//    println(z)
    println(z.filter { it == true }.count())

    val z2 = y.map {
        val overlap = min(it.first().last, it.last().last) - max(it.first().start, it.last().start) + 1
        overlap > 0
    }

    println(z2.filter { it == true }.count())

}