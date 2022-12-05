package common

import java.io.File

fun readInputFileAsString(fileName:String): String {
    return File(fileName).readText()
}

fun readInputFile(fileName: String): List<String> {
    return File(fileName).readLines()
}
