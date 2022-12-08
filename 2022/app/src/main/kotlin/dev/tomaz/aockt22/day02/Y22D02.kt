package dev.tomaz.aockt22.day02

import dev.tomaz.aockt22.utils.readInput

fun main() {

    fun inputToSymbols(input: List<String>): List<Pair<String, String>> =
        input.map { it.split(" ").let { Pair(it[0], it[1]) } }

    fun symbolToHandShape(s: String) =
        when (s) {
            "X", "A" -> Hand.Rock
            "Y", "B" -> Hand.Paper
            "Z", "C" -> Hand.Scissors
            else -> null
        }

    fun symbolToResult(s: String) =
        when (s) {
            "X" -> Result.Lose
            "Y" -> Result.Draw
            "Z" -> Result.Win
            else -> null
        }

    fun part1(input: List<String>): Int {
        // opponent: A = rock, B = paper, C = scissors
        // response: X = rock, Y = paper, Z = scissors

        // score: rock = 1, paper = 2, scissors = 3; win = 6, lost = 0, draw = 3

        val hands = inputToSymbols(input)
            .map { Pair(symbolToHandShape(it.first), symbolToHandShape(it.second)) }

        val score = hands.sumOf { (oppnent, response) ->
            val result = response?.shoot(oppnent!!)!!
            result.socre + response.score
        }
        return score
    }


    fun part2(input: List<String>): Int {
        return inputToSymbols(input)
            .map { Pair(symbolToHandShape(it.first), symbolToResult(it.second)) }
            .sumOf { (opt, result) ->
                val myHand = when (result!!) {
                    Result.Win -> opt!!.loses
                    Result.Lose -> opt!!.beats
                    Result.Draw -> opt
                }

                myHand!!.score + result.socre
            }

    }

    val sampleInut = readInput(2, "sample.txt")
    val samplePart1 = part1(sampleInut)

    println("score = $samplePart1")

    val input = readInput(2, "input.txt")
    println("score = ${part1(input)}")

    println("score p2 = ${part2(sampleInut)}")
    println("score p2 = ${part2(input)}")

}


private enum class Hand(val score: Int) {
    Rock(1), Paper(2), Scissors(3);

    val beats: Hand
        get() = when (this) {
            Rock -> Scissors
            Paper -> Rock
            Scissors -> Paper
        }

    val loses: Hand
        get() = when (this) {
            Rock -> Paper
            Paper -> Scissors
            Scissors -> Rock
        }


    public fun shoot(hand: Hand): Result? = when {
        (this == hand) -> Result.Draw
        (this.beats == hand) -> Result.Win
        (this.loses == hand) -> Result.Lose
        else -> null
    }

}


private enum class Result(val socre: Int) {
    Win(6),
    Lose(0),
    Draw(3)
}

private fun Hand.shoot(other: Hand): Result {
    return when (this) {
        Hand.Rock -> when (other) {
            Hand.Paper -> Result.Lose
            Hand.Scissors -> Result.Win
            Hand.Rock -> Result.Draw
        }

        Hand.Paper -> when (other) {
            Hand.Rock -> Result.Win
            Hand.Paper -> Result.Draw
            Hand.Scissors -> Result.Lose
        }

        Hand.Scissors -> when (other) {
            Hand.Rock -> Result.Lose
            Hand.Paper -> Result.Win
            Hand.Scissors -> Result.Draw
        }
    }
}