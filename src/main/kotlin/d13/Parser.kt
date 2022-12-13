package d13

fun String.parse(): List<Any> {

    val stacks: MutableList<MutableList<Any>> = emptyList<MutableList<Any>>().toMutableList()
    var lastStack = emptyList<Any>().toMutableList()

    val split = this.split(",")
    split.forEach { str ->
        val charCollect = emptyList<Char>().toMutableList()

        str.forEach { c ->
            when (c) {
                '[' -> {
                    stacks.add(emptyList<String>().toMutableList())
                    lastStack = stacks.last()
                }
                ']' -> {
                    val str = charCollect.joinToString("")
                    if (str.isNotEmpty()) {
                        lastStack.add(str.toInt())
                        charCollect.clear()
                    }
                    if (stacks.size > 1) {
                        //sweep
                        stacks.removeLast()
                        stacks.last().add(lastStack)
                        lastStack = stacks.last()
                    }
                }
                else -> charCollect.add(c)
            }
        }
        //if there is anything in charCollect, it should go to the stack
        val str = charCollect.joinToString("")
        if (str.isNotEmpty()) {
            lastStack.add(str.toInt())
        }
        charCollect.clear()
    }

    return stacks.first().toList()
}