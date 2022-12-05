package d5


fun main() {
//[D] [W] [W] [F] [T] [H] [Z] [W] [R]
    val x = "[D] [W] [W] [F] [T] [H] [Z] [W] [R]"

    val indices = (0..32 step 4)
    println(indices.toList())

    val stacks = List(9){ mutableListOf<String>()}

    indices.forEach{ index -> stacks[index.div(4)].add(x.substring(index+1, index+2).trim())}

    val y = "[F]     [N] [D]     [L]     [S] [W]"

    indices.forEach{ index -> stacks[index.div(4)].add(y.substring(index+1, index+2).trim())}

    val z = "[W]     [J] [L]             [J] [V]"

    indices.forEach{ index -> stacks[index.div(4)].add(z.substring(index+1, index+2).trim())}

    println(stacks)
    val ss = stacks.map { it -> it.filterNot { it.isEmpty() } }
    println(ss)


    val pattern = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
    val str = "move 1 from 2 to 1"
    val matchResult = pattern.matchEntire(str)
    print(matchResult?.groupValues?.drop(1))

}
