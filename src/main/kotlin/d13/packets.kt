package d13

import common.readInputFileAsString

fun main() {
    val input = readInputFileAsString("src/main/kotlin/d13/input.dat")

    val pairsStr = input.split("\n\n".toRegex()).map { pairStr -> pairStr.split("\n") }

    val pairs = pairsStr.map { Pair(it.first().parse(), it.last().parse()) }

//    println(compare(pairs[1]))
    println(pairs.map { compare(it) })
    println(pairs.map { compare(it) }.mapIndexed { index, b -> if(b!!) index+1 else 0 })

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
            if (left.size == 0) {
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
