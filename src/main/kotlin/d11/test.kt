package d11

import java.lang.IllegalArgumentException

fun main() {
    val l = listOf<String>("If true: throw to monkey 2", "If false: throw to monkey 3")
    val pair = l.fold(Pair(0,0)) { acc, it ->
        when {
            it.contains("true") -> acc.copy(first = it.substringAfter("monkey").trim().toInt())
            it.contains("false") -> acc.copy(second = it.substringAfter("monkey").trim().toInt())
            else -> throw IllegalArgumentException("illegal")
        }
    }

    println(pair)
}