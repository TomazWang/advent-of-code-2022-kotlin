package dev.tomaz.aockt22.utils

import java.io.File

fun readInput(dayNum:Int, fileName: String): List<String> {
    val dayStr = dayNum.toString().padStart(2, '0')
    val dayDir = "day${dayStr}"
    val rootDir = "app/res"
    return File("${rootDir}/${dayDir}", "$fileName").readLines()
}
