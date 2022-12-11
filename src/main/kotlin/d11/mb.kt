package d11

import common.readInputFileAsString
import java.lang.IllegalArgumentException

data class Monk(
    val items: MutableList<Int>,
    val testFactor: Int,
    val throwsTo: Pair<Int, Int>,
    val operation: ArrayDeque<String>,
    val comfortFactor: Int = 3
) {

    fun turn(monks: List<Monk>) {
        this.items.forEach { throwTo(it, monks) }
        this.items.clear()
    }

    fun throwTo(item:Int, monks: List<Monk>) {
        val newWL = operate(item) / comfortFactor
        val test = newWL % testFactor == 0

        val throwTo = if (test) throwsTo.first else throwsTo.second
        monks[throwTo].items.add(newWL)
    }

    fun operate(oldWL : Int): Int {

        val operator = operation.first()
        val operand  =  when (operation.last()) {
            "old" -> oldWL
            else -> operation.last().toInt()
        }

        return  when (operator) {
            "*" -> oldWL * operand
            "+" -> oldWL + operand
            else -> oldWL //assume this for now to reduce complexity
        }
    }

    companion object Parser {
        fun parse(str: String): Monk {
            val lines = str.split("\n".toRegex()).map { it.trim() }.drop(1)
            println(lines)
            val items = items(lines[0]).toMutableList()
            val testFactor = testFactor(lines[2])
            val throwsTo = throwsTo(lines.subList(3,5))
            val operation = operation(lines[1])

            return Monk(items, testFactor, throwsTo, operation)

        }

        private fun operation(s: String): ArrayDeque<String> {

            val op = s.substringAfter("=").trim()
            val stack = ArrayDeque<String>()
            stack.addAll(op.split(" ").map { it.trim() }.drop(1))
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

//just for fun, these are curious monks, not monkeys
fun main() {
    val monkNotes = readInputFileAsString("src/main/kotlin/d11/input.dat.smol")
    val monks = monkNotes.split("\n\n".toRegex()).map { (Monk).parse(it) }
    println(monks)

    for (i in (1..20)) {
        monks.forEach { m -> m.turn(monks)}
    }


    println(monks)

}