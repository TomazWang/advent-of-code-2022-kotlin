package dev.tomaz.aockt22

import dev.tomaz.aockt22.utils.readInput


fun main() {

    fun inputToCalList(input: List<String>): List<Int> {
        val calList = input.fold(mutableListOf<Int>(0)) { acc: MutableList<Int>, curr: String ->

            if (curr.isBlank()) {
                // if curr is blank, add a new number
                acc.add(0)
            } else {
                val num = curr.toInt()
                acc[acc.lastIndex] = acc.last() + num
            }
            // if curr is not blank, append number
            acc
        }
        return calList
    }

    fun part1(input: List<String>): Int = inputToCalList(input).maxOrNull() ?: 0

    fun part2(input: List<String>): Int = inputToCalList(input)
        .sortedDescending()
        .take(3)
        .sum()


    val sampleInput = readInput(dayNum = 1, fileName = "sample.txt")
    val sampleP1Result = part1(sampleInput)
    println("sample p1 ans: ${sampleP1Result}")

    val puzzleInput = readInput(dayNum = 1, fileName = "input-part1.txt")
    val part1Result = part1(puzzleInput)
    println("part1 ans: ${part1Result}")

    val sampleP2Result = part2(sampleInput)
    println("sample p2 ans: ${sampleP2Result}")
    val part2Result = part2(puzzleInput)
    println("part2 ans: ${part2Result}")

}



