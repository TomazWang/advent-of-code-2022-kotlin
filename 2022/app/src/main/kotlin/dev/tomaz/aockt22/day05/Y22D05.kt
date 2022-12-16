package dev.tomaz.aockt22.day05

import dev.tomaz.aockt22.utils.readInputAsText

/**
 * Created by TomazWang on 2022/12/09.
 *
 *
 * @author Tomaz Wang
 * @since 2022/12/09
 **/

fun main() {
    val inputText = readInputAsText(5, "input.txt")
    
    val data = inputText.split("\n\n")
    val initialCrates = data[0]
    val instructions = data[1].split("\n").map { it.toInstruction() }
    
    // println(data)
    // println(initialCrates)
    // println(instructions)
    
    val stacks = parseCrates(initialCrates).mapValues { ArrayDeque(it.value) }
    println(stacks.toList().sortedBy { it.first })
    
    instructions.filterNotNull().forEach {
        val (from, to, count) = it
        repeat(count) {
            stacks[from]
                ?.first()
                ?.also { moveItem ->
                    stacks[to]?.addFirst(moveItem)
                    stacks[from]?.removeFirst()
                }
        }
        println("after instruction: $it   $stacks")
    }
    
    println(stacks.mapValues { it.value.first() }.toList().sortedBy { it.first }.map { it.second })
}


private fun parseCrates(init: String): Map<Int, List<String>> {
    val lines = init.lines()
    val r = lines
        .dropLast(1)
        .map { line ->
            line.chunked(4).map { c -> c.trim() }
                .mapIndexed { index, v -> index + 1 to v }
                .filterNot { it.second.isBlank() }
        }
        .flatten()
        .groupBy({ it.first }, { it.second.replace("""[\[\]]""".toRegex(), "") })
    
    return r
}

private data class Instruction(val from: Int, val to: Int, val count: Int)

private fun String.toInstruction(): Instruction? {
    val r = """move (\d) from (\d) to (\d)""".toRegex()
    
    r.find(this)?.let {
        val (count, from, to) = it.destructured
        return Instruction(from.toInt(), to.toInt(), count.toInt())
    }
    
    return null
}