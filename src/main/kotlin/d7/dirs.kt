package d7

class Node (val name:String,
                 var size:Int=0,
                 val contents: MutableSet<Node> = mutableSetOf<Node>(),
                 val parent: Node? = null) {

    fun add(str: String): Node {

        when {
            str.startsWith("dir") -> this.contents.add(Node(str.substringAfter(" "), parent=this))
            else -> this.contents.add(Node(str.substringAfter(" "),
                size = str.substringBefore(" ").toInt(),
                parent=this))
        }

        return this
    }

    fun size(): Int {
        return if (this.contents.isEmpty()) this.size else this.contents.fold(0){ sum, it -> sum + it.size() }
    }

    fun sizeAtmost(maxSize:Int): List<Node> {
        val onlyDirs = this.contents.filter { it.contents.isNotEmpty() }

        return onlyDirs.filter { it.size() <= maxSize } + onlyDirs.flatMap { it.sizeAtmost(maxSize)  }
    }

    fun sizeAtleast(minSize: Int): List<Node> {
        val onlyDirs = this.contents.filter { it.contents.isNotEmpty() }
        return onlyDirs.filter { it.size() >= minSize } + onlyDirs.flatMap { it.sizeAtleast(minSize)  }
    }

}


fun solve1(root: Node){
    val summ = root.sizeAtmost(100000).sumOf { it.size() }
    println(summ)

}

fun solve2(root: Node){
    val totalSpace = 70000000
    val unused = totalSpace - root.size()
    val deficit = 30000000 - unused
    println(root.sizeAtleast(deficit).minBy { it.size() }.size())

}


fun main() {
    val terminal = common.readInputFile("src/main/kotlin/d7/input.dat")
    val root = Node("/")

    terminal.drop(1).fold(root) { tree, it -> parse(it, tree) }

    solve1(root)
    solve2(root)

}

fun parse(str : String, tree: Node): Node {

    val tree = when {
        str.startsWith("$ ls") -> tree
        str.startsWith("$ cd") -> cd(tree, str)
        else -> tree.add(str)
    }
    return tree
}

fun cd(tree: Node, str: String): Node {

    //change directory
    val dir = str.substringAfter("cd ")
    return when (dir) {
        ".." ->  tree.parent!!
        else -> tree.contents.first { it -> it.name == dir }
    }
}


