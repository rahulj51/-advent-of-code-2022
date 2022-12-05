package d5

import common.readInputFileAsString


fun stacks(stacksStr:String): List<MutableList<Char>> {
    val stackLines = stacksStr.split("\n").reversed()

    val numberOfStacks = stackLines.first().split("\\s".toRegex()).last().toInt()

    val stacks = List(numberOfStacks){ mutableListOf<Char>()}

    stackLines.drop(1). forEach { stackLine ->
        (0..stackLine.length-3 step 4).forEach {
                index -> stacks[index.div(4)].add(stackLine.substring(index + 1, index + 2).first())
        }
    }

    return stacks.map { it -> it.filter { it.isLetter() }.toMutableList() }
}

fun move9000(moveLine:String, stacks:List<MutableList<Char>>) {

    val pattern = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
    val (crates, from, to) = pattern.matchEntire(moveLine)!!.groupValues!!.drop(1).map { it.toInt() }

    for (i in (1..crates.toInt())) {

        stacks[to - 1].add(stacks.get(from-1).last())
        stacks[from -1].removeLast()
    }
}

fun move9001(moveLine:String, stacks:List<MutableList<Char>>) {

    val pattern = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
    val (crates, from, to) = pattern.matchEntire(moveLine)!!.groupValues!!.drop(1).map { it.toInt() }

    stacks[to-1].addAll(stacks[from-1].takeLast(crates))
    for (i in (1..crates.toInt())) { stacks[from -1].removeLast() }

}

fun solve1(moveLines: List<String>, stacks:List<MutableList<Char>>) {
    moveLines.forEach { it -> move9000(it, stacks) }
    println(stacks.map { it.last() }.joinToString(""))
}

fun solve2(moveLines: List<String>, stacks:List<MutableList<Char>>) {
    moveLines.forEach { it -> move9001(it, stacks) }
    println(stacks.map { it.last() }.joinToString(""))
}



fun main() {
    val input = readInputFileAsString("src/main/kotlin/d5/input.dat")

    val stacksStr = input.split("\n\n".toRegex()).first()
    var stacks = stacks(stacksStr)
    val moveLines = input.split("\n\n".toRegex()).last().split("\n".toRegex())

    solve1(moveLines, stacks)

    //reset
    stacks = stacks(stacksStr)
    solve2(moveLines, stacks)

}