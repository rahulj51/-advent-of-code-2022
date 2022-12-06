package d6

fun main() {
    val datastream = common.readInputFileAsString("src/main/kotlin/d6/input.dat")

    println(startOfPacketMarker(datastream))
    println(startOfMessageMarker(datastream))

}

fun startOfPacketMarker(datastream: String): Int {
    return datastream
        .windowed(4)
        .indexOfFirst { it -> it.toSet().size == it.length } + 4
}

fun startOfMessageMarker(datastream: String): Int {
    return datastream
        .windowed(14)
        .indexOfFirst { it -> it.toSet().size == it.length } + 14
}
