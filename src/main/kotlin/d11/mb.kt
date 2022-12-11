package d11

import common.readInputFileAsString
import java.lang.IllegalArgumentException
import java.math.BigInteger

fun Long.toBigInteger() = BigInteger.valueOf(this)
fun Int.toBigInteger() = BigInteger.valueOf(toLong())

data class Monk(
    val items: MutableList<BigInteger>,
    val testFactor: BigInteger,
    val throwsTo: Pair<Int, Int>,
    val operation: ArrayDeque<String>,
    val comfortFactor: BigInteger = 3.toBigInteger(),
    var inspections: BigInteger = 0.toBigInteger()
) {

    var bigMod = 0.toBigInteger()
    fun turn(monks: List<Monk>) {
        this.items.forEach { throwTo(it, monks) }
        this.inspections += this.items.size.toBigInteger()
        this.items.clear()
    }

    fun throwTo(item:BigInteger, monks: List<Monk>) {

        if (bigMod == 0.toBigInteger())
            bigMod = monks.fold(1.toBigInteger()) { acc, it -> acc * it.testFactor }

        val newWL = (operate(item) / comfortFactor) % bigMod
        val test = (newWL % testFactor) == 0L.toBigInteger()

        val throwTo = if (test) throwsTo.first else throwsTo.second
        monks[throwTo].items.add(newWL)
    }

    fun operate(oldWL : BigInteger): BigInteger {

        val operator = operation.first()
        val operand: BigInteger  =  when (operation.last()) {
            "old" -> oldWL
            else -> operation.last().toLong().toBigInteger()
        }

        val x = when (operator) {
            "*" -> oldWL * operand
            "+" -> oldWL + operand
            else -> oldWL //assume this for now to reduce complexity
        }

        return x
    }

    companion object Parser {
        fun parse(str: String, comfortFactor: BigInteger=3.toBigInteger()): Monk {
            val lines = str.split("\n".toRegex()).map { it.trim() }.drop(1)
            val items = items(lines[0]).toMutableList()
            val testFactor = testFactor(lines[2])
            val throwsTo = throwsTo(lines.subList(3,5))
            val operation = operation(lines[1])

            return Monk(items, testFactor, throwsTo, operation, comfortFactor)
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

        private fun testFactor(s: String): BigInteger {
            return s.substringAfter("by").trim().toLong().toBigInteger()
        }

        private fun items(s: String): List<BigInteger> {
            return s.substringAfter(":").trim().split(",").map { it.trim().toLong().toBigInteger() }
        }
    }
}

fun solve1(monkNotes: String) {
    val monks = monkNotes.split("\n\n".toRegex()).map { (Monk).parse(it) }

    for (i in (1..20)) {
        monks.forEach { m -> m.turn(monks)}
    }
    val inspections = monks.map { it.inspections }.sortedDescending()
    println(inspections)
    println(inspections[0] * inspections[1])
}

fun solve2(monkNotes: String) {
    val monks = monkNotes.split("\n\n".toRegex()).map { (Monk).parse(it, 1L.toBigInteger()) }

    for (i in (1..10000)) {
        monks.forEach { m -> m.turn(monks)}
    }
    val inspections = monks.map { it.inspections }.sortedDescending()
    println(inspections)
    println(inspections[0] * inspections[1])
}

//just for fun, these are curious monks, not monkeys
//solved with help from
// https://www.reddit.com/r/adventofcode/comments/zifqmh/comment/izs3pfj/
// Also see coprimes and chinese remainder theorem (appaently not relevant here but still interesting
fun main() {
    val monkNotes = readInputFileAsString("src/main/kotlin/d11/input.dat")
    solve1(monkNotes)
    solve2(monkNotes)
}

