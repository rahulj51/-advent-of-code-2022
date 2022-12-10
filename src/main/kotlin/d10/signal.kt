package d10

import common.readInputFile

fun MutableList<Int>.noop() : MutableList<Int> {
    val last = this.last()
    this.add(last)
    return this
}

fun MutableList<Int>.addVal(value: Int) : MutableList<Int> {
    val last = this.last()
    this.add(last+value)
    return this
}

fun MutableList<Int>.addX(value: Int) : MutableList<Int> {
    return this.noop().addVal(value)
}

fun main() {
    val instructions = readInputFile("src/main/kotlin/d10/input.dat")

    val cycles = instructions.fold(listOf(1).toMutableList()){ acc, it ->
        when{
            it.trim() == "noop" -> acc.noop()
            it.trim().startsWith("addx") -> acc.addX(it.trim().substringAfter(" ").toInt())
        }
        acc
    }

//    println(cycles)

    val strength = (20..220 step 40).fold(0){ acc, it -> acc + it * cycles[it-1]}
    println(strength)
}