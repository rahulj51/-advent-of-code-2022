package d13

import common.readInputFileAsString

fun main() {
//    val input = readInputFileAsString("src/main/kotlin/d13/input.dat")
//    solve1(input)
//    solve2(input)

    val s = "[5,[6,[7,8]],10]".toMutableList()
    println(s)


}

fun solve2(input:String) {

    val divider1 = "[[2]]"
    val divider2 = "[[6]]"
    val packetsStr = input.split("\n\n".toRegex()).flatMap { pairStr -> pairStr.split("\n") } + divider1 + divider2
    val packets = packetsStr.map { it.parse()}

    val sorted = packets.sortedWith(Comparator { o1, o2 ->
        when (compare(o1,o2)) {
            null -> 0
            true -> -1
            false -> 1
        }
    })

    println((sorted.indexOf(divider1.parse())+1) * (sorted.indexOf(divider2.parse())+1))


}


fun solve1(input: String) {
    val pairsStr = input.split("\n\n".toRegex()).map { pairStr -> pairStr.split("\n") }

    val pairs = pairsStr.map { Pair(it.first().parse(), it.last().parse()) }

//    println(compare(pairs[1]))
//    println(pairs.map { compare(it) })
//    println(pairs.map { compare(it) }.mapIndexed { index, b -> if(b!!) index+1 else 0 })
    println(pairs.map { compare(it) }.mapIndexed { index, b -> if(b!!) index+1 else 0 }.sum())
}


fun compare(left: List<Any>, right:List<Any>): Boolean? {
    return compareVal(left, right)
}

fun compare(pair: Pair<List<Any>, List<Any>>): Boolean? {
    val left = pair.first
    val right = pair.second

    return compareVal(left, right)
}

fun compareVal(left: Any?, right: Any?): Boolean? {

    return when {
        left is Int && right is Int -> if (left == right) null else left < right
        left is List<*> && right is List<*> ->
            if (left.size == 0 && right.size == 0) {
                null
            }
            else if (left.size == 0) {
                true
            } else if (right.size == 0) {
                false
            } else {
                compareVal(left.first(), right.first()) ?: compareVal(left.drop(1), right.drop(1))

            }
        left is Int && right is List<*> -> compareVal(listOf(left), right)
        left is List<*> && right is Int -> compareVal(left, listOf(right))
        else -> false
    }

}
