package dev.tomaz.aockt22.day03

import dev.tomaz.aockt22.utils.readInput

fun charToPiority(c: Char): Int = when (c) {
    in 'a'..'z' -> ('a'..'z').indexOf(c) + 1
    in 'A'..'Z' -> ('A'..'Z').indexOf(c) + 27
    else -> 0
}


fun main() {

    fun Char.toPiority(): Int = charToPiority(this)

    fun part1(inputLins: List<String>): Int {
        return inputLins.sumOf { line ->
            val halfIdx = line.length / 2 - 1

            val firstComp = line.substring(0..halfIdx).toSet()
            val secComp = line.substring(halfIdx + 1).toSet()

            // error point
            secComp.filter { firstComp.contains(it) }
                .sumOf { charToPiority(it) }
        }
    }

    fun part2(inputLines: List<String>): Int {
        return inputLines.chunked(3)
            .map { group -> //List<List<String>>
                group
                    .map { it.toSet() }
                    .reduce { acc, chars -> acc.intersect(chars) }
                    .first()
                    .toPiority()
            }
            .sum()
    }

    val sampleInput = readInput(3, "sample.txt")
    val puzzleInput = readInput(3, "input.txt")


    println("part1(sampleInput) = ${part1(sampleInput)}")
    println("part1(puzzleInput) = ${part1(puzzleInput)}")

    println("part2(sampleInput) = ${part2(sampleInput)}")
    println("part2(puzzleInput) = ${part2(puzzleInput)}")
}