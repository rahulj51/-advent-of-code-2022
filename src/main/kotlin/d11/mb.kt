package d11

import common.readInputFileAsString
import java.lang.IllegalArgumentException
import java.math.BigInteger

data class Monk(
    val items: MutableList<Long>,
    val testFactor: Long,
    val throwsTo: Pair<Int, Int>,
    val operation: ArrayDeque<String>,
    val comfortFactor: Long = 3L,
    var inspections: Int = 0
) {

    fun turn(monks: List<Monk>) {
        this.items.forEach { throwTo(it, monks) }
        this.inspections += this.items.size
        this.items.clear()
    }

    fun throwTo(item:Long, monks: List<Monk>) {
        val newWL = operate(item) / comfortFactor
        val test = newWL % testFactor == 0L

        val throwTo = if (test) throwsTo.first else throwsTo.second
        monks[throwTo].items.add(newWL)
    }

    fun operate(oldWL : Long): Long {

        val operator = operation.first()
        val operand  =  when (operation.last()) {
            "old" -> oldWL
            else -> operation.last().toLong()
        }

        val x = when (operator) {
            "*" -> oldWL * operand
            "+" -> oldWL + operand
            else -> oldWL //assume this for now to reduce complexity
        }

        if (x < 0) {
            println("pause here")
        }
        return x
    }

    companion object Parser {
        fun parse(str: String): Monk {
            val lines = str.split("\n".toRegex()).map { it.trim() }.drop(1)
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

        private fun testFactor(s: String): Long {
            return s.substringAfter("by").trim().toLong()
        }

        private fun items(s: String): List<Long> {
            return s.substringAfter(":").trim().split(",").map { it.trim().toLong() }
        }
    }


}

//just for fun, these are curious monks, not monkeys
fun main() {
    val monkNotes = readInputFileAsString("src/main/kotlin/d11/input.dat")
    val monks = monkNotes.split("\n\n".toRegex()).map { (Monk).parse(it) }
    println(monks)

    for (i in (1..20)) {
        println("in round $i")
        if (i == 16) {
            println("pause here")
        }
        monks.forEach { m -> m.turn(monks)}
        println(monks)
        println()
    }


    println(monks)
    val inspections = monks.map { it.inspections }
    println(inspections)
//    println(inspections[0] * inspections[1])

}