package dev.tomaz.aockt22.day06

import dev.tomaz.aockt22.utils.readInputAsText

/**
 * Created by TomazWang on 2022/12/09.
 *
 *
 * @author Tomaz Wang
 * @since 2022/12/09
 **/

fun main() {
    
    val sample0 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb" // 7
    val sample1 = "bvwbjplbgvbhsrlpgdmjqwftvncz"  // 5
    val sample2 = "nppdvjthqldpwncqszvftbrmjlhg" // 6
    val sample3 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" // 10
    val sample4 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" // 11
    
    val input = readInputAsText(6, "input.txt")
    
    println(part1(sample0))
    println(part1(sample1))
    println(part1(sample2))
    println(part1(sample3))
    println(part1(sample4))
    
    println(part1(input))
    
    
    println("-----")
    
    println(part2(sample0))
    println(part2(sample1))
    println(part2(sample2))
    println(part2(sample3))
    println(part2(sample4))
    
    println(part2(input))
}


private fun part1(input: String) = scan(input, 4)
private fun part2(input: String) = scan(input, 14)

private fun scan(input: String, distinctInRecent: Int): Int {
    val chars = input.toList()
    
    if (chars.size <= distinctInRecent) {
        return chars.size
    }
    
    
    for (idx in (distinctInRecent - 1) until (chars.size)) {
        val recentFour = chars.subList(idx - (distinctInRecent - 1), idx + 1)
        if (recentFour.toSet().size == distinctInRecent) {
            return idx + 1
        }
    }
    
    return -1
}