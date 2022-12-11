package d11

import common.readInputFileAsString
import java.lang.IllegalArgumentException

data class Monkey(
    val items: MutableList<Int>,
    val testFactor: Int,
    val throwsTo: Pair<Int, Int>,
    val operation: ArrayDeque<String>
) {

    companion object Parser {
        fun parse(str: String): Monkey {
            val lines = str.split("\n".toRegex()).map { it.trim() }.drop(1)
            println(lines)
            val items = items(lines[0]).toMutableList()
            val testFactor = testFactor(lines[2])
            val throwsTo = throwsTo(lines.subList(3,5))
            val operation = operation(lines[1])

            return Monkey(items, testFactor, throwsTo, operation)

        }

        private fun operation(s: String): ArrayDeque<String> {

            val op = s.substringAfter("=").trim()
            val stack = ArrayDeque<String>()
            stack.addAll(op.split(" ").map { it.trim() })
            return stack
        }

        private fun throwsTo(l: List<String>): Pair<Int,Int> {
            val pair = l.fold(Pair(0,0)) { acc, it ->
                when {
                    it.contains("true") -> acc.copy(first = it.substringAfter("monkey").trim().toInt())
                    it.contains("false") -> acc.copy(second = it.substringAfter("monkey").trim().toInt())
                    else -> throw IllegalArgumentException("illegal")
                }
            }

            return pair
        }

        private fun testFactor(s: String): Int {
            return s.substringAfter("by").trim().toInt()
        }

        private fun items(s: String): List<Int> {
            return s.substringAfter(":").trim().split(",").map { it.trim().toInt() }
        }


    }


}


fun main() {
    val monkeynotes = readInputFileAsString("src/main/kotlin/d11/input.dat")
    val monks = monkeynotes.split("\n\n".toRegex()).map { (Monkey).parse(it) }
    println(monks)

}