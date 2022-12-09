package dev.tomaz.aockt22.day04

import dev.tomaz.aockt22.utils.readInput

fun main() {

    fun readInputAsSectionPair(inputLines: List<String>) = inputLines.map { line ->
        line.split(",").let {
            val first = it[0].toSectionDef()
            val sec = it[1].toSectionDef()
            Pair(first, sec)
        }
    }

    fun part1(inputLines: List<String>): Int {
        // line: a-b,c-d
        val sections = readInputAsSectionPair(inputLines)

        val ans = sections.sumOf { sec ->
            val (one: SectionDef, other: SectionDef) = sec
            when {
                one.start <= other.start && one.end >= other.end -> 1
                other.start <= one.start && other.end >= one.end -> 1
                else -> 0 as Int
            }
        }

        return ans
    }

    fun part2(inputLines: List<String>): Int {
        val sectionPairs = readInputAsSectionPair(inputLines)
        val ans = sectionPairs.map { (one, other) ->
            when {
                one.start in other.start..other.end || one.end in other.start..other.end -> 1
                other.start in one.start..one.end || other.end in one.start..one.end -> 1
                else -> 0
            }
        }.sum()
        return ans
    }

    // parse inputs
    val sampleInput = readInput(4, "sample.txt")
    val puzzleInput = readInput(4, "input.txt")


    println("part 1 sample: ${part1(sampleInput)}")
    println("part 1 puzzle: ${part1(puzzleInput)}")
    println("part 2 sample: ${part2(sampleInput)}")
    println("part 2 puzzle: ${part2(puzzleInput)}")


}

private data class SectionDef(val start: Int, val end: Int)

private fun String.toSectionDef() = this.split("-").let {
    val start = it.getOrNull(0)?.toInt() ?: 0
    val end = it.getOrNull(1)?.toInt() ?: 0
    SectionDef(start, end)
}